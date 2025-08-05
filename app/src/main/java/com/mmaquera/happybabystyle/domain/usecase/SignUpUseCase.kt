package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Use Case para registro de nuevos usuarios
 * Encapsula la lógica de negocio para el registro
 * Siguiendo Single Responsibility Principle
 */
class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Ejecuta el registro de un nuevo usuario
     * @param name Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param confirmPassword Confirmación de la contraseña
     * @return Flow<AuthResult> Resultado del registro
     */
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<AuthResult> {
        
        // Validaciones de negocio
        if (name.isBlank()) {
            return flowOf(
                AuthResult.Error(
                    AuthException.UnknownError("El nombre es requerido")
                )
            )
        }
        
        if (email.isBlank()) {
            return flowOf(
                AuthResult.Error(
                    AuthException.UnknownError("El email es requerido")
                )
            )
        }
        
        if (password.isBlank()) {
            return flowOf(
                AuthResult.Error(
                    AuthException.UnknownError("La contraseña es requerida")
                )
            )
        }
        
        if (password != confirmPassword) {
            return flowOf(
                AuthResult.Error(
                    AuthException.UnknownError("Las contraseñas no coinciden")
                )
            )
        }
        
        // Validaciones técnicas usando el repository
        if (!authRepository.isValidEmail(email)) {
            return flowOf(
                AuthResult.Error(
                    AuthException.UnknownError("Formato de email inválido")
                )
            )
        }
        
        if (!authRepository.isValidPassword(password)) {
            return flowOf(
                AuthResult.Error(
                    AuthException.WeakPassword("La contraseña debe tener al menos 6 caracteres")
                )
            )
        }
        
        // Ejecutar registro
        return authRepository.register(name, email, password)
    }
}