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
    class NetworkError(message: String = "Error de conexión", cause: Throwable? = null) : AuthException(message, cause)
    
    /**
     * Error del servidor
     */
    class ServerError(message: String = "Error del servidor", cause: Throwable? = null) : AuthException(message, cause)
    
    /**
     * Email ya está en uso
     */
    class EmailAlreadyInUse(message: String = "Este email ya está registrado") : AuthException(message)
    
    /**
     * Contraseña muy débil
     */
    class WeakPassword(message: String = "La contraseña es muy débil") : AuthException(message)
    
    /**
     * Registro deshabilitado
     */
    class SignupDisabled(message: String = "El registro está deshabilitado") : AuthException(message)
    
    /**
     * Límite de intentos excedido
     */
    class RateLimitExceeded(message: String = "Demasiados intentos, espera un momento") : AuthException(message)
    
    /**
     * Operación no soportada
     */
    class UnsupportedOperation(message: String = "Operación no soportada") : AuthException(message)
    
    /**
     * Error de configuración
     */
    class ConfigurationError(message: String = "Error de configuración") : AuthException(message)
    
    /**
     * Error desconocido
     */
    class UnknownError(message: String = "Error desconocido", cause: Throwable? = null) : AuthException(message, cause)
}