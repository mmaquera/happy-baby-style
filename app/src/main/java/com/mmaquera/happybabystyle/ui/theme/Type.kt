package com.mmaquera.happybabystyle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Custom font families for baby-friendly design
val MontserratRounded = FontFamily.Default // Fallback to system font
val Quicksand = FontFamily.Default // Fallback to system font

// Baby-friendly typography with rounded, soft fonts
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = MontserratRounded,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = MontserratRounded,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = MontserratRounded,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Quicksand,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

@Preview(name = "Tipografía Baby-Friendly", showBackground = true)
@Composable
fun TypographyPreview() {
    HappyBabyStyleTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Tipografía Baby-Friendly",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Display styles
            TypographySection(
                title = "Estilos Display (Montserrat Rounded)",
                styles = listOf(
                    "Display Large" to MaterialTheme.typography.displayLarge,
                    "Display Medium" to MaterialTheme.typography.displayMedium,
                    "Display Small" to MaterialTheme.typography.displaySmall
                )
            )
            
            // Headline styles
            TypographySection(
                title = "Estilos Headline (Quicksand)",
                styles = listOf(
                    "Headline Large" to MaterialTheme.typography.headlineLarge,
                    "Headline Medium" to MaterialTheme.typography.headlineMedium,
                    "Headline Small" to MaterialTheme.typography.headlineSmall
                )
            )
            
            // Title styles
            TypographySection(
                title = "Estilos Title (Quicksand)",
                styles = listOf(
                    "Title Large" to MaterialTheme.typography.titleLarge,
                    "Title Medium" to MaterialTheme.typography.titleMedium,
                    "Title Small" to MaterialTheme.typography.titleSmall
                )
            )
            
            // Body styles
            TypographySection(
                title = "Estilos Body (Quicksand)",
                styles = listOf(
                    "Body Large" to MaterialTheme.typography.bodyLarge,
                    "Body Medium" to MaterialTheme.typography.bodyMedium,
                    "Body Small" to MaterialTheme.typography.bodySmall
                )
            )
            
            // Label styles
            TypographySection(
                title = "Estilos Label (Quicksand)",
                styles = listOf(
                    "Label Large" to MaterialTheme.typography.labelLarge,
                    "Label Medium" to MaterialTheme.typography.labelMedium,
                    "Label Small" to MaterialTheme.typography.labelSmall
                )
            )
            
            // Example usage
            TypographyExampleSection()
        }
    }
}

@Composable
private fun TypographySection(
    title: String,
    styles: List<Pair<String, TextStyle>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            styles.forEach { (name, style) ->
                Text(
                    text = "$name - Happy Baby Style",
                    style = style,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun TypographyExampleSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ejemplo de Uso",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "Este es un ejemplo de cómo se ve el texto con la tipografía baby-friendly. Los caracteres 'y' e 'i' pueden mostrar elementos gráficos especiales cuando se usan con BabyStyleText.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Text(
            text = "Los colores suaves y las fuentes redondeadas crean una experiencia visual cálida y acogedora perfecta para aplicaciones relacionadas con bebés.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Etiqueta de ejemplo",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}