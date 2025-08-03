package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use Case para autenticación con Google
 * Encapsula toda la lógica de negocio para login con Google Sign-In
 */
class LoginWithGoogleUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Ejecuta el login con Google
     * @param parameters Parámetros con tokens de Google
     * @return Flow<AuthResult> Resultado de la autenticación
     */
    suspend operator fun invoke(parameters: Params): Flow<AuthResult> = flow {
        // Emitir estado de carga
        emit(AuthResult.Loading)
        
        try {
            // Validar parámetros de entrada
            validateParams(parameters)
            
            // Crear credenciales de Google
            val credentials = LoginCredentials.Google(
                idToken = parameters.idToken,
                accessToken = parameters.accessToken
            )
            
            // Ejecutar login a través del repository
            authRepository.login(credentials).collect { result ->
                emit(result)
            }
            
        } catch (exception: AuthException) {
            emit(AuthResult.Error(exception))
        } catch (exception: Exception) {
            emit(AuthResult.Error(AuthException.UnknownError(
                message = "Error inesperado durante el login con Google",
                cause = exception
            )))
        }
    }
    
    /**
     * Valida los parámetros de entrada
     */
    private fun validateParams(params: Params) {
        when {
            params.idToken.isBlank() -> {
                throw AuthException.InvalidCredentials("ID Token de Google es requerido")
            }
            // Validaciones adicionales específicas de Google pueden ir aquí
        }
    }
    
    /**
     * Parámetros requeridos para el use case
     */
    data class Params(
        val idToken: String,
        val accessToken: String? = null
    )
}