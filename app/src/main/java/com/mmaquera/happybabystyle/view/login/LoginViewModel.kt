package com.mmaquera.happybabystyle.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val showPassword: Boolean = false
)

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object SignIn : LoginEvent()
    object SignInWithGoogle : LoginEvent()
    object SignInWithFacebook : LoginEvent()
    object SignInWithApple : LoginEvent()
    object ForgotPassword : LoginEvent()
    object SignUp : LoginEvent()
}

class LoginViewModel : ViewModel() {
    
    var state by mutableStateOf(LoginState())
        private set
    
    // Dummy data for demonstration
    private val dummyUsers = mapOf(
        "user@example.com" to "password123",
        "test@happybabystyle.com" to "test123",
        "demo@baby.com" to "demo456"
    )
    
    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email,
                    errorMessage = null
                )
            }
            
            is LoginEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    errorMessage = null
                )
            }
            
            is LoginEvent.TogglePasswordVisibility -> {
                state = state.copy(
                    showPassword = !state.showPassword
                )
            }
            
            is LoginEvent.SignIn -> {
                performSignIn()
            }
            
            is LoginEvent.SignInWithGoogle -> {
                performSocialSignIn("Google")
            }
            
            is LoginEvent.SignInWithFacebook -> {
                performSocialSignIn("Facebook")
            }
            
            is LoginEvent.SignInWithApple -> {
                performSocialSignIn("Apple")
            }
            
            is LoginEvent.ForgotPassword -> {
                handleForgotPassword()
            }
            
            is LoginEvent.SignUp -> {
                handleSignUp()
            }
        }
    }
    
    private fun performSignIn() {
        if (state.email.isEmpty() || state.password.isEmpty()) {
            state = state.copy(
                errorMessage = "Por favor completa todos los campos"
            )
            return
        }
        
        state = state.copy(isLoading = true)
        
        // Simulate network delay
        viewModelScope.launch {
            delay(1500) // Simulate API call
            
            val isValidUser = dummyUsers[state.email] == state.password
            
            if (isValidUser) {
                state = state.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    errorMessage = null
                )
            } else {
                state = state.copy(
                    isLoading = false,
                    errorMessage = "Credenciales incorrectas. Intenta con:\nuser@example.com / password123"
                )
            }
        }
    }
    
    private fun performSocialSignIn(provider: String) {
        state = state.copy(isLoading = true)
        
        // Simulate social login
        viewModelScope.launch {
            delay(2000) // Simulate social login delay
            
            state = state.copy(
                isLoading = false,
                isLoggedIn = true,
                errorMessage = null
            )
        }
    }
    
    private fun handleForgotPassword() {
        // Simulate forgot password functionality
        state = state.copy(
            errorMessage = "Se ha enviado un enlace de recuperación a tu email"
        )
    }
    
    private fun handleSignUp() {
        // Navigate to sign up screen (in a real app)
        state = state.copy(
            errorMessage = "Navegando a la pantalla de registro..."
        )
    }
    
    fun resetState() {
        state = LoginState()
    }
} 