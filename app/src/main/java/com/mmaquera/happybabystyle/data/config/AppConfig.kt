package com.mmaquera.happybabystyle.data.config

/**
 * Configuración de la aplicación
 * 
 * Migración completa a GraphQL - Eliminando dependencias de Supabase
 */
object AppConfig {
    
    // GraphQL Configuration
    const val GRAPHQL_ENDPOINT = "http://localhost:3001/graphql"
    const val GRAPHQL_ENDPOINT_EMULATOR = "http://10.0.2.2:3001/graphql"
    
    // Google OAuth Configuration  
    // TODO: Obtén este ID desde Google Cloud Console → APIs & Services → Credentials
    // NOTA: Debe ser un Android Client ID, NO Web Client ID
    const val GOOGLE_CLIENT_ID = "581901746036-h6tjcdsg8jiniilmnjfhrfpf0d8471c2.apps.googleusercontent.com"
    
    // App Configuration
    const val APP_NAME = "Happy Baby Style"
    const val APP_VERSION = "1.0.0"
    
    // Network Configuration
    const val NETWORK_TIMEOUT_SECONDS = 30L
    const val MAX_RETRY_ATTEMPTS = 3
}