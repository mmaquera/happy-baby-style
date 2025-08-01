package com.mmaquera.happybabystyle.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BabyStyleText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    showRainbow: Boolean = true,
    showStar: Boolean = true
) {
    val annotatedString = buildAnnotatedString {
        text.forEachIndexed { index, char ->
            when {
                char == 'y' && showRainbow -> {
                    pushStringAnnotation(
                        tag = "rainbow",
                        annotation = "rainbow"
                    )
                    append(char.toString())
                    pop()
                }
                char == 'i' && showStar -> {
                    pushStringAnnotation(
                        tag = "star",
                        annotation = "star"
                    )
                    append(char.toString())
                    pop()
                }
                else -> {
                    append(char.toString())
                }
            }
        }
    }

    Text(
        text = annotatedString,
        style = style,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun BabyStyleCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = SurfaceBackground,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        content()
    }
}

@Composable
fun BabyStyleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        content()
    }
}

@Composable
fun RainbowStarIcon(
    modifier: Modifier = Modifier,
    size: Float = 24f
) {
    Canvas(modifier = modifier.size(size.dp)) {
        // Draw rainbow arc
        val rainbowPath = Path().apply {
            moveTo(size * 0.1f, size * 0.7f)
            quadraticBezierTo(
                size * 0.5f, size * 0.3f,
                size * 0.9f, size * 0.7f
            )
        }
        
        // Rainbow colors using theme colors
        val rainbowColors = listOf(
            Primary, // Primary color
            Secondary, // Secondary color
            SecondaryText, // Tertiary color
            SurfaceBackground, // Surface background
            BorderLight, // Border color
            BorderSelection, // Selection color
            IconPrimary // Icon color
        )
        
        // Draw rainbow stripes
        rainbowColors.forEachIndexed { index, color ->
            drawPath(
                path = rainbowPath,
                color = color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = size * 0.08f,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )
        }
        
        // Draw star
        drawStar(
            center = Offset(size * 0.5f, size * 0.8f),
            radius = size * 0.15f,
            color = IconSecondary
        )
    }
}

private fun DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color
) {
    val path = Path()
    val points = 5
    val innerRadius = radius * 0.4f
    
    for (i in 0 until points * 2) {
        val angle = i * Math.PI / points
        val currentRadius = if (i % 2 == 0) radius else innerRadius
        val x = center.x + (currentRadius * kotlin.math.cos(angle)).toFloat()
        val y = center.y + (currentRadius * kotlin.math.sin(angle)).toFloat()
        
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    
    drawPath(path, color)
}

@Composable
fun BabyStyleSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Background,
                shape = RoundedCornerShape(0.dp)
            ),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
    ) {
        content()
    }
}

// ========== PREVIEWS ==========

@Preview(name = "BabyStyleText - Con elementos gráficos", showBackground = true)
@Composable
fun BabyStyleTextPreview() {
    HappyBabyStyleTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BabyStyleText(
                text = "Happy Baby Style",
                style = MaterialTheme.typography.displaySmall
            )
            
            BabyStyleText(
                text = "Sin elementos gráficos",
                style = MaterialTheme.typography.headlineMedium,
                showRainbow = false,
                showStar = false
            )
            
            BabyStyleText(
                text = "Solo con arcoíris",
                style = MaterialTheme.typography.titleLarge,
                showRainbow = true,
                showStar = false
            )
            
            BabyStyleText(
                text = "Solo con estrella",
                style = MaterialTheme.typography.bodyLarge,
                showRainbow = false,
                showStar = true
            )
        }
    }
}

@Preview(name = "BabyStyleCard", showBackground = true)
@Composable
fun BabyStyleCardPreview() {
    HappyBabyStyleTheme {
        BabyStyleCard(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tarjeta Baby Style",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Esta es una tarjeta con el tema baby-friendly, con bordes redondeados y colores del diseño Figma.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Preview(name = "BabyStyleButton", showBackground = true)
@Composable
fun BabyStyleButtonPreview() {
    HappyBabyStyleTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BabyStyleButton(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Botón Baby Style",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            BabyStyleButton(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Text(
                    text = "Botón Deshabilitado",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            BabyStyleButton(
                onClick = { /* TODO */ },
                modifier = Modifier.width(200.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RainbowStarIcon(size = 20f)
                    Text(
                        text = "Con Icono",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview(name = "RainbowStarIcon", showBackground = true)
@Composable
fun RainbowStarIconPreview() {
    HappyBabyStyleTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Iconos Rainbow Star",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RainbowStarIcon(size = 24f)
                RainbowStarIcon(size = 32f)
                RainbowStarIcon(size = 48f)
                RainbowStarIcon(size = 64f)
            }
            
            Text(
                text = "Diferentes tamaños",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "BabyStyleSurface", showBackground = true)
@Composable
fun BabyStyleSurfacePreview() {
    HappyBabyStyleTheme {
        BabyStyleSurface {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Superficie Baby Style",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                BabyStyleCard {
                    Text(
                        text = "Contenido en tarjeta",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                BabyStyleButton(
                    onClick = { /* TODO */ }
                ) {
                    Text("Botón de ejemplo")
                }
            }
        }
    }
}

@Preview(name = "Componentes Combinados", showBackground = true)
@Composable
fun CombinedComponentsPreview() {
    HappyBabyStyleTheme {
        BabyStyleSurface {
            Column(
                modifier = Modifier.padding(16.dp),
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
                }
                
                // Cards
                BabyStyleCard {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Paleta de Colores",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ColorSwatch(Primary, "Primario")
                            ColorSwatch(Secondary, "Secundario")
                            ColorSwatch(SurfaceBackground, "Fondo")
                        }
                    }
                }
                
                // Buttons
                BabyStyleButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("¡Explorar!")
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    color: Color,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = color,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 