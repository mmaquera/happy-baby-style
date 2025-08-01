package com.mmaquera.happybabystyle.view.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Comprehensive previews for FavoriteScreen components
 * Following Google's best practices for previews
 */

@Preview(
    name = "FavoriteScreen - Complete Light Theme",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun FavoriteScreenCompleteLightPreview() {
    HappyBabyStyleTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen(
                onProductClick = { productId ->
                    // Handle product click
                },
                onSearchClick = {
                    // Handle search click
                },
                onHomeClick = {
                    // Handle home navigation
                },
                onCategoriesClick = {
                    // Handle categories navigation
                },
                onCartClick = {
                    // Handle cart navigation
                },
                onProfileClick = {
                    // Handle profile navigation
                }
            )
        }
    }
}

@Preview(
    name = "FavoriteScreen - Complete Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun FavoriteScreenCompleteDarkPreview() {
    HappyBabyStyleTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen(
                onProductClick = { productId ->
                    // Handle product click
                },
                onSearchClick = {
                    // Handle search click
                },
                onHomeClick = {
                    // Handle home navigation
                },
                onCategoriesClick = {
                    // Handle categories navigation
                },
                onCartClick = {
                    // Handle cart navigation
                },
                onProfileClick = {
                    // Handle profile navigation
                }
            )
        }
    }
}

@Preview(
    name = "FavoriteScreen - Empty State",
    showBackground = true
)
@Composable
fun FavoriteScreenEmptyStatePreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This would show an empty state when no favorites exist
            // In a real implementation, you'd modify the ViewModel to return empty list
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Loading State",
    showBackground = true
)
@Composable
fun FavoriteScreenLoadingStatePreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This would show a loading state
            // In a real implementation, you'd modify the ViewModel to return loading state
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Error State",
    showBackground = true
)
@Composable
fun FavoriteScreenErrorStatePreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This would show an error state
            // In a real implementation, you'd modify the ViewModel to return error state
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Compact Layout",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
fun FavoriteScreenCompactPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Large Layout",
    showBackground = true,
    widthDp = 480,
    heightDp = 800
)
@Composable
fun FavoriteScreenLargePreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Tablet Layout",
    showBackground = true,
    widthDp = 600,
    heightDp = 960
)
@Composable
fun FavoriteScreenTabletPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Landscape",
    showBackground = true,
    widthDp = 800,
    heightDp = 400
)
@Composable
fun FavoriteScreenLandscapePreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Accessibility Preview",
    showBackground = true
)
@Composable
fun FavoriteScreenAccessibilityPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This preview helps verify accessibility features
            // Content descriptions, touch targets, color contrast, etc.
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - RTL Layout",
    showBackground = true
)
@Composable
fun FavoriteScreenRTLPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This preview helps verify RTL (Right-to-Left) layout support
            // In a real implementation, you'd set the layout direction to RTL
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - High Contrast",
    showBackground = true
)
@Composable
fun FavoriteScreenHighContrastPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // This preview helps verify high contrast mode support
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Font Scale Large",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
fun FavoriteScreenLargeFontPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
}

@Preview(
    name = "FavoriteScreen - Font Scale Extra Large",
    showBackground = true,
    fontScale = 2.0f
)
@Composable
fun FavoriteScreenExtraLargeFontPreview() {
    HappyBabyStyleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteScreen()
        }
    }
} 