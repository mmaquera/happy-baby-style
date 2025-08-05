package com.mmaquera.happybabystyle.view.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Campo de texto personalizado para el formulario de registro
 * Basado en el diseño de Figma con colores específicos
 * Siguiendo Single Responsibility Principle
 */
@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize = 16.sp,
                color = Color(0xFF171212) // Color del diseño Figma
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                color = Color(0xFF826b6b) // Color placeholder del diseño
            )
        },
        visualTransformation = if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPassword && onTogglePasswordVisibility != null) {
            {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        imageVector = if (showPassword) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = if (showPassword) {
                            "Ocultar contraseña"
                        } else {
                            "Mostrar contraseña"
                        },
                        tint = Color(0xFF826b6b)
                    )
                }
            }
        } else null,
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F2F2), // Color de fondo del diseño
            unfocusedContainerColor = Color(0xFFF5F2F2),
            disabledContainerColor = Color(0xFFF5F2F2),
            errorContainerColor = Color(0xFFF5F2F2),
            
            focusedBorderColor = Color(0xFFFABAC2), // Color rosa del diseño
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.error,
            
            focusedTextColor = Color(0xFF171212),
            unfocusedTextColor = Color(0xFF171212),
            disabledTextColor = Color(0xFF826b6b),
            errorTextColor = Color(0xFF171212),
            
            cursorColor = Color(0xFFFABAC2)
        ),
        shape = RoundedCornerShape(12.dp), // Esquinas redondeadas del diseño
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp) // Altura mínima de 56dp, pero puede crecer
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpTextFieldPreview() {
    HappyBabyStyleTheme {
        SignUpTextField(
            value = "",
            onValueChange = {},
            label = "Name",
            placeholder = "Enter your name"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpTextFieldPasswordPreview() {
    HappyBabyStyleTheme {
        SignUpTextField(
            value = "password123",
            onValueChange = {},
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
            showPassword = false,
            onTogglePasswordVisibility = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpTextFieldErrorPreview() {
    HappyBabyStyleTheme {
        SignUpTextField(
            value = "invalid",
            onValueChange = {},
            label = "Email",
            placeholder = "Enter your email",
            isError = true,
            errorMessage = "Formato de email inválido",
            keyboardType = KeyboardType.Email
        )
    }
}