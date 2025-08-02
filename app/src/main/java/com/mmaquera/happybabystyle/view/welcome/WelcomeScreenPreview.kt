package com.mmaquera.happybabystyle.view.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

// ========== PREVIEWS ==========

@Preview(
    name = "Welcome Screen - Light Theme",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun WelcomeScreenLightPreview() {
    HappyBabyStyleTheme(darkTheme = false) {
        WelcomeScreen()
    }
}

@Preview(
    name = "Welcome Screen - Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun WelcomeScreenDarkPreview() {
    HappyBabyStyleTheme(darkTheme = true) {
        WelcomeScreen()
    }
}

@Preview(
    name = "Welcome Content - Components Only",
    showBackground = true
)
@Composable
fun WelcomeContentPreview() {
    HappyBabyStyleTheme {
        WelcomeContent(
            uiState = WelcomeUiState(),
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}

@Preview(
    name = "Action Buttons Section",
    showBackground = true
)
@Composable
fun ActionButtonsSectionPreview() {
    HappyBabyStyleTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ActionButtonsSection(
                uiState = WelcomeUiState(),
                onLoginClick = {},
                onSignUpClick = {},
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(
    name = "Figma Login Button",
    showBackground = true
)
@Composable
fun FigmaLoginButtonPreview() {
    HappyBabyStyleTheme {
        FigmaLoginButton(
            onClick = {},
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Figma Sign Up Button",
    showBackground = true
)
@Composable
fun FigmaSignUpButtonPreview() {
    HappyBabyStyleTheme {
        FigmaSignUpButton(
            onClick = {},
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Figma Login Button - Disabled",
    showBackground = true
)
@Composable
fun FigmaLoginButtonDisabledPreview() {
    HappyBabyStyleTheme {
        FigmaLoginButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Figma Sign Up Button - Disabled",
    showBackground = true
)
@Composable
fun FigmaSignUpButtonDisabledPreview() {
    HappyBabyStyleTheme {
        FigmaSignUpButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Background Image Section",
    showBackground = true
)
@Composable
fun BackgroundImageSectionPreview() {
    HappyBabyStyleTheme {
        BackgroundImageSection(
            modifier = Modifier.size(200.dp)
        )
    }
} 