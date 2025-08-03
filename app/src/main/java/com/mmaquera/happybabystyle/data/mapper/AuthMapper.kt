package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.data.model.SupabaseUser
import com.mmaquera.happybabystyle.data.model.UserProfile
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.domain.model.AuthProvider

/**
 * Mapper para convertir entre modelos de datos y dominio en autenticación
 */
object AuthMapper {
    
    /**
     * Mapea excepciones de la capa de datos a excepciones del dominio
     */
    fun mapToAuthException(throwable: Throwable): AuthException {
        return when {
            throwable.message?.contains("invalid_credentials") == true -> {
                AuthException.InvalidCredentials("Credenciales incorrectas")
            }
            throwable.message?.contains("user_not_found") == true -> {
                AuthException.UserNotFound("Usuario no encontrado")
            }
            throwable.message?.contains("email_not_confirmed") == true -> {
                AuthException.EmailNotVerified("Debes verificar tu email antes de iniciar sesión")
            }
            throwable.message?.contains("account_disabled") == true -> {
                AuthException.AccountDisabled("Tu cuenta ha sido deshabilitada")
            }
            throwable.message?.contains("too_many_requests") == true -> {
                AuthException.TooManyAttempts("Demasiados intentos. Intenta más tarde")
            }
            throwable.message?.contains("network") == true || 
            throwable.message?.contains("connection") == true -> {
                AuthException.NetworkError("Error de conexión. Verifica tu internet")
            }
            throwable.message?.contains("server") == true ||
            throwable.message?.contains("500") == true ||
            throwable.message?.contains("503") == true -> {
                AuthException.ServerError("Error del servidor. Intenta más tarde")
            }
            throwable.message?.contains("config") == true -> {
                AuthException.ConfigurationError("Error de configuración de la aplicación")
            }
            else -> {
                AuthException.UnknownError(
                    message = throwable.message ?: "Error desconocido",
                    cause = throwable
                )
            }
        }
    }
    
    /**
     * Mapea SupabaseUser a User del dominio
     */
    fun mapToUser(supabaseUser: SupabaseUser, profile: UserProfile? = null): User {
        val provider = when {
            supabaseUser.appMetadata.provider == "google" -> AuthProvider.GOOGLE
            supabaseUser.appMetadata.provider == "facebook" -> AuthProvider.FACEBOOK
            supabaseUser.appMetadata.provider == "apple" -> AuthProvider.APPLE
            else -> AuthProvider.EMAIL
        }
        
        return User(
            id = supabaseUser.id,
            email = supabaseUser.email,
            firstName = profile?.firstName ?: supabaseUser.userMetadata.givenName,
            lastName = profile?.lastName ?: supabaseUser.userMetadata.familyName,
            avatarUrl = profile?.avatarUrl ?: supabaseUser.userMetadata.avatarUrl ?: supabaseUser.userMetadata.picture,
            provider = provider,
            isEmailVerified = supabaseUser.emailConfirmedAt != null,
            phone = profile?.phone ?: supabaseUser.phone,
            createdAt = profile?.createdAt ?: supabaseUser.createdAt
        )
    }
    
    /**
     * Mapea errores HTTP a excepciones del dominio
     */
    fun mapHttpErrorToAuthException(statusCode: Int, errorBody: String?): AuthException {
        return when (statusCode) {
            400 -> AuthException.InvalidCredentials("Datos de login inválidos")
            401 -> AuthException.InvalidCredentials("Credenciales incorrectas")
            403 -> AuthException.AccountDisabled("Acceso denegado")
            404 -> AuthException.UserNotFound("Usuario no encontrado")
            429 -> AuthException.TooManyAttempts("Demasiados intentos")
            500, 502, 503, 504 -> AuthException.ServerError("Error del servidor")
            else -> AuthException.UnknownError("Error HTTP $statusCode: $errorBody")
        }
    }
}