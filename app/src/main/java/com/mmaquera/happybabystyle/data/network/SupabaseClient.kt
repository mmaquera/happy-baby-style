package com.mmaquera.happybabystyle.data.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
// import javax.inject.Inject  // Temporalmente deshabilitado
// import javax.inject.Singleton  // Temporalmente deshabilitado
import com.mmaquera.happybabystyle.data.config.AppConfig

// @Singleton  // Temporalmente deshabilitado
class SupabaseClient() {
    
    companion object {
        // Configuración de Supabase desde AppConfig
        private val SUPABASE_URL = AppConfig.SUPABASE_URL
        private val SUPABASE_ANON_KEY = AppConfig.SUPABASE_ANON_KEY
        
        // Endpoints de Supabase
        const val AUTH_ENDPOINT = "/auth/v1"
        const val REST_ENDPOINT = "/rest/v1"
        const val STORAGE_ENDPOINT = "/storage/v1"
    }
    
    private var accessToken: String? = null
    
    val httpClient = HttpClient(Android) {
        
        // Configurar serialización JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        
        // Configurar logging para APIs HTTP
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.INFO
            filter { request ->
                // Log solo requests a Supabase
                request.url.host.contains("supabase") || 
                request.url.toString().contains("auth") ||
                request.url.toString().contains("rest")
            }
        }
    }
    
    /**
     * Actualizar el token de acceso para requests autenticados
     */
    fun updateAccessToken(token: String?) {
        accessToken = token
    }
    
    /**
     * Obtener la URL base de Supabase
     */
    fun getBaseUrl(): String = SUPABASE_URL
    
    /**
     * Obtener la clave anónima
     */
    fun getAnonKey(): String = SUPABASE_ANON_KEY
    
    /**
     * Obtener el token de acceso actual (o anon key)
     */
    fun getAccessToken(): String = accessToken ?: SUPABASE_ANON_KEY
    
    /**
     * Limpiar autenticación
     */
    fun clearAuth() {
        accessToken = null
    }
}