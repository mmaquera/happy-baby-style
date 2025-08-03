package com.mmaquera.happybabystyle.data.config

/**
 * Configuración de la aplicación
 * 
 * IMPORTANTE: Actualiza estos valores con tus claves reales
 */
object AppConfig {
    
    // Supabase Configuration
    const val SUPABASE_URL = "https://uumwjhoqkiiyxuperrws.supabase.co"
    
    // TODO: Obtén esta clave desde Supabase Dashboard → Settings → API
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." // CAMBIAR POR TU CLAVE
    
    // Google OAuth Configuration  
    // TODO: Obtén este ID desde Google Cloud Console → APIs & Services → Credentials
    const val GOOGLE_CLIENT_ID = "tu_google_client_id.googleusercontent.com" // CAMBIAR POR TU CLIENT ID
    
    // App Configuration
    const val APP_NAME = "Happy Baby Style"
    const val APP_VERSION = "1.0.0"
}