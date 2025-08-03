package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.data.model.SupabaseAuthUser
import com.mmaquera.happybabystyle.data.model.SupabaseUserProfile
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.model.AuthProvider
import com.mmaquera.happybabystyle.domain.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Mapper para convertir entre modelos de Supabase y modelos de dominio
 * Siguiendo Clean Architecture - convierte datos de la capa de datos a dominio
 */
object SupabaseAuthMapper {

    /**
     * Convierte SupabaseAuthUser y SupabaseUserProfile a User del dominio
     */
    fun mapToUser(supabaseUser: SupabaseAuthUser, profile: SupabaseUserProfile?): User {
        // Determinar el proveedor de autenticación basado en metadata
        val provider = determineAuthProvider(supabaseUser)
        
        // Extraer nombres de user_metadata o del perfil
        val firstName = profile?.firstName 
            ?: supabaseUser.userMetadata["given_name"]?.toString()
            ?: supabaseUser.userMetadata["name"]?.toString()?.split(" ")?.firstOrNull()
            ?: "Usuario"
            
        val lastName = profile?.lastName 
            ?: supabaseUser.userMetadata["family_name"]?.toString()
            ?: supabaseUser.userMetadata["name"]?.toString()?.split(" ")?.drop(1)?.joinToString(" ")
            ?: ""

        // Extraer avatar URL
        val avatarUrl = profile?.avatarUrl
            ?: supabaseUser.userMetadata["avatar_url"]?.toString()
            ?: supabaseUser.userMetadata["picture"]?.toString()

        return User(
            id = supabaseUser.id,
            email = supabaseUser.email,
            firstName = firstName,
            lastName = lastName,
            avatarUrl = avatarUrl,
            provider = provider,
            isEmailVerified = supabaseUser.emailConfirmedAt != null,
            phone = profile?.phone ?: supabaseUser.phone,
            createdAt = profile?.createdAt ?: supabaseUser.createdAt
        )
    }

    /**
     * Determina el proveedor de autenticación basado en los metadata del usuario
     */
    private fun determineAuthProvider(supabaseUser: SupabaseAuthUser): AuthProvider {
        return when {
            supabaseUser.userMetadata.containsKey("iss") && 
            supabaseUser.userMetadata["iss"]?.toString()?.contains("google") == true -> AuthProvider.GOOGLE
            
            supabaseUser.userMetadata.containsKey("provider") && 
            supabaseUser.userMetadata["provider"]?.toString() == "google" -> AuthProvider.GOOGLE
            
            else -> AuthProvider.EMAIL
        }
    }

    /**
     * Convierte timestamp string a LocalDateTime
     */
    private fun parseTimestamp(timestamp: String?): LocalDateTime? {
        return try {
            timestamp?.let {
                LocalDateTime.parse(it.replace("Z", ""), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Mapea excepciones generales a AuthException específicas
     */
    fun mapToAuthException(exception: Throwable): AuthException {
        return when {
            // Errores de red
            exception.message?.contains("network", ignoreCase = true) == true ||
            exception.message?.contains("connection", ignoreCase = true) == true ||
            exception.message?.contains("timeout", ignoreCase = true) == true -> {
                AuthException.NetworkError(
                    message = "Error de conexión. Verifica tu internet e intenta nuevamente",
                    cause = exception
                )
            }

            // Errores de credenciales inválidas
            exception.message?.contains("invalid", ignoreCase = true) == true ||
            exception.message?.contains("wrong", ignoreCase = true) == true ||
            exception.message?.contains("incorrect", ignoreCase = true) == true -> {
                AuthException.InvalidCredentials(
                    "Las credenciales proporcionadas son incorrectas"
                )
            }

            // Errores de email no encontrado
            exception.message?.contains("not found", ignoreCase = true) == true ||
            exception.message?.contains("user not found", ignoreCase = true) == true -> {
                AuthException.UserNotFound(
                    "No se encontró una cuenta con este email"
                )
            }

            // Errores de email ya registrado
            exception.message?.contains("already", ignoreCase = true) == true ||
            exception.message?.contains("exists", ignoreCase = true) == true -> {
                AuthException.EmailAlreadyInUse(
                    "Ya existe una cuenta con este email"
                )
            }

            // Errores de contraseña débil
            exception.message?.contains("password", ignoreCase = true) == true &&
            (exception.message?.contains("weak", ignoreCase = true) == true ||
             exception.message?.contains("short", ignoreCase = true) == true) -> {
                AuthException.WeakPassword(
                    "La contraseña debe tener al menos 8 caracteres"
                )
            }

            // Errores de verificación de email
            exception.message?.contains("verification", ignoreCase = true) == true ||
            exception.message?.contains("confirm", ignoreCase = true) == true -> {
                AuthException.EmailNotVerified(
                    "Debes verificar tu email antes de continuar"
                )
            }

            // Errores de servidor
            exception.message?.contains("server", ignoreCase = true) == true ||
            exception.message?.contains("5", ignoreCase = true) == true -> {
                AuthException.ServerError(
                    message = "Error del servidor. Intenta más tarde",
                    cause = exception
                )
            }

            // Error genérico
            else -> {
                AuthException.UnknownError(
                    message = "Error inesperado: ${exception.message ?: "Error desconocido"}",
                    cause = exception
                )
            }
        }
    }

    /**
     * Mapea códigos de error específicos de Supabase a AuthException
     */
    fun mapSupabaseErrorToAuthException(errorCode: String?, errorMessage: String?): AuthException {
        return when (errorCode?.lowercase()) {
            "invalid_grant", "invalid_credentials" -> {
                AuthException.InvalidCredentials("Email o contraseña incorrectos")
            }
            "signup_disabled" -> {
                AuthException.SignupDisabled("El registro está temporalmente deshabilitado")
            }
            "email_not_confirmed" -> {
                AuthException.EmailNotVerified("Debes verificar tu email antes de continuar")
            }
            "user_not_found" -> {
                AuthException.UserNotFound("No se encontró una cuenta con este email")
            }
            "too_many_requests" -> {
                AuthException.RateLimitExceeded("Demasiados intentos. Intenta más tarde")
            }
            else -> {
                AuthException.UnknownError(
                    message = errorMessage ?: "Error de autenticación desconocido",
                    cause = null
                )
            }
        }
    }
}