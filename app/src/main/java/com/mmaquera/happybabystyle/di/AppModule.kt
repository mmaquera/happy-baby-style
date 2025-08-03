package com.mmaquera.happybabystyle.di

// Temporalmente comentado - Módulo de DI con Hilt
/*
import android.content.Context
import com.mmaquera.happybabystyle.data.network.SupabaseClient
import com.mmaquera.happybabystyle.data.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return SupabaseClient()
    }
    
    @Provides
    @Singleton
    fun provideAuthService(
        @ApplicationContext context: Context,
        supabaseClient: SupabaseClient
    ): AuthService {
        return AuthService(supabaseClient, context)
    }
}
*/