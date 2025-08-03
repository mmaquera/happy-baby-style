package com.mmaquera.happybabystyle.view.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme
import com.mmaquera.happybabystyle.ui.theme.SecondaryText
import com.mmaquera.happybabystyle.ui.theme.SurfaceBackground

/**
 * Campo de texto personalizado para el formulario de login
 * Siguiendo Single Responsibility Principle - solo maneja la entrada de texto
 */
@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = null
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
            focusedBorderColor = if (errorMessage != null) MaterialTheme.colorScheme.error else Color.Transparent,
            unfocusedBorderColor = if (errorMessage != null) MaterialTheme.colorScheme.error else Color.Transparent,
            focusedPlaceholderColor = SecondaryText,
            unfocusedPlaceholderColor = SecondaryText,
            errorBorderColor = MaterialTheme.colorScheme.error
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
        } else null,
        isError = errorMessage != null,
        supportingText = if (errorMessage != null) {
            {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else null
    )
}

@Preview(name = "Login Text Field - Normal", showBackground = true)
@Composable
private fun LoginTextFieldPreview() {
    HappyBabyStyleTheme {
        LoginTextField(
            value = "",
            onValueChange = {},
            placeholder = "Correo electrónico"
        )
    }
}

@Preview(name = "Login Text Field - Password", showBackground = true)
@Composable
private fun LoginTextFieldPasswordPreview() {
    HappyBabyStyleTheme {
        LoginTextField(
            value = "password123",
            onValueChange = {},
            placeholder = "Contraseña",
            isPassword = true,
            showPassword = false,
            onTogglePasswordVisibility = {}
        )
    }
}

@Preview(name = "Login Text Field - Error", showBackground = true)
@Composable
private fun LoginTextFieldErrorPreview() {
    HappyBabyStyleTheme {
        LoginTextField(
            value = "invalid-email",
            onValueChange = {},
            placeholder = "Correo electrónico",
            errorMessage = "Formato de email inválido"
        )
    }
}