package com.mmaquera.happybabystyle.util

import com.mmaquera.happybabystyle.data.mapper.ErrorMapper
import com.mmaquera.happybabystyle.data.mapper.ValidationMapper
import com.mmaquera.happybabystyle.data.network.ApolloGraphQLClient
import com.mmaquera.happybabystyle.data.repository.ApolloAuthRepository
import com.mmaquera.happybabystyle.domain.repository.AuthRepository
import com.mmaquera.happybabystyle.domain.repository.ProductRepository
import com.mmaquera.happybabystyle.domain.usecase.GetCurrentUserUseCase
import com.mmaquera.happybabystyle.domain.usecase.LoginWithEmailUseCase
import com.mmaquera.happybabystyle.domain.usecase.LoginWithGoogleUseCase
import com.mmaquera.happybabystyle.domain.usecase.LogoutUseCase
import com.mmaquera.happybabystyle.domain.usecase.SignUpUseCase
import com.mmaquera.happybabystyle.domain.usecase.ValidateCredentialsUseCase

/**
 * Proveedor de servicios Apollo GraphQL
 * Implementa Clean Architecture con inyección de dependencias manual
 *
 * Características:
 * - Singleton pattern
 * - Lazy initialization
 * - Thread-safe
 * - Clean Architecture compliance
 */
object ApolloServiceProvider {

    @Volatile
    private var apolloClient: ApolloGraphQLClient? = null

    @Volatile
    private var productRepository: ProductRepository? = null

    @Volatile
    private var authRepository: AuthRepository? = null

    // ===== CLIENTE APOLLO =====

    fun getApolloClient(): ApolloGraphQLClient {
        return apolloClient ?: synchronized(this) {
            apolloClient ?: ApolloGraphQLClient().also { apolloClient = it }
        }
    }

    // ===== REPOSITORIOS =====

    /*fun getProductRepository(): ProductRepository {
        return productRepository ?: synchronized(this) {
            productRepository ?: ApolloProductRepository(getApolloClient()).also { productRepository = it }
        }
    }
    */
    fun getAuthRepository(): AuthRepository {
        return authRepository ?: synchronized(this) {
            authRepository ?: ApolloAuthRepository(
                apolloClient = getApolloClient(),
                validationMapper = ValidationMapper(),
                errorMapper = ErrorMapper()
            ).also { authRepository = it }
        }
    }

    // ===== USE CASES =====

    /*fun getProductsUseCase(): GetProductsUseCase {
        return GetProductsUseCase(getProductRepository())
    }
    
    fun getProductByIdUseCase(): GetProductByIdUseCase {
        return GetProductByIdUseCase(getProductRepository())
    }
    
    fun getCategoriesUseCase(): GetCategoriesUseCase {
        return GetCategoriesUseCase(getProductRepository())
    }
    */


    fun getLoginWithEmailUseCase(): LoginWithEmailUseCase {
        return LoginWithEmailUseCase(getAuthRepository())
    }

    fun getLoginWithGoogleUseCase(): LoginWithGoogleUseCase {
        return LoginWithGoogleUseCase(getAuthRepository())
    }

    fun getSignUpUseCase(): SignUpUseCase {
        return SignUpUseCase(getAuthRepository())
    }

    fun getValidateCredentialsUseCase(): ValidateCredentialsUseCase {
        return ValidateCredentialsUseCase(getAuthRepository())
    }

    fun getCurrentUserUseCase(): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(getAuthRepository())
    }

    fun getLogoutUseCase(): LogoutUseCase {
        return LogoutUseCase(getAuthRepository())
    }

    // ===== UTILIDADES =====

    /*fun updateAuthToken(token: String?) {
        getApolloClient().updateAccessToken(token)
    }
    
    fun clearAuth() {
        getApolloClient().clearAuth()
    }
    
    fun isAuthenticated(): Boolean {
        return getApolloClient().isAuthenticated()
    }*/
} 