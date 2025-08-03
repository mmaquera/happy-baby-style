package com.mmaquera.happybabystyle.domain.model

/**
 * Domain model para el resultado de autenticación
 * Representa todos los posibles estados de una operación de autenticación
 */
sealed class AuthResult {
    
    /**
     * Autenticación exitosa
     */
    data class Success(
        val user: User,
        val accessToken: String,
        val refreshToken: String? = null
    ) : AuthResult()
    
    /**
     * Error en la autenticación
     */
    data class Error(
        val exception: AuthException
    ) : AuthResult()
    
    /**
     * Estado de carga
     */
    object Loading : AuthResult()
}

/**
 * Excepciones específicas del dominio de autenticación
 */
sealed class AuthException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    
    /**
     * Credenciales inválidas
     */
    class InvalidCredentials(message: String = "Email o contraseña incorrectos") : AuthException(message)
    
    /**
     * Usuario no encontrado
     */
    class UserNotFound(message: String = "Usuario no encontrado") : AuthException(message)
    
    /**
     * Email no verificado
     */
    class EmailNotVerified(message: String = "Por favor verifica tu email") : AuthException(message)
    
    /**
     * Cuenta deshabilitada
     */
    class AccountDisabled(message: String = "Cuenta deshabilitada") : AuthException(message)
    
    /**
     * Demasiados intentos
     */
    class TooManyAttempts(message: String = "Demasiados intentos, intenta más tarde") : AuthException(message)
    
    /**
     * Error de red
     */
    class NetworkError(message: String = "Error de conexión") : AuthException(message)
    
    /**
     * Error del servidor
     */
    class ServerError(message: String = "Error del servidor") : AuthException(message)
    
    /**
     * Error de configuración
     */
    class ConfigurationError(message: String = "Error de configuración") : AuthException(message)
    
    /**
     * Error desconocido
     */
    class UnknownError(message: String = "Error desconocido", cause: Throwable? = null) : AuthException(message, cause)
}