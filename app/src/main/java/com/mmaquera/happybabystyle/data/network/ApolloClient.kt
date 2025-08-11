package com.mmaquera.happybabystyle.data.network

import DateTimeAdapter
import com.mmaquera.happybabystyle.data.config.AppConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomScalarType
import com.apollographql.apollo.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Cliente Apollo GraphQL siguiendo Clean Architecture
 *
 * Características:
 * - Configuración centralizada
 * - Manejo de autenticación
 * - Logging estructurado
 * - Timeouts configurables
 * - Cache automático
 */
class ApolloGraphQLClient {

    companion object {
        private const val GRAPHQL_ENDPOINT = AppConfig.GRAPHQL_ENDPOINT_EMULATOR
    }

    private var accessToken: String? = null

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(AppConfig.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(AppConfig.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(AppConfig.NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request()
            println("🌐 Apollo GraphQL Request: ${request.method} ${request.url}")
            val response = chain.proceed(request)
            println("🌐 Apollo GraphQL Response: ${response.code}")
            response
        }
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            // Agregar token de autenticación si existe
            accessToken?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(GRAPHQL_ENDPOINT)
        .addCustomScalarAdapter(
            CustomScalarType(
                name = "DateTime",
                className = "java.time.LocalDateTime"
            ),
            DateTimeAdapter
        )
        .okHttpClient(okHttpClient)
        .build()

    /**
     * Obtiene el cliente Apollo configurado
     */
    fun getClient(): ApolloClient = apolloClient

    /**
     * Actualiza el token de autenticación
     */
    fun updateAccessToken(token: String?) {
        accessToken = token
    }

    /**
     * Limpia la autenticación
     */
    fun clearAuth() {
        accessToken = null
    }

    /**
     * Verifica si está autenticado
     */
    fun isAuthenticated(): Boolean = accessToken != null
} 