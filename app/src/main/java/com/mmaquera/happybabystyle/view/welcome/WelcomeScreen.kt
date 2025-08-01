package com.mmaquera.happybabystyle.view.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mmaquera.happybabystyle.ui.theme.*

/**
 * Welcome Screen composable following the exact Figma design specifications
 * 
 * Design Details from Figma:
 * - Background image covers full screen
 * - Buttons positioned at bottom with specific spacing
 * - Login button: #fabac2 (pink) background
 * - Sign Up button: #f5f0f0 (gray) background
 * - 12dp gap between buttons
 * - 16dp horizontal padding, 12dp vertical padding
 * - 24dp border radius (rounded-3xl)
 * - Plus Jakarta Sans Bold, 16px, #171212 color
 */
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    WelcomeContent(
        uiState = uiState,
        onLoginClick = viewModel::onLoginClick,
        onSignUpClick = viewModel::onSignUpClick,
        modifier = modifier
    )
}

/**
 * Content composable that exactly matches Figma layout structure
 * Following Single Responsibility Principle - handles layout composition only
 */
@Composable
fun WelcomeContent(
    uiState: WelcomeUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Main container matching Figma structure
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background image section (Depth 1, Frame 0 in Figma)
        BackgroundImageSection(
            imageUrl = uiState.backgroundImageUrl,
            modifier = Modifier.fillMaxSize()
        )
        
        // Action buttons section (Depth 1, Frame 1 in Figma)
        ActionButtonsSection(
            uiState = uiState,
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

/**
 * Background image section matching Figma's Depth 1, Frame 0 structure
 * Following Composition over Inheritance principle
 */
@Composable
fun BackgroundImageSection(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Background image with exact Figma specifications
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Welcome background image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Action buttons section matching Figma's Depth 1, Frame 1 structure
 * Exact spacing and layout from Figma design
 */
@Composable
fun ActionButtonsSection(
    uiState: WelcomeUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.95f))
            .padding(horizontal = 16.dp, vertical = 12.dp), // px-4 py-3 from Figma
        verticalArrangement = Arrangement.spacedBy(12.dp), // gap-3 from Figma
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Login Button - exact Figma specifications
        FigmaLoginButton(
            text = uiState.loginButtonText,
            onClick = onLoginClick,
            enabled = uiState.isLoginEnabled,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Sign Up Button - exact Figma specifications
        FigmaSignUpButton(
            text = uiState.signUpButtonText,
            onClick = onSignUpClick,
            enabled = uiState.isSignUpEnabled,
            modifier = Modifier.fillMaxWidth()
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
    text: String,
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
            containerColor = Color(0xFFFABAC2), // #fabac2 from Figma
            contentColor = Color(0xFF171212), // #171212 from Figma
            disabledContainerColor = Color(0xFFE5DBDB),
            disabledContentColor = Color(0xFF8A6163)
        ),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp) // px-5 py-0 from Figma
    ) {
        Text(
            text = text,
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
    text: String,
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
            text = text,
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

