package com.mmaquera.happybabystyle.view.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

import com.mmaquera.happybabystyle.util.ServiceProvider
import com.mmaquera.happybabystyle.view.login.components.ErrorDialog
import com.mmaquera.happybabystyle.view.signup.components.SignUpButton
import com.mmaquera.happybabystyle.view.signup.components.SignUpTextField

/**
 * Pantalla de registro siguiendo el diseño de Figma
 * Implementa MVVM pattern y Clean Architecture
 * Refactorizada con ConstraintLayout para mejor control de posicionamiento
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: SignUpViewModel = remember {
        SignUpViewModel(
            signUpUseCase = ServiceProvider.getSignUpUseCase(context)
        )
    }
    val state = viewModel.state
    val scrollState = rememberScrollState()

    // Efecto para manejar redirección después de aceptar la alerta de éxito
    LaunchedEffect(state.shouldNavigateToLogin) {
        if (state.shouldNavigateToLogin) {
            onSignUpSuccess()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Color de fondo del diseño
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        // Referencias para ConstraintLayout
        val (
            header,
            nameField,
            emailField,
            passwordField,
            confirmPasswordField,
            signUpButton,
            loginPrompt
        ) = createRefs()

        // Header con botón back y título
        SignUpHeader(
            onNavigateBack = onNavigateBack,
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Campo Name
        SignUpTextField(
            value = state.name,
            onValueChange = { viewModel.handleEvent(SignUpEvent.NameChanged(it)) },
            label = "Nombre",
            placeholder = "Ingresa tu nombre",
            isError = state.nameError != null,
            errorMessage = state.nameError,
            keyboardType = KeyboardType.Text,
            modifier = Modifier
                .constrainAs(nameField) {
                    top.linkTo(header.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Campo Email
        SignUpTextField(
            value = state.email,
            onValueChange = { viewModel.handleEvent(SignUpEvent.EmailChanged(it)) },
            label = "Correo electrónico",
            placeholder = "Ingresa tu email",
            isError = state.emailError != null,
            errorMessage = state.emailError,
            keyboardType = KeyboardType.Email,
            modifier = Modifier
                .constrainAs(emailField) {
                    top.linkTo(nameField.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Campo Password
        SignUpTextField(
            value = state.password,
            onValueChange = { viewModel.handleEvent(SignUpEvent.PasswordChanged(it)) },
            label = "Contraseña",
            placeholder = "Ingresa tu contraseña",
            isPassword = true,
            showPassword = state.showPassword,
            onTogglePasswordVisibility = { 
                viewModel.handleEvent(SignUpEvent.TogglePasswordVisibility) 
            },
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
            keyboardType = KeyboardType.Password,
            modifier = Modifier
                .constrainAs(passwordField) {
                    top.linkTo(emailField.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Campo Confirm Password
        SignUpTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.handleEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
            label = "Confirmar contraseña",
            placeholder = "Confirma tu contraseña",
            isPassword = true,
            showPassword = state.showConfirmPassword,
            onTogglePasswordVisibility = { 
                viewModel.handleEvent(SignUpEvent.ToggleConfirmPasswordVisibility) 
            },
            isError = state.confirmPasswordError != null,
            errorMessage = state.confirmPasswordError,
            keyboardType = KeyboardType.Password,
            modifier = Modifier
                .constrainAs(confirmPasswordField) {
                    top.linkTo(passwordField.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Botón Sign Up
        SignUpButton(
            onClick = { viewModel.handleEvent(SignUpEvent.SignUp) },
            text = "Registrate",
            isLoading = state.isLoading,
            enabled = state.isFormValid,
            modifier = Modifier
                .constrainAs(signUpButton) {
                    top.linkTo(confirmPasswordField.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Link "Already have an account? Login"
        LoginPrompt(
            onNavigateToLogin = onNavigateToLogin,
            modifier = Modifier
                .constrainAs(loginPrompt) {
                    top.linkTo(signUpButton.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }

    // Dialog de error
    if (state.errorMessage != null) {
        ErrorDialog(
            message = state.errorMessage,
            onDismiss = { viewModel.handleEvent(SignUpEvent.DismissError) }
        )
    }

    // Dialog de éxito
    if (state.showSuccessMessage) {
        SuccessDialog(
            message = state.successMessage,
            onDismiss = { viewModel.handleEvent(SignUpEvent.DismissSuccessMessage) }
        )
    }
}

/**
 * Header de la pantalla con botón back y título
 * Refactorizado con ConstraintLayout para mejor control de posicionamiento
 */
@Composable
private fun SignUpHeader(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // Referencias para ConstraintLayout
        val (backButton, title) = createRefs()

        // Botón Back
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF171212),
                modifier = Modifier.size(24.dp)
            )
        }

        // Título centrado
        Text(
            text = "Registrate",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

/**
 * Prompt para ir a login
 * Optimizado con ConstraintLayout para mantener consistencia
 */
@Composable
private fun LoginPrompt(
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToLogin() }
            .padding(vertical = 8.dp)
    ) {
        val text = createRef()
        
        Text(
            text = "¿Ya tienes una cuenta? Inicia sesión",
            fontSize = 14.sp,
            color = Color(0xFF826b6b),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

/**
 * Dialog de éxito para mostrar mensajes de registro exitoso
 */
@Composable
private fun SuccessDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "¡Éxito!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color(0xFF171212),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Entendido",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        containerColor = Color(0xFFFFFFFF),
        titleContentColor = Color(0xFF4CAF50),
        textContentColor = Color(0xFF171212)
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    HappyBabyStyleTheme {
        SignUpScreen()
    }
}