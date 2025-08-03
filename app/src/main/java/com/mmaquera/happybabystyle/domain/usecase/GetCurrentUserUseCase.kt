package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.User
import com.mmaquera.happybabystyle.domain.repository.AuthRepository

/**
 * Use Case para obtener el usuario actualmente autenticado
 */
class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Obtiene el usuario actualmente autenticado
     * @return User? Usuario actual o null si no hay sesión
     */
    suspend operator fun invoke(): User? {
        return authRepository.getCurrentUser()
    }
}