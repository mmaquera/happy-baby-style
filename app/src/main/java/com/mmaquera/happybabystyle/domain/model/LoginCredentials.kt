package com.mmaquera.happybabystyle.domain.model

/**
 * Domain model para credenciales de login
 * Encapsula los datos necesarios para autenticación
 */
sealed class LoginCredentials {
    
    /**
     * Credenciales para login con email y contraseña
     */
    data class EmailPassword(
        val email: String,
        val password: String
    ) : LoginCredentials()
    
    /**
     * Credenciales para login con Google
     */
    data class Google(
        val idToken: String,
        val accessToken: String? = null
    ) : LoginCredentials()
    
    /**
     * Credenciales para login con Facebook
     */
    data class Facebook(
        val accessToken: String
    ) : LoginCredentials()
    
    /**
     * Credenciales para login con Apple
     */
    data class Apple(
        val identityToken: String,
        val authorizationCode: String
    ) : LoginCredentials()
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