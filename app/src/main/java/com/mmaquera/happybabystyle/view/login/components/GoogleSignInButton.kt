package com.mmaquera.happybabystyle.view.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme
import com.mmaquera.happybabystyle.ui.theme.PrimaryText
import com.mmaquera.happybabystyle.ui.theme.SurfaceBackground

/**
 * Botón personalizado para Google Sign-In
 * Siguiendo Single Responsibility Principle - solo maneja la acción de Google Sign-In
 */
@Composable
fun GoogleSignInButton(
    onGoogleClick: () -> Unit,
    isGoogleLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    SocialLoginButton(
        text = stringResource(id = R.string.continue_with_google),
        onClick = onGoogleClick,
        isLoading = isGoogleLoading,
        modifier = modifier
    )
}

/**
 * Componente base para botones de login social
 * Extensible para futuros proveedores (Facebook, Apple, etc.)
 */
@Composable
private fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceBackground,
            disabledContainerColor = SurfaceBackground.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = PrimaryText,
                strokeWidth = 2.dp
            )
        } else {
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
}

@Preview(name = "Google Sign-In Button", showBackground = true)
@Composable
private fun GoogleSignInButtonPreview() {
    HappyBabyStyleTheme {
        GoogleSignInButton(
            onGoogleClick = {},
            isGoogleLoading = false
        )
    }
}

@Preview(name = "Google Sign-In Button - Loading", showBackground = true)
@Composable
private fun GoogleSignInButtonLoadingPreview() {
    HappyBabyStyleTheme {
        GoogleSignInButton(
            onGoogleClick = {},
            isGoogleLoading = true
        )
    }
}