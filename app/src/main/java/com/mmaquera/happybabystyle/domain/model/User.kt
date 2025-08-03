package com.mmaquera.happybabystyle.domain.model

/**
 * Domain model para Usuario
 * Representa la entidad de usuario en el dominio de la aplicación
 */
data class User(
    val id: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatarUrl: String? = null,
    val phone: String? = null,
    val isEmailVerified: Boolean = false,
    val provider: AuthProvider,
    val createdAt: String? = null
)

/**
 * Enum para los proveedores de autenticación
 */
enum class AuthProvider {
    EMAIL,
    GOOGLE,
    FACEBOOK,
    APPLE
}