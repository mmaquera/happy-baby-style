package com.mmaquera.happybabystyle.view.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Botón de registro personalizado siguiendo el diseño de Figma
 * Color: #fabac2 (rosa)
 * Siguiendo Single Responsibility Principle
 */
@Composable
fun SignUpButton(
    onClick: () -> Unit,
    text: String = "Sign Up",
    isLoading: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFABAC2), // Color rosa del diseño Figma
            contentColor = Color(0xFF171212), // Color del texto
            disabledContainerColor = Color(0xFFFABAC2).copy(alpha = 0.5f),
            disabledContentColor = Color(0xFF171212).copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(24.dp), // Esquinas muy redondeadas del diseño
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp) // Altura del botón del diseño
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF171212),
                strokeWidth = 2.dp,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (enabled && !isLoading) {
                Color(0xFF171212)
            } else {
                Color(0xFF171212).copy(alpha = 0.5f)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpButtonPreview() {
    HappyBabyStyleTheme {
        SignUpButton(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpButtonLoadingPreview() {
    HappyBabyStyleTheme {
        SignUpButton(
            onClick = {},
            isLoading = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpButtonDisabledPreview() {
    HappyBabyStyleTheme {
        SignUpButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}