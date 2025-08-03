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
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InV1bXdqaG9xa2lpeXh1cGVycndzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQyMzM0MTAsImV4cCI6MjA2OTgwOTQxMH0.oJN3ioz4BlJeN3eU6hnOk-oTsY2nonUvLn3tFhuBvPo" // CAMBIAR POR TU CLAVE
    
    // Google OAuth Configuration  
    // TODO: Obtén este ID desde Google Cloud Console → APIs & Services → Credentials
    // NOTA: Debe ser un Android Client ID, NO Web Client ID
    const val GOOGLE_CLIENT_ID = "581901746036-h6tjcdsg8jiniilmnjfhrfpf0d8471c2.apps.googleusercontent.com" // ⭐ NUEVO Android Client ID - Creado el 3 ago 2025
    
    // App Configuration
    const val APP_NAME = "Happy Baby Style"
    const val APP_VERSION = "1.0.0"
}