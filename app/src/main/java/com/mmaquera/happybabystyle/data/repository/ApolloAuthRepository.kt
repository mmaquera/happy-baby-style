package com.mmaquera.happybabystyle.data.repository

import com.mmaquera.happybabystyle.data.network.ApolloGraphQLClient
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import com.mmaquera.happybabystyle.graphql.LoginUserMutation
import com.mmaquera.happybabystyle.graphql.RegisterUserMutation
import com.mmaquera.happybabystyle.graphql.LogoutUserMutation
import com.mmaquera.happybabystyle.graphql.GetCurrentUserQuery
import com.mmaquera.happybabystyle.graphql.RefreshTokenMutation
import com.mmaquera.happybabystyle.graphql.fragment.AuthResponseInfo
import com.mmaquera.happybabystyle.graphql.fragment.UserInfo
import com.mmaquera.happybabystyle.graphql.type.LoginUserInput
import com.mmaquera.happybabystyle.graphql.type.RegisterUserInput
import com.mmaquera.happybabystyle.graphql.type.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

/**
 * Repositorio de autenticación usando Apollo GraphQL
 * Implementa Clean Architecture con código generado type-safe
 * 
 * Características:
 * - Código generado type-safe por Apollo
 * - Cache automático
 * - Optimistic updates
 * - Error handling robusto
 */
class ApolloAuthRepository(
    private val apolloClient: ApolloGraphQLClient
) : AuthRepository {
    
    private val _authState = MutableStateFlow<User?>(null)
    
    override suspend fun login(credentials: LoginCredentials): Flow<AuthResult> = flow {
        try {
            // Validar que las credenciales sean de tipo EmailPassword
            if (credentials !is LoginCredentials.EmailPassword) {
                emit(AuthResult.Error(AuthException.UnsupportedOperation("Tipo de credenciales no soportado")))
                return@flow
            }
            
            val mutation = LoginUserMutation(
                input = LoginUserInput(
                    email = credentials.email,
                    password = credentials.password
                )
            )
            
            val response = apolloClient.getClient().mutation(mutation).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error de autenticación"
                emit(AuthResult.Error(AuthException.UnknownError(errorMessage)))
            } else {
                val authData = response.data?.loginUser?.authResponseInfo
                if (authData != null) {
                    // Actualizar token en el cliente
                    authData.token.let { token ->
                        apolloClient.updateAccessToken(token)
                    }
                    
                    // Mapear usuario
                    val user = mapToUser(authData.user)
                    _authState.value = user
                    emit(AuthResult.Success(
                        user = user,
                        accessToken = authData.token,
                        refreshToken = authData.refreshToken
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
            
            val response = apolloClient.getClient().mutation(mutation).execute()
            
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
            
            val response = apolloClient.getClient().query(query).execute()
            
            if (response.hasErrors()) {
                null
            } else {
                val userData = response.data?.currentUser
                userData?.let { mapToUser(it) }
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
        return isValidEmail(email) && isValidPassword(password)
    }
    
    override fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    override fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    override suspend fun register(name: String, email: String, password: String): Flow<AuthResult> = flow {
        try {
            val mutation = RegisterUserMutation(
                input = RegisterUserInput(
                    firstName = name.split(" ").firstOrNull() ?: "",
                    lastName = name.split(" ").drop(1).joinToString(" "),
                    email = email,
                    password = password
                )
            )
            
            val response = apolloClient.getClient().mutation(mutation).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error de registro"
                emit(AuthResult.Error(AuthException.UnknownError(errorMessage)))
            } else {
                val authData = response.data?.registerUser?.authResponseInfo
                if (authData != null) {
                    // Actualizar token en el cliente
                    authData.token.let { token ->
                        apolloClient.updateAccessToken(token)
                    }
                    
                    // Mapear usuario
                    val user = mapToUser(authData.user)
                    _authState.value = user
                    emit(AuthResult.Success(
                        user = user,
                        accessToken = authData.token,
                        refreshToken = authData.refreshToken
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
    
    // ===== FUNCIONES DE MAPPING =====
    
    private fun mapToUser(userData: AuthResponseInfo.User): User {
        return User(
            id = userData.id,
            email = userData.email,
            firstName = userData.profile?.firstName,
            lastName = userData.profile?.lastName,
            avatarUrl = userData.profile?.avatarUrl,
            phone = null, // No disponible en AuthResponseInfo
            isEmailVerified = userData.emailVerified,
            provider = mapProvider(userData.role),
            createdAt = userData.createdAt.toString()
        )
    }
    
    private fun mapToUser(userData: GetCurrentUserQuery.CurrentUser): User {
        return User(
            id = userData.userInfo.id,
            email = userData.userInfo.email,
            firstName = userData.profile?.userProfileInfo?.firstName,
            lastName = userData.profile?.userProfileInfo?.lastName,
            avatarUrl = userData.profile?.userProfileInfo?.avatarUrl,
            phone = userData.profile?.userProfileInfo?.phone,
            isEmailVerified = userData.userInfo.emailVerified,
            provider = mapProvider(userData.userInfo.role),
            createdAt = userData.userInfo.createdAt.toString()
        )
    }
    
    private fun mapProvider(role: UserRole): com.mmaquera.happybabystyle.domain.model.AuthProvider {
        return when (role) {
            UserRole.admin -> com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            UserRole.customer -> com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            UserRole.staff -> com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            else -> com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
        }
    }
} 