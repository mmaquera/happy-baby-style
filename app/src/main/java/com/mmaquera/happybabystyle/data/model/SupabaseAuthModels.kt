package com.mmaquera.happybabystyle.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelos específicos para autenticación con Supabase MCP
 * Representan la estructura de datos que maneja Supabase Auth
 */

/**
 * Usuario de Supabase Auth obtenido directamente del MCP
 */
@Serializable
data class SupabaseAuthUser(
    val id: String,
    val email: String,
    val aud: String? = null,
    val role: String? = null,
    @SerialName("email_confirmed_at")
    val emailConfirmedAt: String? = null,
    @SerialName("phone_confirmed_at")
    val phoneConfirmedAt: String? = null,
    @SerialName("last_sign_in_at")
    val lastSignInAt: String? = null,
    @SerialName("app_metadata")
    val appMetadata: Map<String, String> = emptyMap(),
    @SerialName("user_metadata")
    val userMetadata: Map<String, String> = emptyMap(),
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val phone: String? = null,
    @SerialName("confirmed_at")
    val confirmedAt: String? = null,
    @SerialName("is_anonymous")
    val isAnonymous: Boolean = false
)

/**
 * Perfil de usuario de la tabla user_profiles
 */
@Serializable
data class SupabaseUserProfile(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    val phone: String? = null,
    @SerialName("birth_date")
    val birthDate: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)



/**
 * Resultado de autenticación exitosa
 */
@Serializable
data class SupabaseAuthResult(
    val user: SupabaseAuthUser,
    @SerialName("session")
    val session: SupabaseSession? = null
)

/**
 * Sesión de autenticación de Supabase
 */
@Serializable
data class SupabaseSession(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("token_type")
    val tokenType: String = "bearer",
    val user: SupabaseAuthUser
)

/**
 * Error de autenticación de Supabase
 */
@Serializable
data class SupabaseAuthError(
    val message: String,
    val error: String? = null,
    @SerialName("error_description")
    val errorDescription: String? = null,
    val code: String? = null
)