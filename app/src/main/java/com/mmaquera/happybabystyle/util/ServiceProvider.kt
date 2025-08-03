package com.mmaquera.happybabystyle.util

import android.content.Context
import com.mmaquera.happybabystyle.data.network.SupabaseClient
import com.mmaquera.happybabystyle.data.service.ModernAuthService
import com.mmaquera.happybabystyle.data.repository.AuthRepositoryImpl
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import com.mmaquera.happybabystyle.domain.usecase.LoginWithEmailUseCase
import com.mmaquera.happybabystyle.domain.usecase.LoginWithGoogleUseCase
import com.mmaquera.happybabystyle.domain.usecase.ValidateCredentialsUseCase

/**
 * Proveedor temporal de servicios hasta que se resuelva Hilt
 */
object ServiceProvider {
    
    @Volatile
    private var supabaseClient: SupabaseClient? = null
    
    fun getSupabaseClient(): SupabaseClient {
        return supabaseClient ?: synchronized(this) {
            supabaseClient ?: SupabaseClient().also { supabaseClient = it }
        }
    }
    
    /**
     * Crea ModernAuthService con Activity context (NO usar Singleton)
     * CredentialManager necesita Activity context para mostrar UI
     */
    fun getModernAuthService(context: Context): ModernAuthService {
        return ModernAuthService(
            context = context, // ✅ Activity context directo, no applicationContext
            supabaseClient = getSupabaseClient()
        )
    }
    
    /**
     * Crea AuthRepository siguiendo Clean Architecture
     * Usa AuthRepositoryImpl con ModernAuthService
     */
    fun getAuthRepository(context: Context): AuthRepository {
        return AuthRepositoryImpl(
            modernAuthService = getModernAuthService(context)
        )
    }
    
    /**
     * Crea LoginWithEmailUseCase con sus dependencias
     */
    fun getLoginWithEmailUseCase(context: Context): LoginWithEmailUseCase {
        return LoginWithEmailUseCase(
            authRepository = getAuthRepository(context)
        )
    }
    
    /**
     * Crea LoginWithGoogleUseCase con sus dependencias
     */
    fun getLoginWithGoogleUseCase(context: Context): LoginWithGoogleUseCase {
        return LoginWithGoogleUseCase(
            authRepository = getAuthRepository(context)
        )
    }
    
    /**
     * Crea ValidateCredentialsUseCase con sus dependencias
     */
    fun getValidateCredentialsUseCase(context: Context): ValidateCredentialsUseCase {
        return ValidateCredentialsUseCase(
            authRepository = getAuthRepository(context)
        )
    }
    
    /**
     * Limpia las instancias (útil para testing)
     */
    fun clear() {
        synchronized(this) {
            supabaseClient = null
            // modernAuthService ya no es singleton, se crea cada vez con Activity context
        }
    }
}