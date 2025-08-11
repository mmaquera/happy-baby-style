package com.mmaquera.happybabystyle.data.mapper

import android.util.Patterns

/**
 * Mapper dedicado para validaciones de credenciales
 * Responsabilidad única: Validación de datos de entrada
 */
class ValidationMapper {
    
    /**
     * Valida formato de email
     */
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    /**
     * Valida formato de contraseña
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    /**
     * Valida credenciales completas
     */
    fun validateCredentials(email: String, password: String): Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }
    
    /**
     * Valida nombre completo para registro
     */
    fun validateFullName(name: String): Boolean {
        return name.trim().length >= 2
    }
    
    /**
     * Extrae nombre y apellido del nombre completo
     */
    fun extractNameParts(fullName: String): Pair<String, String> {
        val parts = fullName.trim().split("\\s+".toRegex())
        val firstName = parts.firstOrNull() ?: ""
        val lastName = parts.drop(1).joinToString(" ")
        return Pair(firstName, lastName)
    }
}
