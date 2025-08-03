package com.mmaquera.happybabystyle.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
// import com.mmaquera.happybabystyle.data.service.AuthResult  // Temporalmente deshabilitado
// import com.mmaquera.happybabystyle.data.service.AuthService  // Temporalmente deshabilitado
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
// import javax.inject.Inject  // Temporalmente deshabilitado
// import dagger.hilt.android.lifecycle.HiltViewModel  // Temporalmente deshabilitado

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val showPassword: Boolean = false,
    val needsGoogleSignIn: Boolean = false // Para trigger del Google Sign-In
)

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object SignIn : LoginEvent()
    object SignInWithGoogle : LoginEvent()
    data class GoogleSignInResult(val task: Task<GoogleSignInAccount>) : LoginEvent()
    object SignInWithFacebook : LoginEvent()
    object SignInWithApple : LoginEvent()
    object ForgotPassword : LoginEvent()
    object SignUp : LoginEvent()
    object ClearGoogleSignInTrigger : LoginEvent()
}

// @HiltViewModel  // Temporalmente deshabilitado
class LoginViewModel(
    // private val authService: AuthService  // Temporalmente deshabilitado
) : ViewModel() {
    
    var state by mutableStateOf(LoginState())
        private set
    
    // Dummy data for demonstration (mantener para email/password local)
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
                // Trigger para que la UI lance Google Sign-In Intent
                state = state.copy(
                    needsGoogleSignIn = true,
                    errorMessage = null
                )
            }
            
            is LoginEvent.GoogleSignInResult -> {
                // Procesar resultado de Google Sign-In
                performGoogleAuthentication(event.task)
            }
            
            is LoginEvent.ClearGoogleSignInTrigger -> {
                state = state.copy(needsGoogleSignIn = false)
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
    
    /**
     * Procesar autenticación con Google usando AuthService
     */
    private fun performGoogleAuthentication(task: Task<GoogleSignInAccount>) {
        state = state.copy(
            isLoading = true,
            needsGoogleSignIn = false,
            errorMessage = null
        )
        
        viewModelScope.launch {
            // Temporalmente comentado - requiere AuthService y Hilt
            // when (val result = authService.handleGoogleSignInResult(task)) {
            //     is AuthResult.Success -> {
            //         state = state.copy(
            //             isLoading = false,
            //             isLoggedIn = true,
            //             errorMessage = null
            //         )
            //     }
            //     is AuthResult.Error -> {
            //         state = state.copy(
            //             isLoading = false,
            //             errorMessage = result.message
            //         )
            //     }
            //     is AuthResult.Loading -> {
            //         // Ya está en loading
            //     }
            // }
            
            // Simulación temporal para testing
            delay(1000)
            state = state.copy(
                isLoading = false,
                isLoggedIn = true,
                errorMessage = null
            )
        }
    }
    
    private fun performSocialSignIn(provider: String) {
        state = state.copy(isLoading = true)
        
        // Simulate social login for Facebook/Apple
        viewModelScope.launch {
            delay(2000) // Simulate social login delay
            
            state = state.copy(
                isLoading = false,
                isLoggedIn = true,
                errorMessage = "Autenticación con $provider simulada exitosamente"
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
    
    /**
     * Obtener cliente de Google Sign-In para la UI
     * Temporalmente comentado - requiere AuthService y Hilt
     */
    // fun getGoogleSignInClient() = authService.getGoogleSignInClient()
} 