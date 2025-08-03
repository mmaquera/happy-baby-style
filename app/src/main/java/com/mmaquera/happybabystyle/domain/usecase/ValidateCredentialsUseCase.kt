package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.repository.AuthRepository

/**
 * Use Case para validar credenciales de email y contraseña
 */
class ValidateCredentialsUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Ejecuta la validación de credenciales
     * @param parameters Parámetros con email y contraseña a validar
     * @return Result Resultado de la validación
     */
    suspend operator fun invoke(parameters: Params): Result {
        val emailValidation = validateEmail(parameters.email)
        val passwordValidation = validatePassword(parameters.password)
        
        return Result(
            emailValidation = emailValidation,
            passwordValidation = passwordValidation,
            isValid = emailValidation.isValidEmail && passwordValidation.isValidPassword
        )
    }
    
    private fun validateEmail(email: String): EmailValidation {
        return when {
            email.isBlank() -> EmailValidation(
                isValidEmail = false,
                emailError = "El email es requerido"
            )
            !authRepository.isValidEmail(email) -> EmailValidation(
                isValidEmail = false,
                emailError = "Formato de email inválido"
            )
            else -> EmailValidation(isValidEmail = true)
        }
    }
    
    private fun validatePassword(password: String): PasswordValidation {
        return when {
            password.isBlank() -> PasswordValidation(
                isValidPassword = false,
                passwordError = "La contraseña es requerida"
            )
            !authRepository.isValidPassword(password) -> PasswordValidation(
                isValidPassword = false,
                passwordError = "La contraseña debe tener al menos 6 caracteres"
            )
            else -> PasswordValidation(isValidPassword = true)
        }
    }
    
    data class Params(
        val email: String,
        val password: String
    )
    
    data class Result(
        val emailValidation: EmailValidation,
        val passwordValidation: PasswordValidation,
        val isValid: Boolean
    )
}

/**
 * Data class para validación de credenciales de email
 */
data class EmailValidation(
    val isValidEmail: Boolean,
    val emailError: String? = null
)

/**
 * Data class para validación de contraseña
 */
data class PasswordValidation(
    val isValidPassword: Boolean,
    val passwordError: String? = null
)