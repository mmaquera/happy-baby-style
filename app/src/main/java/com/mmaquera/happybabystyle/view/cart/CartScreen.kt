package com.mmaquera.happybabystyle.view.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.ui.theme.*

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onCategoriesClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val orderSummary by viewModel.orderSummary.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        CartHeader(onBackClick = onBackClick)
        
        // Cart Items
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onIncreaseQuantity = { viewModel.increaseQuantity(item.id) },
                    onDecreaseQuantity = { viewModel.decreaseQuantity(item.id) }
                )
            }
            
            // Order Summary Section
            item {
                OrderSummarySection(orderSummary = orderSummary)
            }
        }
        
        // Checkout Button
        CheckoutButton(
            onCheckoutClick = { viewModel.checkout() },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        // Bottom Navigation
        BottomNavigation(
            onHomeClick = onHomeClick,
            onCategoriesClick = onCategoriesClick,
            onFavoritesClick = onFavoritesClick,
            onCartClick = { /* Already on cart */ },
            onProfileClick = onProfileClick,
            currentScreen = "cart"
        )
    }
}

@Composable
private fun CartHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = SurfaceBackground,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = PrimaryText,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Text(
            text = "Cart",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            color = PrimaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        
        // Empty space for balance
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image and Details
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Placeholder
            val placeholderColor = when (item.imageUrl) {
                "ic_product_placeholder_1" -> Color(0xFFE5DBDB)
                "ic_product_placeholder_2" -> Color(0xFFFABAC2)
                "ic_product_placeholder_3" -> Color(0xFF171212)
                else -> Color(0xFFE5DBDB)
            }
            
            Box(
                modifier = Modifier
                    .size(width = 56.dp, height = 75.dp)
                    .background(
                        color = placeholderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                // Simple pattern overlay
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }
            }
            
            // Product Details
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    color = PrimaryText
                )
                
                Text(
                    text = item.size,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = SecondaryText
                )
            }
        }
        
        // Quantity Controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Decrease Button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = SurfaceBackground,
                        shape = CircleShape
                    )
                    .clickable { onDecreaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    color = PrimaryText
                )
            }
            
            // Quantity
            Text(
                text = item.quantity.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = PrimaryText,
                modifier = Modifier.width(16.dp),
                textAlign = TextAlign.Center
            )
            
            // Increase Button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = SurfaceBackground,
                        shape = CircleShape
                    )
                    .clickable { onIncreaseQuantity() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    color = PrimaryText
                )
            }
        }
    }
}

@Composable
private fun OrderSummarySection(orderSummary: OrderSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Title
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            color = PrimaryText,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Summary Items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryRow(
                label = "Subtotal",
                value = "$${String.format("%.2f", orderSummary.subtotal)}"
            )
            
            SummaryRow(
                label = "Shipping",
                value = "Free"
            )
            
            SummaryRow(
                label = "Tax",
                value = "$${String.format("%.2f", orderSummary.tax)}"
            )
            
            androidx.compose.material3.HorizontalDivider(
                color = BorderLight,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            SummaryRow(
                label = "Total",
                value = "$${String.format("%.2f", orderSummary.total)}",
                isTotal = true
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = if (isTotal) 16.sp else 14.sp,
                fontWeight = if (isTotal) FontWeight.Medium else FontWeight.Normal
            ),
            color = if (isTotal) PrimaryText else SecondaryText
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = if (isTotal) 16.sp else 14.sp,
                fontWeight = if (isTotal) FontWeight.Medium else FontWeight.Normal
            ),
            color = if (isTotal) PrimaryText else PrimaryText
        )
    }
}

@Composable
private fun CheckoutButton(
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onCheckoutClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFABAC2) // Pink color from Figma
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = "Checkout",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = PrimaryText
        )
    }
}

@Composable
private fun BottomNavigation(
    onHomeClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentScreen: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
    ) {
        androidx.compose.material3.HorizontalDivider(
            color = BorderLight,
            thickness = 1.dp
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = currentScreen == "home",
                onClick = onHomeClick
            )
            
            NavigationItem(
                icon = Icons.Default.Category,
                label = "Categories",
                isSelected = currentScreen == "categories",
                onClick = onCategoriesClick
            )
            
            NavigationItem(
                icon = Icons.Default.Favorite,
                label = "Favorites",
                isSelected = currentScreen == "favorites",
                onClick = onFavoritesClick
            )
            
            NavigationItem(
                icon = Icons.Default.ShoppingCart,
                label = "Cart",
                isSelected = currentScreen == "cart",
                onClick = onCartClick
            )
            
            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = currentScreen == "profile",
                onClick = onProfileClick
            )
        }
        
        // Bottom safe area
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun NavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) PrimaryText else SecondaryText,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            color = if (isSelected) PrimaryText else SecondaryText
        )
    }
}