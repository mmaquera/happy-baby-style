package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use Case para autenticación con email y contraseña
 * Encapsula toda la lógica de negocio para login con credenciales email/password
 */
class LoginWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Ejecuta el login con email y contraseña
     * @param params Parámetros con email y contraseña
     * @return Flow<AuthResult> Resultado de la autenticación
     */
    suspend operator fun invoke(parameters: Params): Flow<AuthResult> = flow {
        // Emitir estado de carga
        emit(AuthResult.Loading)
        
        try {
            // Validar parámetros de entrada
            validateParams(parameters)
            
            // Crear credenciales de email y contraseña
            val credentials = LoginCredentials.EmailPassword(
                email = parameters.email.trim(),
                password = parameters.password
            )
            
            // Ejecutar login a través del repository
            authRepository.login(credentials).collect { result ->
                emit(result)
            }
            
        } catch (exception: Exception) {
            emit(AuthResult.Error(
                AuthException.UnknownError(
                    message = "Error inesperado durante el login con email",
                    cause = exception
                )
            ))
        }
    }
    
    /**
     * Valida los parámetros de entrada
     */
    private fun validateParams(params: Params) {
        when {
            params.email.isBlank() -> {
                throw AuthException.InvalidCredentials("Email es requerido")
            }
            params.password.isBlank() -> {
                throw AuthException.InvalidCredentials("Contraseña es requerida")
            }
            !isValidEmailFormat(params.email) -> {
                throw AuthException.InvalidCredentials("Formato de email inválido")
            }
        }
    }
    
    /**
     * Validación básica de formato de email
     */
    private fun isValidEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    /**
     * Parámetros requeridos para el use case
     */
    data class Params(
        val email: String,
        val password: String
    )
}