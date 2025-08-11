package com.mmaquera.happybabystyle.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.usecase.LoginWithEmailUseCase
import com.mmaquera.happybabystyle.domain.usecase.LoginWithGoogleUseCase
import com.mmaquera.happybabystyle.domain.usecase.ValidateCredentialsUseCase
import com.mmaquera.happybabystyle.domain.usecase.GetCurrentUserUseCase
import com.mmaquera.happybabystyle.domain.usecase.LogoutUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.mmaquera.happybabystyle.util.ApolloServiceProvider

data class LoginState(
    val email: String = "marco.arka@gmail.com",
    val password: String = "Demo123$",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val showPassword: Boolean = false,
    val isGoogleSignInLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isFormValid: Boolean = false
)

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object SignIn : LoginEvent()
    object SignInWithGoogle : LoginEvent()
    object ForgotPassword : LoginEvent()
    object SignUp : LoginEvent()
}

class LoginViewModel : ViewModel() {
    
    // Use Cases Apollo GraphQL - Inyección directa sin Hilt
    private val loginWithEmailUseCase: LoginWithEmailUseCase = ApolloServiceProvider.getLoginWithEmailUseCase()
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase = ApolloServiceProvider.getLoginWithGoogleUseCase()
    private val validateCredentialsUseCase: ValidateCredentialsUseCase = ApolloServiceProvider.getValidateCredentialsUseCase()
    private val getCurrentUserUseCase: GetCurrentUserUseCase = ApolloServiceProvider.getCurrentUserUseCase()
    private val logoutUseCase: LogoutUseCase = ApolloServiceProvider.getLogoutUseCase()
    
    var state by mutableStateOf(LoginState())
        private set
    
    // Use Cases required for Clean Architecture
    // All authentication logic is handled through use cases
    
    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                handleEmailChanged(event.email)
            }
            
            is LoginEvent.PasswordChanged -> {
                handlePasswordChanged(event.password)
            }
            
            is LoginEvent.TogglePasswordVisibility -> {
                state = state.copy(
                    showPassword = !state.showPassword
                )
            }
            
            is LoginEvent.SignIn -> {
                performEmailSignIn()
            }
            
            is LoginEvent.SignInWithGoogle -> {
                performGoogleSignIn()
            }
            
            is LoginEvent.ForgotPassword -> {
                handleForgotPassword()
            }
            
            is LoginEvent.SignUp -> {
                handleSignUp()
            }
        }
    }
    
    /**
     * Maneja el cambio de email con validación en tiempo real
     */
    private fun handleEmailChanged(email: String) {
        state = state.copy(email = email, errorMessage = null)
        validateCredentials()
    }
    
    /**
     * Maneja el cambio de contraseña con validación en tiempo real
     */
    private fun handlePasswordChanged(password: String) {
        state = state.copy(password = password, errorMessage = null)
        validateCredentials()
    }
    
    /**
     * Valida las credenciales usando el Use Case
     */
    private fun validateCredentials() {
        viewModelScope.launch {
            try {
                val params = ValidateCredentialsUseCase.Params(
                    email = state.email,
                    password = state.password
                )
                val result = validateCredentialsUseCase(params)
                
                state = state.copy(
                    emailError = result.emailValidation.emailError,
                    passwordError = result.passwordValidation.passwordError,
                    isFormValid = result.isValid
                )
            } catch (exception: Exception) {
                // Manejar error de validación si es necesario
            }
        }
    }
    
    /**
     * Realiza login con email y contraseña usando Clean Architecture
     */
    private fun performEmailSignIn() {
        
        viewModelScope.launch {
            val params = LoginWithEmailUseCase.Params(
                email = state.email,
                password = state.password
            )
            
            loginWithEmailUseCase(params).collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }
                    
                    is AuthResult.Success -> {
                        state = state.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            errorMessage = null
                        )
                    }
                    
                    is AuthResult.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = getErrorMessage(result.exception)
                        )
                    }
                }
            }
        }
    }
    

    
    /**
     * Procesar autenticación con Google usando Clean Architecture
     */
    private fun performGoogleSignIn() {
        
        state = state.copy(
            isGoogleSignInLoading = true,
            errorMessage = null
        )
        
        viewModelScope.launch {
            // Para Google necesitamos obtener el ID token primero
            // Por ahora simulamos que ya lo tenemos - en una implementación real
            // esto vendría del Credential Manager
            val params = LoginWithGoogleUseCase.Params(
                idToken = "mock_id_token", // Esto debería venir del Credential Manager
                accessToken = null
            )
            
            loginWithGoogleUseCase(params).collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        state = state.copy(
                            isGoogleSignInLoading = true,
                            errorMessage = null
                        )
                    }
                    
                    is AuthResult.Success -> {
                        state = state.copy(
                            isGoogleSignInLoading = false,
                            isLoggedIn = true,
                            errorMessage = null
                        )
                    }
                    
                    is AuthResult.Error -> {
                        state = state.copy(
                            isGoogleSignInLoading = false,
                            errorMessage = getErrorMessage(result.exception)
                        )
                    }
                }
            }
        }
    }
    

    
    /**
     * Convierte AuthException a mensaje de error legible
     */
    private fun getErrorMessage(exception: AuthException): String {
        return when (exception) {
            is AuthException.InvalidCredentials -> exception.message ?: "Credenciales inválidas"
            is AuthException.UserNotFound -> exception.message ?: "Usuario no encontrado"
            is AuthException.EmailNotVerified -> exception.message ?: "Email no verificado"
            is AuthException.AccountDisabled -> exception.message ?: "Cuenta deshabilitada"
            is AuthException.TooManyAttempts -> exception.message ?: "Demasiados intentos"
            is AuthException.NetworkError -> exception.message ?: "Error de conexión"
            is AuthException.ServerError -> exception.message ?: "Error del servidor"
            is AuthException.ConfigurationError -> exception.message ?: "Error de configuración"
            is AuthException.EmailAlreadyInUse -> exception.message ?: "Email ya está en uso"
            is AuthException.WeakPassword -> exception.message ?: "Contraseña muy débil"
            is AuthException.SignupDisabled -> exception.message ?: "Registro deshabilitado"
            is AuthException.RateLimitExceeded -> exception.message ?: "Demasiados intentos"
            is AuthException.UnsupportedOperation -> exception.message ?: "Operación no soportada"
            is AuthException.UnknownError -> exception.message ?: "Error desconocido"
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