package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use Case para cerrar sesión del usuario
 */
class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Ejecuta el logout del usuario
     * @return Flow<AuthResult> Resultado del logout
     */
    suspend operator fun invoke(): Flow<AuthResult> = flow {
        // Emitir estado de carga
        emit(AuthResult.Loading)
        
        try {
            // Ejecutar logout a través del repository
            authRepository.logout().collect { result ->
                emit(result)
            }
        } catch (exception: Exception) {
            emit(AuthResult.Error(AuthException.UnknownError(
                message = "Error inesperado durante el logout",
                cause = exception
            )))
        }
    }
}