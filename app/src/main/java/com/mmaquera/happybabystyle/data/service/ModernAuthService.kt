package com.mmaquera.happybabystyle.data.service

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.mmaquera.happybabystyle.data.config.AppConfig
import com.mmaquera.happybabystyle.data.model.*
import com.mmaquera.happybabystyle.data.network.SupabaseClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.delay

/**
 * Servicio de autenticación moderno usando Credential Manager API
 * Reemplaza las APIs deprecadas de Google Sign-In
 */
class ModernAuthService(
    private val context: Context,
    private val supabaseClient: SupabaseClient
) {
    
    private val credentialManager = CredentialManager.create(context)
    private var currentUser: SupabaseUser? = null
    private var currentProfile: UserProfile? = null

    /**
     * Obtiene el Activity context desde cualquier context
     */
    private fun getActivityContext(): Activity? {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> {
                var baseContext = context.baseContext
                while (baseContext is ContextWrapper) {
                    if (baseContext is Activity) return baseContext
                    baseContext = baseContext.baseContext
                }
                null
            }
            else -> null
        }
    }

    /**
     * Inicia el flujo de autenticación con Google usando Credential Manager
     */
    suspend fun signInWithGoogle(): AuthResult {
        return try {
            // Validar que tenemos Activity context
            val activityContext = getActivityContext()
            if (activityContext == null) {
                return AuthResult.Error(
                    "Se necesita Activity context para mostrar UI de credenciales. " +
                    "Context actual: ${context::class.simpleName}"
                )
            }

            println("🔑 GoogleAuth: Iniciando con Activity context: ${activityContext::class.simpleName}")
            println("🔑 GoogleAuth: Verificando disponibilidad de Google Play Services...")
            
            // Verificar Google Play Services
            try {
                val availabilityResult = com.google.android.gms.common.GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(activityContext)
                if (availabilityResult != com.google.android.gms.common.ConnectionResult.SUCCESS) {
                    println("❌ GoogleAuth: Google Play Services no disponible: $availabilityResult")
                    return AuthResult.Error("Google Play Services no está disponible")
                }
                println("✅ GoogleAuth: Google Play Services disponible")
            } catch (e: Exception) {
                println("⚠️ GoogleAuth: No se pudo verificar Google Play Services: ${e.message}")
            }
            
            // Configurar opciones de Google ID
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(AppConfig.GOOGLE_CLIENT_ID)
                .setAutoSelectEnabled(false) // Cambiar a false para forzar selector
                .setNonce(null) // Opcional: quitar nonce por ahora
                .build()

            println("🔑 GoogleAuth: Client ID configurado: ${AppConfig.GOOGLE_CLIENT_ID}")
            println("🔑 GoogleAuth: Package name: com.mmaquera.happybabystyle")
            println("🔑 GoogleAuth: SHA-1: 38:DC:91:10:E7:24:AF:21:FB:B2:22:AF:59:A7:78:EA:56:5D:57:06")

            // Crear request de credenciales
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            println("🔑 GoogleAuth: Lanzando selector de credenciales...")

            // Obtener credenciales - usar Activity context
            val credentialResponse = credentialManager.getCredential(
                request = request,
                context = activityContext  // ✅ Activity context específico
            )

            println("🔑 GoogleAuth: Credenciales obtenidas exitosamente")

            // Procesar respuesta
            handleCredentialResponse(credentialResponse)
            
        } catch (e: GetCredentialException) {
            println("❌ GoogleAuth Error: ${e.message}")
            println("❌ GoogleAuth Error Type: ${e::class.simpleName}")
            println("❌ GoogleAuth Error Code: ${e.errorMessage}")
            
            // TEMPORAL: Si es error 28444, usar mock para continuar desarrollo
            if (e.message?.contains("28444") == true) {
                println("🔧 Error 28444 detectado - usando modo mock para desarrollo")
                return createMockAuthResult()
            }
            
            when (e.type) {
                "androidx.credentials.exceptions.NoCredentialException" -> {
                    println("🔍 No hay credenciales disponibles - probablemente OAuth consent screen no configurado")
                }
                "androidx.credentials.exceptions.GetCredentialInterruptedException" -> {
                    println("🔍 Usuario canceló el flujo de credenciales")
                }
                else -> {
                    println("🔍 Error tipo: ${e.type}")
                }
            }
            AuthResult.Error("Error al obtener credenciales: ${e.message} (Tipo: ${e::class.simpleName})", e)
        } catch (e: Exception) {
            println("❌ GoogleAuth Error inesperado: ${e.message}")
            AuthResult.Error("Error inesperado: ${e.message}", e)
        }
    }

    /**
     * Procesa la respuesta de credenciales de Google
     */
    private suspend fun handleCredentialResponse(response: GetCredentialResponse): AuthResult {
        return try {
            when (val credential = response.credential) {
                is GoogleIdTokenCredential -> {
                    // Extraer ID token de Google
                    val idToken = credential.idToken
                    
                    // Intercambiar token con Supabase
                    exchangeTokenWithSupabase(idToken)
                }
                else -> {
                    AuthResult.Error("Tipo de credencial no soportado")
                }
            }
        } catch (e: Exception) {
            AuthResult.Error("Error procesando credenciales: ${e.message}", e)
        }
    }

    /**
     * Intercambia el ID token de Google con Supabase para obtener sesión
     */
    private suspend fun exchangeTokenWithSupabase(idToken: String): AuthResult {
        return try {
            val authRequest = SupabaseAuthRequest(
                provider = "google",
                idToken = idToken
            )

            val response = supabaseClient.httpClient.post("${supabaseClient.getBaseUrl()}/auth/v1/token") {
                contentType(ContentType.Application.Json)
                header("apikey", supabaseClient.getAnonKey())
                setBody(authRequest)
            }

            if (response.status.isSuccess()) {
                val authResponse = response.body<SupabaseAuthResponse>()
                
                // Actualizar token en el cliente
                supabaseClient.updateAccessToken(authResponse.accessToken)
                currentUser = authResponse.user

                // Intentar obtener o crear perfil
                val profile = getOrCreateUserProfile(authResponse.user)
                currentProfile = profile

                AuthResult.Success(authResponse.user, profile)
            } else {
                val errorBody = response.bodyAsText()
                AuthResult.Error("Error de autenticación: $errorBody")
            }

        } catch (e: Exception) {
            AuthResult.Error("Error intercambiando token: ${e.message}", e)
        }
    }

    /**
     * Obtiene o crea el perfil del usuario en nuestra base de datos
     */
    private suspend fun getOrCreateUserProfile(user: SupabaseUser): UserProfile? {
        return try {
            // Intentar obtener perfil existente
            val existingProfile = getUserProfile(user.id)
            
            if (existingProfile != null) {
                existingProfile
            } else {
                // Crear nuevo perfil
                createUserProfile(user)
            }
        } catch (e: Exception) {
            null // Si falla, continuar sin perfil
        }
    }

    /**
     * Obtiene el perfil del usuario de la base de datos
     */
    private suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val response = supabaseClient.httpClient.get("${supabaseClient.getBaseUrl()}/rest/v1/user_profiles") {
                header("apikey", supabaseClient.getAnonKey())
                header("Authorization", "Bearer ${supabaseClient.getAccessToken()}")
                parameter("user_id", "eq.$userId")
                parameter("select", "*")
            }

            if (response.status.isSuccess()) {
                val profiles = response.body<List<UserProfile>>()
                profiles.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Crea un nuevo perfil de usuario
     */
    private suspend fun createUserProfile(user: SupabaseUser): UserProfile? {
        return try {
            val profileRequest = CreateUserProfileRequest(
                userId = user.id,
                firstName = user.userMetadata.givenName ?: user.userMetadata.name?.split(" ")?.firstOrNull(),
                lastName = user.userMetadata.familyName ?: user.userMetadata.name?.split(" ")?.drop(1)?.joinToString(" "),
                avatarUrl = user.userMetadata.avatarUrl ?: user.userMetadata.picture
            )

            val response = supabaseClient.httpClient.post("${supabaseClient.getBaseUrl()}/rest/v1/user_profiles") {
                contentType(ContentType.Application.Json)
                header("apikey", supabaseClient.getAnonKey())
                header("Authorization", "Bearer ${supabaseClient.getAccessToken()}")
                setBody(profileRequest)
            }

            if (response.status.isSuccess()) {
                response.body<UserProfile>()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Cierra sesión del usuario
     */
    suspend fun signOut(): AuthResult {
        return try {
            // Limpiar autenticación local
            supabaseClient.clearAuth()
            currentUser = null
            currentProfile = null

            // TODO: Revocar token en Supabase si es necesario
            
            AuthResult.Success(
                user = SupabaseUser(
                    id = "",
                    aud = "",
                    role = "",
                    email = "",
                    emailConfirmedAt = null,
                    phone = null,
                    confirmedAt = null,
                    lastSignInAt = null,
                    appMetadata = AppMetadata("", listOf()),
                    userMetadata = UserMetadata(null, null, null, null, null, null, null, null, null, null, null),
                    identities = null,
                    createdAt = "",
                    updatedAt = ""
                ),
                profile = null
            )
        } catch (e: Exception) {
            AuthResult.Error("Error cerrando sesión: ${e.message}", e)
        }
    }

    /**
     * Obtiene el usuario actual
     */
    fun getCurrentUser(): SupabaseUser? = currentUser

    /**
     * Obtiene el perfil actual
     */
    fun getCurrentProfile(): UserProfile? = currentProfile

    /**
     * Verifica si hay un usuario autenticado
     */
    fun isAuthenticated(): Boolean = currentUser != null
    
    /**
     * TEMPORAL: Crea resultado de autenticación mock para desarrollo
     * mientras se resuelve error 28444 de Google Cloud Console
     */
    private suspend fun createMockAuthResult(): AuthResult {
        println("🎭 Creando usuario mock para desarrollo...")
        delay(1000) // Simular delay de red
        
        val mockUser = SupabaseUser(
            id = "mock-user-${System.currentTimeMillis()}",
            aud = "authenticated", 
            role = "authenticated",
            email = "developer@happybabystyle.com",
            emailConfirmedAt = "2025-01-01T00:00:00.000Z",
            phone = null,
            confirmedAt = "2025-01-01T00:00:00.000Z",
            lastSignInAt = "2025-01-01T00:00:00.000Z",
            appMetadata = AppMetadata(provider = "mock", providers = listOf("mock")),
            userMetadata = UserMetadata(
                avatarUrl = null,
                email = "developer@happybabystyle.com",
                emailVerified = true,
                fullName = "Developer User",
                givenName = "Developer", 
                familyName = "User",
                iss = "mock",
                name = "Developer User",
                picture = null,
                providerId = "mock",
                sub = "mock-sub"
            ),
            identities = emptyList(),
            createdAt = "2025-01-01T00:00:00.000Z",
            updatedAt = "2025-01-01T00:00:00.000Z"
        )
        
        val mockProfile = UserProfile(
            id = "mock-profile-${System.currentTimeMillis()}",
            userId = mockUser.id,
            firstName = "Developer",
            lastName = "User",
            phone = null,
            birthDate = null,
            avatarUrl = null,
            createdAt = "2025-01-01T00:00:00.000Z",
            updatedAt = "2025-01-01T00:00:00.000Z"
        )
        
        currentUser = mockUser
        currentProfile = mockProfile
        
        println("🎭 Usuario mock creado: ${mockUser.email}")
        return AuthResult.Success(mockUser, mockProfile)
    }
}