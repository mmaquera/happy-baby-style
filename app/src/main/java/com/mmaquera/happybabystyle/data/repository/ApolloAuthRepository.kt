package com.mmaquera.happybabystyle.data.repository

import com.apollographql.apollo.api.Optional
import com.mmaquera.happybabystyle.data.mapper.ErrorMapper
import com.mmaquera.happybabystyle.data.mapper.ValidationMapper
import com.mmaquera.happybabystyle.data.mapper.mapFromCurrentUserQuery
import com.mmaquera.happybabystyle.data.mapper.mapFromUserBasicInfo
import com.mmaquera.happybabystyle.data.network.ApolloGraphQLClient
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import com.mmaquera.happybabystyle.graphql.GetCurrentUserQuery
import com.mmaquera.happybabystyle.graphql.LoginUserMutation
import com.mmaquera.happybabystyle.graphql.LogoutUserMutation
import com.mmaquera.happybabystyle.graphql.RegisterUserMutation
import com.mmaquera.happybabystyle.graphql.type.CreateUserProfileInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repositorio de autenticación usando Apollo GraphQL
 * Implementa Clean Architecture con código generado type-safe
 * 
 * Responsabilidad única: Coordinación de operaciones de autenticación
 * - Delega construcción de mutations al QueryBuilder
 * - Delega mapeo de usuarios al UserMapper
 * - Delega validaciones al ValidationMapper
 * - Delega manejo de errores al ErrorMapper
 */
class ApolloAuthRepository(
    private val apolloClient: ApolloGraphQLClient,
    private val validationMapper: ValidationMapper,
    private val errorMapper: ErrorMapper,
) : AuthRepository {
    
    private val _authState = MutableStateFlow<User?>(null)
    
    override suspend fun login(credentials: LoginCredentials): Flow<AuthResult> = flow {
        try {
            // Validar que las credenciales sean de tipo EmailPassword
            if (credentials !is LoginCredentials.EmailPassword) {
                emit(AuthResult.Error(AuthException.UnsupportedOperation("Tipo de credenciales no soportado")))
                return@flow
            }
            
            // Validar credenciales usando el validation mapper
            if (!validationMapper.validateCredentials(credentials.email, credentials.password)) {
                emit(AuthResult.Error(AuthException.InvalidCredentials("Credenciales inválidas")))
                return@flow
            }
            
            // Construir mutation usando el query builder
            val mutation = LoginUserMutation(
                email = credentials.email,
                password = credentials.password
            )
            
            // Ejecutar mutation
            val response = apolloClient.getClient().mutation(mutation).executeV3()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(AuthResult.Error(AuthException.UnknownError(errorMessage)))
            } else {
                val userData = response.data?.loginUser?.user
                if (userData != null) {
                    // Mapear usuario usando el user mapper
                    val user = userData.userBasicInfo.mapFromUserBasicInfo()
                    _authState.value = user
                    emit(AuthResult.Success(
                        user = user,
                        accessToken = "", // Token no disponible en la respuesta actual
                        refreshToken = "" // Refresh token no disponible en la respuesta actual
                    ))
                } else {
                    emit(AuthResult.Error(AuthException.UnknownError("Respuesta de autenticación inválida")))
                }
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(AuthException.NetworkError(e.message ?: "Error de conexión", e)))
        }
    }.catch { exception ->
        emit(AuthResult.Error(AuthException.UnknownError(exception.message ?: "Error desconocido", exception)))
    }
    
    override suspend fun logout(): Flow<AuthResult> = flow {
        try {
            val mutation = LogoutUserMutation()
            
            // Ejecutar mutation
            val response = apolloClient.getClient().mutation(mutation).executeV3()
            
            // Limpiar autenticación independientemente de la respuesta del servidor
            apolloClient.clearAuth()
            _authState.value = null
            
            // Crear un usuario temporal para el logout exitoso
            val tempUser = User(
                id = "",
                email = "",
                provider = com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            )
            emit(AuthResult.Success(user = tempUser, accessToken = ""))
        } catch (e: Exception) {
            // Limpiar autenticación local incluso si hay error
            apolloClient.clearAuth()
            _authState.value = null
            val tempUser = User(
                id = "",
                email = "",
                provider = com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            )
            emit(AuthResult.Success(user = tempUser, accessToken = ""))
        }
    }.catch { exception ->
        // Limpiar autenticación local
        apolloClient.clearAuth()
        _authState.value = null
        val tempUser = User(
            id = "",
            email = "",
            provider = com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
        )
        emit(AuthResult.Success(user = tempUser, accessToken = ""))
    }
    
    override suspend fun getCurrentUser(): User? {
        return try {
            val query = GetCurrentUserQuery()
            
            // Ejecutar query
            val response = apolloClient.getClient().query(query).executeV3()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                null
            } else {
                val userData = response.data?.currentUser
                userData?.mapFromCurrentUserQuery()
            }
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun isUserAuthenticated(): Boolean {
        return apolloClient.isAuthenticated()
    }
    
    override fun observeAuthState(): Flow<User?> {
        return _authState
    }
    
    override suspend fun refreshToken(): Flow<AuthResult> = flow {
        try {
            // TODO: Implementar refresh token cuando esté disponible
            emit(AuthResult.Error(AuthException.UnsupportedOperation("Refresh token no implementado")))
        } catch (e: Exception) {
            emit(AuthResult.Error(AuthException.NetworkError(e.message ?: "Error al refrescar token", e)))
        }
    }.catch { exception ->
        emit(AuthResult.Error(AuthException.UnknownError(exception.message ?: "Error desconocido", exception)))
    }
    
    override fun validateCredentials(email: String, password: String): Boolean {
        return validationMapper.validateCredentials(email, password)
    }
    
    override fun isValidEmail(email: String): Boolean {
        return validationMapper.isValidEmail(email)
    }
    
    override fun isValidPassword(password: String): Boolean {
        return validationMapper.isValidPassword(password)
    }

    override suspend fun register(name: String, email: String, password: String): Flow<AuthResult> = flow {
        try {
            // Validar nombre completo usando el validation mapper
            if (!validationMapper.validateFullName(name)) {
                emit(AuthResult.Error(AuthException.InvalidCredentials("Nombre inválido")))
                return@flow
            }
            
            // Extraer nombre y apellido usando el validation mapper
            val (firstName, lastName) = validationMapper.extractNameParts(name)

            // Construir mutation usando el query builder
            val mutation = RegisterUserMutation(
                input = CreateUserProfileInput(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = Optional.present(password)
                )
            )

            // Ejecutar mutation
            val response = apolloClient.getClient().mutation(mutation).executeV3()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(AuthResult.Error(AuthException.UnknownError(errorMessage)))
            } else {
                val userData = response.data?.registerUser?.user
                if (userData != null) {
                    // Mapear usuario usando el user mapper
                    val user = userData.userBasicInfo.mapFromUserBasicInfo()
                    _authState.value = user
                    emit(AuthResult.Success(
                        user = user,
                        accessToken = "", // Token no disponible en la respuesta actual
                        refreshToken = "" // Refresh token no disponible en la respuesta actual
                    ))
                } else {
                    emit(AuthResult.Error(AuthException.UnknownError("Respuesta de registro inválida")))
                }
            }
        } catch (e: Exception) {
            emit(AuthResult.Error(AuthException.NetworkError(e.message ?: "Error de conexión", e)))
        }
    }.catch { exception ->
        emit(AuthResult.Error(AuthException.UnknownError(exception.message ?: "Error desconocido", exception)))
    }
    
    /**
     * Método adicional para login con Google
     */
    suspend fun signInWithGoogle(idToken: String): Flow<AuthResult> = flow {
        try {
            // TODO: Implementar login con Google cuando esté disponible en GraphQL
            emit(AuthResult.Error(AuthException.UnsupportedOperation("Login con Google no implementado en GraphQL")))
        } catch (e: Exception) {
            emit(AuthResult.Error(AuthException.NetworkError(e.message ?: "Error de conexión", e)))
        }
    }.catch { exception ->
        emit(AuthResult.Error(AuthException.UnknownError(exception.message ?: "Error desconocido", exception)))
    }
} 