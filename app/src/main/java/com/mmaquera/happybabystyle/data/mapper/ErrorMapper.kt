package com.mmaquera.happybabystyle.data.mapper

import android.net.http.NetworkException
import com.apollographql.apollo.api.ApolloResponse
import com.mmaquera.happybabystyle.domain.model.AuthException

/**
 * Mapper dedicado para el manejo y transformación de errores
 * Responsabilidad única: Transformación y manejo de errores de GraphQL y red
 */
class ErrorMapper {
    
    /**
     * Mapea errores de respuesta GraphQL a excepciones del dominio
     */
    /*fun mapGraphQLErrors(response: ApolloResponse<*>): Exception {
        val errorMessage = response.errors?.firstOrNull()?.message ?: "Error desconocido"
        return NetworkException.GraphQLError(errorMessage)
    }*/
    
    /**
     * Mapea errores de autenticación a excepciones del dominio
     */
    /*fun mapAuthError(errorMessage: String): AuthException {
        return when {
            errorMessage.contains("credenciales", ignoreCase = true) -> 
                AuthException.InvalidCredentials(errorMessage)
            errorMessage.contains("no autorizado", ignoreCase = true) -> 
                AuthException.Unauthorized(errorMessage)
            errorMessage.contains("token", ignoreCase = true) -> 
                AuthException.TokenExpired(errorMessage)
            else -> AuthException.UnknownError(errorMessage)
        }
    }*/
    
    /**
     * Mapea errores de red a excepciones del dominio
     */
    /*fun mapNetworkError(exception: Throwable): NetworkException {
        return when (exception) {
            is NetworkException -> exception
            else -> NetworkException.ConnectionError(exception.message ?: "Error de conexión", exception)
        }
    }*/
    
    /**
     * Verifica si una respuesta tiene errores
     */
    fun hasErrors(response: ApolloResponse<*>): Boolean {
        return response.hasErrors() || response.data == null
    }
    
    /**
     * Obtiene el mensaje de error principal de una respuesta
     */
    fun getErrorMessage(response: ApolloResponse<*>): String {
        return response.errors?.firstOrNull()?.message ?: "Error desconocido"
    }
}
