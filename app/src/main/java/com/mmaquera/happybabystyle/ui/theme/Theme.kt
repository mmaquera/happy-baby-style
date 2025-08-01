package com.mmaquera.happybabystyle.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    tertiary = SecondaryText,
    onTertiary = OnPrimary,
    background = Background,
    onBackground = PrimaryText,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = BorderLight,
    outlineVariant = BorderSelection,
    scrim = Disabled,
    inverseSurface = PrimaryText,
    inverseOnSurface = OnPrimary,
    inversePrimary = OnPrimary,
    surfaceTint = Primary
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    tertiary = SecondaryText,
    onTertiary = OnPrimary,
    background = Background,
    onBackground = PrimaryText,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = BorderLight,
    outlineVariant = BorderSelection,
    scrim = Disabled,
    inverseSurface = PrimaryText,
    inverseOnSurface = OnPrimary,
    inversePrimary = OnPrimary,
    surfaceTint = Primary
)

@Composable
fun HappyBabyStyleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Preview(name = "Tema Claro", showBackground = true)
@Composable
fun LightThemePreview() {
    HappyBabyStyleTheme(darkTheme = false) {
        ThemePreviewContent()
    }
}

@Preview(name = "Tema Oscuro", showBackground = true)
@Composable
fun DarkThemePreview() {
    HappyBabyStyleTheme(darkTheme = true) {
        ThemePreviewContent()
    }
}

@Composable
private fun ThemePreviewContent() {
    BabyStyleSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BabyStyleText(
                    text = "Happy Baby Style",
                    style = MaterialTheme.typography.displaySmall
                )
                
                RainbowStarIcon(size = 48f)
                
                Text(
                    text = "Tema Baby-Friendly",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
            
            // Color scheme showcase
            BabyStyleCard {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Esquema de Colores",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ColorSchemeRow(
                        title = "Primario",
                        color = MaterialTheme.colorScheme.primary,
                        onColor = MaterialTheme.colorScheme.onPrimary
                    )
                    
                    ColorSchemeRow(
                        title = "Secundario",
                        color = MaterialTheme.colorScheme.secondary,
                        onColor = MaterialTheme.colorScheme.onSecondary
                    )
                    
                    ColorSchemeRow(
                        title = "Terciario",
                        color = MaterialTheme.colorScheme.tertiary,
                        onColor = MaterialTheme.colorScheme.onTertiary
                    )
                    
                    ColorSchemeRow(
                        title = "Fondo",
                        color = MaterialTheme.colorScheme.background,
                        onColor = MaterialTheme.colorScheme.onBackground
                    )
                    
                    ColorSchemeRow(
                        title = "Superficie",
                        color = MaterialTheme.colorScheme.surface,
                        onColor = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            // Typography showcase
            BabyStyleCard {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Tipografía",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Display Small",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Headline Medium",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    
                    Text(
                        text = "Body Large",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "Label Medium",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            
            // Interactive elements
            BabyStyleCard {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Elementos Interactivos",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    BabyStyleButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Botón Primario")
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    BabyStyleButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    ) {
                        Text("Botón Deshabilitado")
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorSchemeRow(
    title: String,
    color: androidx.compose.ui.graphics.Color,
    onColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = color,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            
            Text(
                text = "Color",
                style = MaterialTheme.typography.labelSmall,
                color = onColor,
                modifier = Modifier
                    .background(
                        color = color,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}