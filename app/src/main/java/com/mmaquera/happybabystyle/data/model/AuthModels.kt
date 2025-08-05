package com.mmaquera.happybabystyle.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelos para autenticación con Supabase
 */

@Serializable
data class SupabaseAuthRequest(
    @SerialName("provider")
    val provider: String,
    @SerialName("id_token") 
    val idToken: String,
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("options")
    val options: AuthOptions? = null
)

@Serializable
data class SignUpRequestData(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("name")
    val name: String
)

@Serializable
data class SignUpRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("data")
    val data: SignUpRequestData
)

@Serializable
data class AuthOptions(
    @SerialName("redirect_to")
    val redirectTo: String? = null,
    @SerialName("scopes")
    val scopes: String? = null,
    @SerialName("query_params")
    val queryParams: Map<String, String>? = null
)

@Serializable
data class SupabaseAuthResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("expires_at")
    val expiresAt: Long,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("user")
    val user: SupabaseUser
)

@Serializable
data class SupabaseUser(
    @SerialName("id")
    val id: String,
    @SerialName("aud")
    val aud: String,
    @SerialName("role")
    val role: String,
    @SerialName("email")
    val email: String,
    @SerialName("email_confirmed_at")
    val emailConfirmedAt: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("confirmed_at")
    val confirmedAt: String? = null,
    @SerialName("confirmation_sent_at")
    val confirmationSentAt: String? = null,
    @SerialName("last_sign_in_at")
    val lastSignInAt: String? = null,
    @SerialName("app_metadata")
    val appMetadata: AppMetadata? = null,
    @SerialName("user_metadata")
    val userMetadata: UserMetadata? = null,
    @SerialName("identities")
    val identities: List<Identity>? = null,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("is_anonymous")
    val isAnonymous: Boolean? = null
)

@Serializable
data class AppMetadata(
    @SerialName("provider")
    val provider: String,
    @SerialName("providers")
    val providers: List<String>
)

@Serializable
data class UserMetadata(
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("email_verified")
    val emailVerified: Boolean? = null,
    @SerialName("phone_verified")
    val phoneVerified: Boolean? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("given_name")
    val givenName: String? = null,
    @SerialName("family_name")
    val familyName: String? = null,
    @SerialName("iss")
    val iss: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("picture")
    val picture: String? = null,
    @SerialName("provider_id")
    val providerId: String? = null,
    @SerialName("sub")
    val sub: String? = null
)

@Serializable
data class Identity(
    @SerialName("identity_id")
    val identityId: String,
    @SerialName("id")
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("identity_data")
    val identityData: UserMetadata? = null,
    @SerialName("provider")
    val provider: String,
    @SerialName("last_sign_in_at")
    val lastSignInAt: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("email")
    val email: String? = null
)

/**
 * Modelo para el perfil de usuario en nuestra DB
 */
@Serializable
data class UserProfile(
    @SerialName("id")
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("birth_date")
    val birthDate: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

/**
 * Request para crear/actualizar perfil
 */
@Serializable
data class CreateUserProfileRequest(
    @SerialName("user_id")
    val userId: String,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)

/**
 * Response de error de Supabase
 */
@Serializable
data class SupabaseError(
    @SerialName("error")
    val error: String,
    @SerialName("error_description")
    val errorDescription: String,
    @SerialName("message")
    val message: String? = null
)