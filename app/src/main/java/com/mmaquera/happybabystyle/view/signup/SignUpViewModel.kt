package com.mmaquera.happybabystyle.view.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmaquera.happybabystyle.domain.model.AuthResult
import com.mmaquera.happybabystyle.domain.model.AuthException
import com.mmaquera.happybabystyle.domain.usecase.SignUpUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.mmaquera.happybabystyle.util.ApolloServiceProvider

/**
 * Estados del formulario de registro
 */
data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val successMessage: String = "",
    val shouldNavigateToLogin: Boolean = false,
    val errorMessage: String? = null,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isFormValid: Boolean = false
)

/**
 * Eventos del formulario de registro
 */
sealed class SignUpEvent {
    data class NameChanged(val name: String) : SignUpEvent()
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent()
    object TogglePasswordVisibility : SignUpEvent()
    object ToggleConfirmPasswordVisibility : SignUpEvent()
    object SignUp : SignUpEvent()
    object NavigateToLogin : SignUpEvent()
    object DismissError : SignUpEvent()
    object DismissSuccessMessage : SignUpEvent()
}

/**
 * ViewModel para la pantalla de registro
 * Siguiendo MVVM pattern y Clean Architecture
 */
class SignUpViewModel : ViewModel() {
    
    // Use Case GraphQL - Inyección directa sin Hilt
    private val signUpUseCase: SignUpUseCase = ApolloServiceProvider.getSignUpUseCase()

    var state by mutableStateOf(SignUpState())
        private set

    /**
     * Maneja los eventos de la UI
     * Siguiendo Single Responsibility Principle
     */
    fun handleEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.NameChanged -> updateName(event.name)
            is SignUpEvent.EmailChanged -> updateEmail(event.email)
            is SignUpEvent.PasswordChanged -> updatePassword(event.password)
            is SignUpEvent.ConfirmPasswordChanged -> updateConfirmPassword(event.confirmPassword)
            is SignUpEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            is SignUpEvent.ToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
            is SignUpEvent.SignUp -> performSignUp()
            is SignUpEvent.NavigateToLogin -> navigateToLogin()
            is SignUpEvent.DismissError -> dismissError()
            is SignUpEvent.DismissSuccessMessage -> dismissSuccessMessage()
        }
    }

    /**
     * Actualiza el nombre y revalida el formulario
     */
    private fun updateName(name: String) {
        state = state.copy(
            name = name,
            nameError = validateName(name)
        )
        validateForm()
    }

    /**
     * Actualiza el email y revalida el formulario
     */
    private fun updateEmail(email: String) {
        state = state.copy(
            email = email,
            emailError = validateEmail(email)
        )
        validateForm()
    }

    /**
     * Actualiza la contraseña y revalida el formulario
     */
    private fun updatePassword(password: String) {
        state = state.copy(
            password = password,
            passwordError = validatePassword(password),
            // Revalidar confirmación si ya se había ingresado
            confirmPasswordError = if (state.confirmPassword.isNotEmpty()) {
                validateConfirmPassword(state.confirmPassword, password)
            } else null
        )
        validateForm()
    }

    /**
     * Actualiza la confirmación de contraseña y revalida el formulario
     */
    private fun updateConfirmPassword(confirmPassword: String) {
        state = state.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = validateConfirmPassword(confirmPassword, state.password)
        )
        validateForm()
    }

    /**
     * Alterna la visibilidad de la contraseña
     */
    private fun togglePasswordVisibility() {
        state = state.copy(showPassword = !state.showPassword)
    }

    /**
     * Alterna la visibilidad de la confirmación de contraseña
     */
    private fun toggleConfirmPasswordVisibility() {
        state = state.copy(showConfirmPassword = !state.showConfirmPassword)
    }

    /**
     * Ejecuta el registro del usuario
     */
    private fun performSignUp() {
        if (!state.isFormValid) return

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)

            signUpUseCase(
                name = state.name.trim(),
                email = state.email.trim(),
                password = state.password,
                confirmPassword = state.confirmPassword
            ).collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                    
                    is AuthResult.Success -> {
                        state = state.copy(
                            isLoading = false,
                            isRegistered = true,
                            showSuccessMessage = true,
                            successMessage = "¡Registro exitoso! Tu cuenta ha sido creada. Haz clic en 'Entendido' para ir al login.",
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
     * Navega a la pantalla de login
     */
    private fun navigateToLogin() {
        // La navegación se manejará en la UI
    }

    /**
     * Oculta el mensaje de error
     */
    private fun dismissError() {
        state = state.copy(errorMessage = null)
    }

    /**
     * Oculta el mensaje de éxito y redirige al login
     */
    private fun dismissSuccessMessage() {
        state = state.copy(
            showSuccessMessage = false,
            successMessage = "",
            isRegistered = false,
            shouldNavigateToLogin = true
        )
    }

    /**
     * Valida el nombre
     */
    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "El nombre es requerido"
            name.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            name.length > 50 -> "El nombre no puede tener más de 50 caracteres"
            !name.matches("^[a-zA-ZÀ-ÿ\\s]+$".toRegex()) -> "El nombre solo puede contener letras y espacios"
            else -> null
        }
    }

    /**
     * Valida el email
     */
    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El email es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de email inválido"
            email.length > 254 -> "El email es demasiado largo"
            else -> null
        }
    }

    /**
     * Valida la contraseña
     */
    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es requerida"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            password.length > 128 -> "La contraseña es demasiado larga"
            else -> null
        }
    }

    /**
     * Valida la confirmación de contraseña
     */
    private fun validateConfirmPassword(confirmPassword: String, password: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirma tu contraseña"
            confirmPassword != password -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    /**
     * Valida todo el formulario
     */
    private fun validateForm() {
        val nameValid = validateName(state.name) == null
        val emailValid = validateEmail(state.email) == null
        val passwordValid = validatePassword(state.password) == null
        val confirmPasswordValid = validateConfirmPassword(state.confirmPassword, state.password) == null

        state = state.copy(
            isFormValid = nameValid && emailValid && passwordValid && confirmPasswordValid &&
                    state.name.isNotBlank() && state.email.isNotBlank() && 
                    state.password.isNotBlank() && state.confirmPassword.isNotBlank()
        )
    }

    /**
     * Convierte excepciones en mensajes de usuario
     */
    private fun getErrorMessage(exception: AuthException): String {
        return when (exception) {
            is AuthException.NetworkError -> "Sin conexión a internet. Verifica tu red."
            is AuthException.InvalidCredentials -> "Credenciales incorrectas."
            is AuthException.UserNotFound -> "Usuario no encontrado."
            is AuthException.EmailAlreadyInUse -> "Ya existe una cuenta con este email."
            is AuthException.WeakPassword -> "La contraseña es muy débil."
            is AuthException.ConfigurationError -> exception.message ?: "Error de configuración"
            is AuthException.SignupDisabled -> "El registro está temporalmente deshabilitado."
            is AuthException.RateLimitExceeded -> "Demasiados intentos. Intenta más tarde."
            is AuthException.UnsupportedOperation -> "Operación no soportada."
            is AuthException.UnknownError -> "Error inesperado: ${exception.message}"
            else -> "Error desconocido: ${exception.message}"
        }
    }
}