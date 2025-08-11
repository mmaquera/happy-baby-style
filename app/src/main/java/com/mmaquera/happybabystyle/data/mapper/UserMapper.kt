package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.domain.model.AuthProvider
import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.graphql.GetCurrentUserQuery
import com.mmaquera.happybabystyle.graphql.fragment.UserBasicInfo

/**
 * Mapper dedicado para transformar datos de usuario de GraphQL al modelo de dominio
 * Responsabilidad única: Transformación de datos de usuario
 */


/**
 * Mapea UserBasicInfo de GraphQL a User del dominio
 */
fun UserBasicInfo.mapFromUserBasicInfo (): User {
    return User(
        id = id,
        email = email,
        firstName = null, // No disponible en UserBasicInfo
        lastName = null, // No disponible en UserBasicInfo
        avatarUrl = null, // No disponible en UserBasicInfo
        phone = null, // No disponible en UserBasicInfo
        isEmailVerified = false, // No disponible en UserBasicInfo
        provider = AuthProvider.EMAIL, // Asumir EMAIL por defecto
        createdAt = createdAt.toString()
    )
}

/**
 * Mapea GetCurrentUserQuery.CurrentUser a User del dominio
 */
fun GetCurrentUserQuery.CurrentUser.mapFromCurrentUserQuery(): User {
    return User(
        id = userBasicInfo.id,
        email = userBasicInfo.email,
        firstName = null, // No disponible en UserBasicInfo
        lastName = null, // No disponible en UserBasicInfo
        avatarUrl = null, // No disponible en UserBasicInfo
        phone = null, // No disponible en UserBasicInfo
        isEmailVerified = false, // No disponible en UserBasicInfo
        provider = AuthProvider.EMAIL, // Asumir EMAIL por defecto
        createdAt = userBasicInfo.createdAt.toString()
    )
}

