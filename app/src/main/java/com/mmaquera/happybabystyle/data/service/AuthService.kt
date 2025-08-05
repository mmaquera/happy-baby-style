package com.mmaquera.happybabystyle.data.service

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mmaquera.happybabystyle.data.model.*
import com.mmaquera.happybabystyle.data.mapper.AuthMapper
import com.mmaquera.happybabystyle.data.network.SupabaseClient
import com.mmaquera.happybabystyle.domain.model.AuthResult
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.mmaquera.happybabystyle.data.config.AppConfig



@Singleton
class AuthService @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val context: Context
) {
    
    companion object {
        // Google Client ID desde AppConfig
        private val GOOGLE_CLIENT_ID = AppConfig.GOOGLE_CLIENT_ID
    }
    
    private var currentUser: SupabaseUser? = null
    private var currentProfile: UserProfile? = null
    private var googleSignInClient: GoogleSignInClient
    
    init {
        // Configurar Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()
        
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }
    
    /**
     * Obtener cliente de Google Sign-In para usar en Activity
     */
    fun getGoogleSignInClient(): GoogleSignInClient = googleSignInClient
    
    /**
     * Procesar resultado de Google Sign-In y autenticar con Supabase
     */
    suspend fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>): AuthResult {
        return try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            
            if (idToken != null) {
                authenticateWithSupabase(idToken, account)
            } else {
                AuthResult.Error(AuthMapper.mapToAuthException(RuntimeException("No se pudo obtener el token de Google")))
            }
        } catch (e: ApiException) {
            AuthResult.Error(AuthMapper.mapToAuthException(e))
        }
    }
    
    /**
     * Autenticar con Supabase usando Google ID Token
     */
    private suspend fun authenticateWithSupabase(
        idToken: String, 
        googleAccount: GoogleSignInAccount
    ): AuthResult {
        return try {
            // 1. Crear request para Supabase Auth
            val authRequest = SupabaseAuthRequest(
                provider = "google",
                idToken = idToken
            )
            
            // 2. Llamar a Supabase Auth API
            val response = supabaseClient.httpClient.post("${supabaseClient.getBaseUrl()}${SupabaseClient.AUTH_ENDPOINT}/token") {
                parameter("grant_type", "id_token")
                header("apikey", supabaseClient.getAnonKey())
                header("Content-Type", "application/json")
                setBody(authRequest)
            }
            
            if (response.status == HttpStatusCode.OK) {
                val authResponse: SupabaseAuthResponse = response.body()
                
                // 3. Guardar token de acceso
                supabaseClient.updateAccessToken(authResponse.accessToken)
                currentUser = authResponse.user
                
                // 4. Obtener o crear perfil de usuario
                val profile = getOrCreateUserProfile(authResponse.user, googleAccount)
                currentProfile = profile
                
                AuthResult.Success(
                    user = AuthMapper.mapToUser(authResponse.user, profile),
                    accessToken = authResponse.accessToken,
                    refreshToken = authResponse.refreshToken
                )
            } else {
                val errorBody = response.bodyAsText()
                AuthResult.Error(AuthMapper.mapToAuthException(RuntimeException("Error de autenticación: $errorBody")))
            }
            
        } catch (e: Exception) {
            AuthResult.Error(AuthMapper.mapToAuthException(e))
        }
    }
    
    /**
     * Obtener o crear perfil de usuario en nuestra DB
     */
    private suspend fun getOrCreateUserProfile(
        user: SupabaseUser,
        googleAccount: GoogleSignInAccount
    ): UserProfile? {
        return try {
            // 1. Intentar obtener perfil existente
            val existingProfile = getUserProfile(user.id)
            
            if (existingProfile != null) {
                existingProfile
            } else {
                // 2. Crear nuevo perfil con datos de Google
                createUserProfile(user, googleAccount)
            }
        } catch (e: Exception) {
            null // No es crítico si falla
        }
    }
    
    /**
     * Obtener perfil de usuario existente
     */
    private suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val response = supabaseClient.httpClient.get("${supabaseClient.getBaseUrl()}${SupabaseClient.REST_ENDPOINT}/user_profiles") {
                parameter("user_id", "eq.$userId")
                parameter("select", "*")
                header("apikey", supabaseClient.getAnonKey())
                header("Authorization", "Bearer ${supabaseClient.getAccessToken()}")
                header("Content-Type", "application/json")
            }
            
            if (response.status == HttpStatusCode.OK) {
                val profiles: List<UserProfile> = response.body()
                profiles.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Crear nuevo perfil de usuario
     */
    private suspend fun createUserProfile(
        user: SupabaseUser,
        googleAccount: GoogleSignInAccount
    ): UserProfile? {
        return try {
            val profileRequest = CreateUserProfileRequest(
                userId = user.id,
                firstName = googleAccount.givenName,
                lastName = googleAccount.familyName,
                avatarUrl = googleAccount.photoUrl?.toString()
            )
            
            val response = supabaseClient.httpClient.post("${supabaseClient.getBaseUrl()}${SupabaseClient.REST_ENDPOINT}/user_profiles") {
                header("apikey", supabaseClient.getAnonKey())
                header("Authorization", "Bearer ${supabaseClient.getAccessToken()}")
                header("Content-Type", "application/json")
                header("Prefer", "return=representation")
                setBody(profileRequest)
            }
            
            if (response.status == HttpStatusCode.Created) {
                val profiles: List<UserProfile> = response.body()
                profiles.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Cerrar sesión
     */
    suspend fun signOut(): Result<Unit> {
        return try {
            // 1. Cerrar sesión en Google
            googleSignInClient.signOut().await()
            
            // 2. Cerrar sesión en Supabase
            supabaseClient.httpClient.post("${supabaseClient.getBaseUrl()}${SupabaseClient.AUTH_ENDPOINT}/logout") {
                header("apikey", supabaseClient.getAnonKey())
                header("Authorization", "Bearer ${supabaseClient.getAccessToken()}")
                header("Content-Type", "application/json")
            }
            
            // 3. Limpiar datos locales
            supabaseClient.clearAuth()
            currentUser = null
            currentProfile = null
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Verificar si el usuario está autenticado
     */
    fun isAuthenticated(): Boolean {
        return currentUser != null
    }
    
    /**
     * Obtener usuario actual
     */
    fun getCurrentUser(): SupabaseUser? = currentUser
    
    /**
     * Obtener perfil actual
     */
    fun getCurrentProfile(): UserProfile? = currentProfile
}