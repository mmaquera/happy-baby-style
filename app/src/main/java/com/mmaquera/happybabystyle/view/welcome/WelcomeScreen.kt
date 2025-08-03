package com.mmaquera.happybabystyle.view.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.BorderLight
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme
import com.mmaquera.happybabystyle.ui.theme.PrimaryText
import com.mmaquera.happybabystyle.ui.theme.Secondary
import com.mmaquera.happybabystyle.ui.theme.SurfaceBackground


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = viewModel(),
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    WelcomeContent(
        uiState = uiState,
        onLoginClick = onLoginClick,
        onSignUpClick = onSignUpClick,
        modifier = modifier
    )
}

/**
 * Content composable that exactly matches Figma layout structure using ConstraintLayout
 * Following Single Responsibility Principle - handles layout composition only
 */
@Composable
fun WelcomeContent(
    uiState: WelcomeUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        // Create references for our composables
        val (backgroundImage, actionButtons) = createRefs()

        // Background image section (Depth 1, Frame 0 in Figma)
        BackgroundImageSection(
            modifier = Modifier.constrainAs(backgroundImage) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(actionButtons.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        // Action buttons section (Depth 1, Frame 1 in Figma)
        ActionButtonsSection(
            uiState = uiState,
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick,
            modifier = Modifier.constrainAs(actionButtons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )
    }
}

/**
 * Background image section matching Figma's Depth 1, Frame 0 structure
 * Following Composition over Inheritance principle
 * Now using ConstraintLayout for precise positioning
 */
@Composable
fun BackgroundImageSection(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val logo = createRef()

        // Logo positioned at center of the available space
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Happy Baby Style Logo",
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

/**
 * Action buttons section matching Figma's Depth 1, Frame 1 structure
 * Exact spacing and layout from Figma design using ConstraintLayout for precise positioning
 */
@Composable
fun ActionButtonsSection(
    uiState: WelcomeUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.95f))
            .padding(horizontal = 16.dp, vertical = 12.dp) // px-4 py-3 from Figma
    ) {
        val (loginButton, signUpButton) = createRefs()

        // Login Button - exact Figma specifications
        FigmaLoginButton(
            onClick = onLoginClick,
            enabled = uiState.isLoginEnabled,
            modifier = Modifier.constrainAs(loginButton) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        // Sign Up Button - exact Figma specifications with 12dp spacing
        FigmaSignUpButton(
            onClick = onSignUpClick,
            enabled = uiState.isSignUpEnabled,
            modifier = Modifier.constrainAs(signUpButton) {
                top.linkTo(loginButton.bottom, margin = 12.dp) // gap-3 from Figma
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )
    }
}

/**
 * Login button matching exact Figma specifications
 * Background: #fabac2, Height: 48dp, Border radius: 24dp
 * Following Open/Closed Principle - specific implementation for Login
 */
@Composable
fun FigmaLoginButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp) // h-12 from Figma
            .clip(RoundedCornerShape(24.dp)), // rounded-3xl from Figma
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary, // #fabac2 from Figma (ver Color.kt)
            contentColor = PrimaryText, // #171212 from Figma (ver Color.kt)
            disabledContainerColor = BorderLight, // #e5dbdb (ver Color.kt)
            disabledContentColor = Secondary // #8a6163 (ver Color.kt)
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp) // px-5 py-0 from Figma
    ) {
        Text(
            text = stringResource(id = R.string.log_in_button_text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp, // text-[16px] from Figma
                lineHeight = 24.sp // leading-[24px] from Figma
            ),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

/**
 * Sign Up button matching exact Figma specifications
 * Background: #f5f0f0, Height: 48dp, Border radius: 24dp
 * Following Open/Closed Principle - specific implementation for Sign Up
 */
@Composable
fun FigmaSignUpButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp) // h-12 from Figma
            .clip(RoundedCornerShape(24.dp)), // rounded-3xl from Figma
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F0F0), // #f5f0f0 from Figma
            contentColor = Color(0xFF171212), // #171212 from Figma
            disabledContainerColor = Color(0xFFE5DBDB),
            disabledContentColor = Color(0xFF8A6163)
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp) // px-5 py-0 from Figma
    ) {
        Text(
            text = stringResource(id = R.string.sign_up_button_text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp, // text-[16px] from Figma
                lineHeight = 24.sp // leading-[24px] from Figma
            ),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
@Preview(
    name = "WelcomeScreen Light",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
fun PreviewWelcomeScreenLight() {
    HappyBabyStyleTheme(darkTheme = false) {
        WelcomeScreen()
    }
}
