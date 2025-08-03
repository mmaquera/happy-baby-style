package com.mmaquera.happybabystyle.data.repository

import android.util.Patterns
import com.mmaquera.happybabystyle.data.mapper.AuthMapper
import com.mmaquera.happybabystyle.data.mapper.ServiceMapper
import com.mmaquera.happybabystyle.data.service.ModernAuthService
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementación del repositorio de autenticación
 * Actúa como puente entre la capa de dominio y la capa de datos
 */
class AuthRepositoryImpl(
    private val modernAuthService: ModernAuthService
) : AuthRepository {
    
    private val _authState = MutableStateFlow<User?>(null)
    
    override suspend fun login(credentials: LoginCredentials): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        
        try {
            val serviceResult = when (credentials) {
                is LoginCredentials.EmailPassword -> {
                    modernAuthService.signInWithEmail(
                        email = credentials.email,
                        password = credentials.password
                    )
                }
                
                is LoginCredentials.Google -> {
                    modernAuthService.signInWithGoogle()
                }
                
                else -> {
                    throw UnsupportedOperationException("Tipo de credencial no soportado")
                }
            }
            
            // Mapear resultado del servicio a dominio
            val domainResult = ServiceMapper.mapToDomainAuthResult(serviceResult)
            
            // Actualizar estado de autenticación si es exitoso
            if (domainResult is AuthResult.Success) {
                _authState.value = domainResult.user
            }
            
            emit(domainResult)
            
        } catch (exception: Exception) {
            val authException = AuthMapper.mapToAuthException(exception)
            emit(AuthResult.Error(authException))
        }
    }
    
    override suspend fun logout(): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        
        try {
            modernAuthService.signOut()
            _authState.value = null
            
            // Crear un usuario vacío para representar logout exitoso
            val emptyUser = User(
                id = "",
                email = "",
                provider = com.mmaquera.happybabystyle.domain.model.AuthProvider.EMAIL
            )
            
            emit(AuthResult.Success(
                user = emptyUser,
                accessToken = "",
                refreshToken = null
            ))
            
        } catch (exception: Exception) {
            val authException = AuthMapper.mapToAuthException(exception)
            emit(AuthResult.Error(authException))
        }
    }
    
    override suspend fun getCurrentUser(): User? {
        return try {
            val supabaseUser = modernAuthService.getCurrentUser()
            val userProfile = modernAuthService.getCurrentProfile()
            
            if (supabaseUser != null) {
                val user = AuthMapper.mapToUser(supabaseUser, userProfile)
                _authState.value = user
                user
            } else {
                _authState.value = null
                null
            }
        } catch (exception: Exception) {
            _authState.value = null
            null
        }
    }
    
    override suspend fun isUserAuthenticated(): Boolean {
        return try {
            modernAuthService.isAuthenticated()
        } catch (exception: Exception) {
            false
        }
    }
    
    override fun observeAuthState(): Flow<User?> {
        return _authState.asStateFlow()
    }
    
    override suspend fun refreshToken(): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        
        try {
            // Por ahora, simplemente verificamos si el usuario actual existe
            val currentUser = getCurrentUser()
            
            if (currentUser != null) {
                emit(AuthResult.Success(
                    user = currentUser,
                    accessToken = "refreshed_token",
                    refreshToken = null
                ))
            } else {
                emit(AuthResult.Error(
                    AuthMapper.mapToAuthException(Exception("No user authenticated"))
                ))
            }
            
        } catch (exception: Exception) {
            val authException = AuthMapper.mapToAuthException(exception)
            emit(AuthResult.Error(authException))
        }
    }
    
    override fun validateCredentials(email: String, password: String): Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }
    
    override fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && 
               Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
               email.length <= 254 // RFC 5321 limit
    }
    
    override fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && 
               password.length >= 6 && // Mínimo 6 caracteres
               password.length <= 128 // Máximo razonable
    }
}