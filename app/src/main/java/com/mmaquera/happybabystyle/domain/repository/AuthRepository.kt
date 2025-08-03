package com.mmaquera.happybabystyle.domain.repository

import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.LoginCredentials
import com.mmaquera.happybabystyle.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface para operaciones de autenticación
 * Define el contrato para todas las operaciones de autenticación en el dominio
 */
interface AuthRepository {
    
    /**
     * Realiza login con credenciales específicas
     * @param credentials Credenciales de login (email/password, Google, etc.)
     * @return Flow<AuthResult> Resultado de la autenticación
     */
    suspend fun login(credentials: LoginCredentials): Flow<AuthResult>
    
    /**
     * Cierra la sesión del usuario actual
     * @return Flow<AuthResult> Resultado de la operación
     */
    suspend fun logout(): Flow<AuthResult>
    
    /**
     * Obtiene el usuario actualmente autenticado
     * @return User? Usuario actual o null si no hay sesión
     */
    suspend fun getCurrentUser(): User?
    
    /**
     * Verifica si hay un usuario actualmente autenticado
     * @return Boolean true si hay sesión activa
     */
    suspend fun isUserAuthenticated(): Boolean
    
    /**
     * Observa el estado de autenticación
     * @return Flow<User?> Flujo del usuario actual
     */
    fun observeAuthState(): Flow<User?>
    
    /**
     * Refresca el token de acceso
     * @return Flow<AuthResult> Resultado de la operación
     */
    suspend fun refreshToken(): Flow<AuthResult>
    
    /**
     * Valida las credenciales de email y contraseña
     * @param email Email a validar
     * @param password Contraseña a validar
     * @return Boolean true si las credenciales son válidas
     */
    fun validateCredentials(email: String, password: String): Boolean
    
    /**
     * Valida formato de email
     * @param email Email a validar
     * @return Boolean true si el formato es válido
     */
    fun isValidEmail(email: String): Boolean
    
    /**
     * Valida fortaleza de contraseña
     * @param password Contraseña a validar
     * @return Boolean true si cumple los requisitos
     */
    fun isValidPassword(password: String): Boolean
}