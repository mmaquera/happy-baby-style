package com.mmaquera.happybabystyle.view.login

import PrimaryButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.BabyStyleText
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme
import com.mmaquera.happybabystyle.ui.theme.PrimaryText
import com.mmaquera.happybabystyle.ui.theme.SecondaryText
import com.mmaquera.happybabystyle.ui.theme.SurfaceBackground

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    val state = viewModel.state

    // Handle successful login
    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onNavigateToHome()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Create references for all composables
        val (
            header,
            welcomeTitle,
            welcomeSubtitle,
            socialButtons,
            emailField,
            passwordField,
            forgotPassword,
            signInButton,
            signUpLink
        ) = createRefs()

        // Header
        BabyStyleText(
            text = stringResource(id = R.string.happy_baby_style),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top, margin = 32.dp)
                centerHorizontallyTo(parent)
            }
        )

        // Welcome Text
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(welcomeTitle) {
                top.linkTo(header.bottom, margin = 32.dp)
                centerHorizontallyTo(parent)
            }
        )

        Text(
            text = stringResource(id = R.string.sign_in_to_continue_shopping),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = SecondaryText
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(welcomeSubtitle) {
                top.linkTo(welcomeTitle.bottom, margin = 8.dp)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // Social Login Buttons
        SocialLoginButtons(
            onGoogleClick = { viewModel.handleEvent(LoginEvent.SignInWithGoogle) },
            onFacebookClick = { viewModel.handleEvent(LoginEvent.SignInWithFacebook) },
            onAppleClick = { viewModel.handleEvent(LoginEvent.SignInWithApple) },
            modifier = Modifier.constrainAs(socialButtons) {
                top.linkTo(welcomeSubtitle.bottom, margin = 32.dp)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // Email Input
        LoginTextField(
            value = state.email,
            onValueChange = { viewModel.handleEvent(LoginEvent.EmailChanged(it)) },
            placeholder = "Correo electrónico",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(socialButtons.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // Password Input
        LoginTextField(
            value = state.password,
            onValueChange = { viewModel.handleEvent(LoginEvent.PasswordChanged(it)) },
            placeholder = "Contraseña",
            isPassword = true,
            showPassword = state.showPassword,
            onTogglePasswordVisibility = { viewModel.handleEvent(LoginEvent.TogglePasswordVisibility) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.constrainAs(passwordField) {
                top.linkTo(emailField.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // Forgot Password
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = SecondaryText
            ),
            modifier = Modifier
                .constrainAs(forgotPassword) {
                    top.linkTo(passwordField.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
                .clickable { viewModel.handleEvent(LoginEvent.ForgotPassword) }
        )

        // Sign In Button
        PrimaryButton(
            onClick = { viewModel.handleEvent(LoginEvent.SignIn) },
            modifier = Modifier.constrainAs(signInButton) {
                top.linkTo(forgotPassword.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(id = R.string.sign_in_button_text),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Sign Up Link
        Text(
            text = stringResource(id = R.string.dont_have_account_sign_up),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = SecondaryText
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(signUpLink) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
                .clickable { onNavigateToSignUp() }
        )
    }

    // Error Message
    state.errorMessage?.let { errorMessage ->
        ErrorDialog(
            message = errorMessage,
            onDismiss = { viewModel.handleEvent(LoginEvent.EmailChanged(state.email)) }
        )
    }
}

@Composable
private fun SocialLoginButtons(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onAppleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SocialLoginButton(
            text = stringResource(id = R.string.continue_with_google),
            onClick = onGoogleClick
        )

        SocialLoginButton(
            text = stringResource(id = R.string.continue_with_facebook),
            onClick = onFacebookClick
        )

        SocialLoginButton(
            text = stringResource(id = R.string.continue_with_apple),
            onClick = onAppleClick
        )
    }
}

@Composable
private fun SocialLoginButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceBackground
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
        )
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    color = SecondaryText
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceBackground,
            unfocusedContainerColor = SurfaceBackground,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedPlaceholderColor = SecondaryText,
            unfocusedPlaceholderColor = SecondaryText
        ),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = keyboardOptions,
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onTogglePasswordVisibility?.invoke() }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = SecondaryText
                    )
                }
            }
        } else null
    )
}

@Composable
private fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Preview(name = "Login Screen", showBackground = true)
@Composable
fun LoginScreenPreview() {
    HappyBabyStyleTheme {
        LoginScreen()
    }
}