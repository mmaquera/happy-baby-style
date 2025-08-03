package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.data.service.AuthResult as DataAuthResult
import com.mmaquera.happybabystyle.domain.model.AuthResult as DomainAuthResult

/**
 * Mapper para convertir entre modelos de servicio y dominio
 */
object ServiceMapper {
    
    /**
     * Mapea AuthResult de la capa de datos al dominio
     */
    fun mapToDomainAuthResult(dataResult: DataAuthResult): DomainAuthResult {
        return when (dataResult) {
            is DataAuthResult.Success -> {
                val domainUser = AuthMapper.mapToUser(dataResult.user, dataResult.profile)
                DomainAuthResult.Success(
                    user = domainUser,
                    accessToken = "", // Los services no manejan access token directamente
                    refreshToken = null
                )
            }
            is DataAuthResult.Error -> {
                DomainAuthResult.Error(
                    AuthMapper.mapToAuthException(
                        dataResult.exception ?: RuntimeException(dataResult.message)
                    )
                )
            }
            is DataAuthResult.Loading -> {
                DomainAuthResult.Loading
            }
        }
    }
    
    /**
     * Mapea de String error a AuthResult.Error del dominio
     */
    fun mapErrorToDomainAuthResult(errorMessage: String, cause: Throwable? = null): DomainAuthResult {
        return DomainAuthResult.Error(
            AuthMapper.mapToAuthException(
                cause ?: RuntimeException(errorMessage)
            )
        )
    }
}