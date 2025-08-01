package com.mmaquera.happybabystyle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmaquera.happybabystyle.ui.theme.*
import com.mmaquera.happybabystyle.view.cart.CartScreen
import com.mmaquera.happybabystyle.view.categories.CategoryScreen
import com.mmaquera.happybabystyle.view.detail.DetailProductScreen
import com.mmaquera.happybabystyle.view.home.HomeScreen
import com.mmaquera.happybabystyle.view.login.LoginScreen
import com.mmaquera.happybabystyle.view.profile.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyBabyStyleTheme {
                // Surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CartScreen()
                }
            }
        }
    }
}

// Demo function for showcasing the design system (optional)
@Composable
fun BabyStyleDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header with rainbow and star elements
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BabyStyleText(
                text = "Happy Baby Style",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            RainbowStarIcon(
                modifier = Modifier.size(48.dp),
                size = 48f
            )
        }
        
        // Welcome message
        BabyStyleCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Bienvenido al mundo de los bebés!",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Diseñado con colores suaves y tipografías redondeadas para crear una experiencia cálida y acogedora.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Color palette showcase
        BabyStyleCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Paleta de Colores Figma",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ColorSwatch(
                        color = Primary,
                        name = "Primario"
                    )
                    ColorSwatch(
                        color = Secondary,
                        name = "Secundario"
                    )
                    ColorSwatch(
                        color = SecondaryText,
                        name = "Texto Sec"
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ColorSwatch(
                        color = SurfaceBackground,
                        name = "Fondo"
                    )
                    ColorSwatch(
                        color = BorderLight,
                        name = "Borde"
                    )
                    ColorSwatch(
                        color = BorderSelection,
                        name = "Selección"
                    )
                }
            }
        }
        
        // Interactive elements
        BabyStyleCard(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    onClick = { /* TODO: Add action */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¡Explorar!",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                BabyStyleButton(
                    onClick = { /* TODO: Add action */ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                ) {
                    Text(
                        text = "Próximamente",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        
        // Typography showcase
        BabyStyleCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Tipografía Suave",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Display Large",
                    style = MaterialTheme.typography.displayLarge,
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
    }
}

@Composable
fun ColorSwatch(
    color: androidx.compose.ui.graphics.Color,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = color,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailProductScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BabyStyleDemoPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BabyStyleDemo()
        }
    }
}