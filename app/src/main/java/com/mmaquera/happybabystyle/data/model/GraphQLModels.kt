package com.mmaquera.happybabystyle.data.model

import kotlinx.serialization.Serializable

/**
 * Modelos de datos para GraphQL
 * Reemplazan los modelos de Supabase para migración completa
 */

@Serializable
data class GraphQLAuthResponse(
    val accessToken: String,
    val refreshToken: String?,
    val success: Boolean,
    val message: String?,
    val user: GraphQLUser?
)

@Serializable
data class GraphQLUser(
    val id: String,
    val email: String,
    val role: String,
    val isActive: Boolean,
    val emailVerified: Boolean,
    val lastLoginAt: String?,
    val createdAt: String,
    val updatedAt: String,
    val profile: GraphQLUserProfile?
) {
    val fullName: String
        get() = profile?.let { "${it.firstName} ${it.lastName}" } ?: email
    
    val displayName: String
        get() = profile?.firstName ?: email.split("@").firstOrNull() ?: email
}

@Serializable
data class GraphQLUserProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?
) {
    val fullName: String
        get() = "$firstName $lastName"
}

@Serializable
data class GraphQLRequest(
    val query: String,
    val variables: kotlinx.serialization.json.JsonObject = kotlinx.serialization.json.JsonObject(emptyMap())
)

@Serializable
data class GraphQLResponse(
    val data: kotlinx.serialization.json.JsonObject? = null,
    val errors: List<GraphQLError>? = null
)

@Serializable
data class GraphQLError(
    val message: String,
    val locations: List<GraphQLLocation>? = null,
    val path: List<kotlinx.serialization.json.JsonPrimitive>? = null
)

@Serializable
data class GraphQLLocation(
    val line: Int,
    val column: Int
) 