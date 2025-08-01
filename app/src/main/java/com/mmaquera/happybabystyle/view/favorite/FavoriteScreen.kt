package com.mmaquera.happybabystyle.view.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.*

/**
 * FavoriteScreen refactorizado siguiendo exactamente el diseño de Figma
 * Mantiene el patrón MVVM, Clean Code y principios SOLID
 * 
 * Single Responsibility: Cada componente tiene una responsabilidad específica
 * Open/Closed: Extensible sin modificar código existente
 * Liskov Substitution: Sigue contratos de Composable
 * Interface Segregation: Funcionalidad específica y enfocada
 * Dependency Inversion: Depende de abstracciones (ViewModel)
 */
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(),
    onProductClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onCategoriesClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)) // Exacto color de Figma
    ) {
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Header con título centrado y botón de búsqueda
            FavoriteHeader(
                onSearchClick = onSearchClick
            )
            
            // Título "My Favorites" alineado a la izquierda
            FavoriteSectionTitle()
            
            // Grid de productos favoritos con espaciado exacto de Figma
            FavoriteProductsGrid(
                favorites = uiState.favorites,
                onProductClick = onProductClick,
                onFavoriteToggle = { productId ->
                    viewModel.toggleFavorite(productId)
                }
            )
        }
        
        // Navegación inferior con borde superior
        FavoriteBottomNavigation(
            onHomeClick = onHomeClick,
            onCategoriesClick = onCategoriesClick,
            onCartClick = onCartClick,
            onProfileClick = onProfileClick
        )
    }
}

/**
 * Header con título centrado y botón de búsqueda
 * Siguiendo exactamente el layout de Figma
 */
@Composable
private fun FavoriteHeader(
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp), // Padding exacto de Figma
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Título centrado con padding izquierdo de 48dp (12 * 4)
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp, // Exacto tamaño de Figma
                lineHeight = 23.sp // Exacto line-height de Figma
            ),
            color = Color(0xFF171212), // Color exacto de Figma
            modifier = Modifier
                .weight(1f)
                .padding(start = 48.dp), // Padding izquierdo de Figma
            textAlign = TextAlign.Center
        )
        
        // Botón de búsqueda con dimensiones exactas
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .size(48.dp) // Tamaño exacto de Figma
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFF5F0F0)) // Color de fondo de Figma
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF8A6163), // Color de icono de Figma
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Título de sección "My Favorites"
 * Siguiendo exactamente el layout de Figma
 */
@Composable
private fun FavoriteSectionTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp) // Altura exacta de Figma
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp, // Exacto tamaño de Figma
                lineHeight = 23.sp // Exacto line-height de Figma
            ),
            color = Color(0xFF171212), // Color exacto de Figma
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

/**
 * Grid de productos favoritos con espaciado exacto de Figma
 * Gap de 3px entre elementos y padding de 16px
 */
@Composable
private fun FavoriteProductsGrid(
    favorites: List<FavoriteProduct>,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp), // Padding exacto de Figma
        horizontalArrangement = Arrangement.spacedBy(3.dp), // Gap exacto de Figma
        verticalArrangement = Arrangement.spacedBy(3.dp), // Gap exacto de Figma
        modifier = Modifier.fillMaxWidth()
    ) {
        items(favorites) { product ->
            FavoriteProductCard(
                product = product,
                onClick = { onProductClick(product.id) },
                onFavoriteToggle = { onFavoriteToggle(product.id) }
            )
        }
    }
}

/**
 * Tarjeta de producto individual
 * Dimensiones exactas de Figma: 173px ancho, 231px alto imagen
 */
@Composable
private fun FavoriteProductCard(
    product: FavoriteProduct,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(173.dp) // Ancho exacto de Figma
            .clickable { onClick() }
    ) {
        // Imagen del producto con dimensiones exactas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(231.dp) // Alto exacto de Figma
                .clip(RoundedCornerShape(12.dp)) // Border radius exacto de Figma
                .background(Color(0xFFF5F0F0)) // Color de fondo de Figma
        ) {
            // Imagen placeholder
            Image(
                painter = painterResource(
                    id = getProductImageResource(product.imageRes)
                ),
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Botón de favorito en la esquina superior derecha
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(
                        color = Color(0xFFFFFFFF).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    imageVector = if (product.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = if (product.isFavorite) {
                        "Remove from favorites"
                    } else {
                        "Add to favorites"
                    },
                    tint = if (product.isFavorite) {
                        Color(0xFFE91E63) // Color rosa para corazón lleno
                    } else {
                        Color(0xFF8A6163) // Color de icono de Figma
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        // Nombre del producto con espaciado exacto
        Spacer(modifier = Modifier.height(12.dp)) // Gap exacto de Figma
        
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp, // Exacto tamaño de Figma
                lineHeight = 24.sp // Exacto line-height de Figma
            ),
            color = Color(0xFF171212), // Color exacto de Figma
            modifier = Modifier.padding(bottom = 12.dp) // Padding inferior de Figma
        )
    }
}

/**
 * Navegación inferior con borde superior
 * Siguiendo exactamente el diseño de Figma
 */
@Composable
private fun FavoriteBottomNavigation(
    onHomeClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFFFFFFF), // Color exacto de Figma
        tonalElevation = 0.dp
    ) {
        Column {
            // Borde superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFF5F2F2)) // Color de borde de Figma
            )
            
            // Contenido de navegación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 9.dp), // Padding exacto de Figma
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home
                NavigationItem(
                    icon = R.drawable.ic_home,
                    label = "Home",
                    isSelected = false,
                    onClick = onHomeClick
                )
                
                // Categories
                NavigationItem(
                    icon = R.drawable.ic_categories,
                    label = "Categories",
                    isSelected = false,
                    onClick = onCategoriesClick
                )
                
                // Favorites (seleccionado)
                NavigationItem(
                    icon = R.drawable.ic_favorites,
                    label = "Favorites",
                    isSelected = true,
                    onClick = { /* Ya en pantalla de favoritos */ }
                )
                
                // Cart
                NavigationItem(
                    icon = R.drawable.ic_cart,
                    label = "Cart",
                    isSelected = false,
                    onClick = onCartClick
                )
                
                // Profile
                NavigationItem(
                    icon = R.drawable.ic_profile,
                    label = "Profile",
                    isSelected = false,
                    onClick = onProfileClick
                )
            }
            
            // Espacio inferior
            Spacer(modifier = Modifier.height(20.dp)) // Altura exacta de Figma
        }
    }
}

/**
 * Elemento individual de navegación
 * Siguiendo exactamente el diseño de Figma
 */
@Composable
private fun NavigationItem(
    @androidx.annotation.DrawableRes icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        // Icono con tamaño exacto
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                if (isSelected) {
                    Color(0xFF171212) // Color primario de Figma
                } else {
                    Color(0xFF826B6B) // Color secundario de Figma
                }
            )
        )
        
        // Espacio entre icono y texto
        Spacer(modifier = Modifier.height(4.dp)) // Gap exacto de Figma
        
        // Texto con tipografía exacta
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                fontSize = 12.sp, // Exacto tamaño de Figma
                lineHeight = 18.sp // Exacto line-height de Figma
            ),
            color = if (isSelected) {
                Color(0xFF171212) // Color primario de Figma
            } else {
                Color(0xFF826B6B) // Color secundario de Figma
            },
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Función auxiliar para obtener el recurso de imagen del producto
 */
private fun getProductImageResource(imageRes: String): Int {
    return when (imageRes) {
        "ic_product_placeholder_1" -> R.drawable.ic_product_placeholder_1
        "ic_product_placeholder_2" -> R.drawable.ic_product_placeholder_2
        "ic_product_placeholder_3" -> R.drawable.ic_product_placeholder_3
        else -> R.drawable.ic_product_placeholder_1
    }
}

// ========== PREVIEWS ==========

@Preview(name = "FavoriteScreen - Light Theme", showBackground = true)
@Composable
fun FavoriteScreenLightPreview() {
    HappyBabyStyleTheme(darkTheme = false) {
        FavoriteScreen()
    }
}

@Preview(name = "FavoriteScreen - Dark Theme", showBackground = true)
@Composable
fun FavoriteScreenDarkPreview() {
    HappyBabyStyleTheme(darkTheme = true) {
        FavoriteScreen()
    }
}

@Preview(name = "FavoriteHeader", showBackground = true)
@Composable
fun FavoriteHeaderPreview() {
    HappyBabyStyleTheme {
        FavoriteHeader(
            onSearchClick = { /* TODO */ }
        )
    }
}

@Preview(name = "FavoriteProductCard", showBackground = true)
@Composable
fun FavoriteProductCardPreview() {
    HappyBabyStyleTheme {
        FavoriteProductCard(
            product = FavoriteProduct(
                id = "1",
                name = "Cozy Cloud Onesie",
                imageRes = "ic_product_placeholder_1",
                price = "$24.99",
                isFavorite = true
            ),
            onClick = { /* TODO */ },
            onFavoriteToggle = { /* TODO */ }
        )
    }
}

@Preview(name = "FavoriteBottomNavigation", showBackground = true)
@Composable
fun FavoriteBottomNavigationPreview() {
    HappyBabyStyleTheme {
        FavoriteBottomNavigation(
            onHomeClick = { /* TODO */ },
            onCategoriesClick = { /* TODO */ },
            onCartClick = { /* TODO */ },
            onProfileClick = { /* TODO */ }
        )
    }
}