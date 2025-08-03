package com.mmaquera.happybabystyle.util

import android.content.Context
import com.mmaquera.happybabystyle.data.network.SupabaseClient
import com.mmaquera.happybabystyle.data.service.ModernAuthService

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
     * Limpia las instancias (útil para testing)
     */
    fun clear() {
        synchronized(this) {
            supabaseClient = null
            // modernAuthService ya no es singleton, se crea cada vez con Activity context
        }
    }
}