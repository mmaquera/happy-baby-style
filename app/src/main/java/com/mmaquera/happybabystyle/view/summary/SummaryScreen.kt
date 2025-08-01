package com.mmaquera.happybabystyle.view.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.ui.theme.*

/**
 * Main Summary Screen composable
 * Following Single Responsibility Principle - only handles summary UI
 */
@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = viewModel(),
    onContinueShopping: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToCategories: () -> Unit = {},
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        SummaryHeader()
        
        // Content
        SummaryContent(
            uiState = uiState,
            onContinueShopping = {
                viewModel.continueShopping()
                onContinueShopping()
            }
        )
        
        // Bottom Navigation
        SummaryBottomNavigation(
            onNavigateToHome = onNavigateToHome,
            onNavigateToCategories = onNavigateToCategories,
            onNavigateToFavorites = onNavigateToFavorites,
            onNavigateToCart = onNavigateToCart,
            onNavigateToProfile = onNavigateToProfile
        )
    }
}

/**
 * Header component for summary screen
 * Following Single Responsibility Principle - only handles header UI
 */
@Composable
private fun SummaryHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title
        Text(
            text = "Happy Baby Style",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // Profile icon
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = PrimaryText,
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * Main content component for summary screen
 * Following Single Responsibility Principle - only handles content UI
 */
@Composable
private fun SummaryContent(
    uiState: SummaryUiState,
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Thank you message
        ThankYouMessage()
        
        // Order summary section
        OrderSummarySection(uiState.orderSummary)
        
        // Continue shopping button
        ContinueShoppingButton(onContinueShopping)
    }
}

/**
 * Thank you message component
 * Following Single Responsibility Principle - only handles thank you message UI
 */
@Composable
private fun ThankYouMessage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Thank you for your order!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Your order has been placed and will be shipped soon. You will receive an email confirmation with tracking information.",
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryText,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

/**
 * Order summary section component
 * Following Single Responsibility Principle - only handles order summary UI
 */
@Composable
private fun OrderSummarySection(orderSummary: OrderSummaryInfo?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Section title
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Order details
        orderSummary?.let { summary ->
            OrderDetailsCard(summary)
        }
    }
}

/**
 * Order details card component
 * Following Single Responsibility Principle - only handles order details UI
 */
@Composable
private fun OrderDetailsCard(orderSummary: OrderSummaryInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Order number row
            OrderDetailRow(
                label = "Order Number",
                value = orderSummary.orderNumber
            )
            
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = BorderLight,
                thickness = 1.dp
            )
            
            // Order date row
            OrderDetailRow(
                label = "Order Date",
                value = orderSummary.orderDate
            )
            
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = BorderLight,
                thickness = 1.dp
            )
            
            // Total row
            OrderDetailRow(
                label = "Total",
                value = "$${String.format("%.2f", orderSummary.total)}"
            )
        }
    }
}

/**
 * Order detail row component
 * Following Single Responsibility Principle - only handles order detail row UI
 */
@Composable
private fun OrderDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryText,
            fontWeight = FontWeight.Normal
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryText,
            fontWeight = FontWeight.Normal
        )
    }
}

/**
 * Continue shopping button component
 * Following Single Responsibility Principle - only handles button UI
 */
@Composable
private fun ContinueShoppingButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryButton
        )
    ) {
        Text(
            text = "Continue Shopping",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
    }
}

/**
 * Bottom navigation component for summary screen
 * Following Single Responsibility Principle - only handles bottom navigation UI
 */
@Composable
private fun SummaryBottomNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .border(
                width = 1.dp,
                color = NavigationBorder,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
    ) {
        // Navigation items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = true,
                onClick = onNavigateToHome
            )
            
            NavigationItem(
                icon = Icons.Default.Category,
                label = "Categories",
                isSelected = false,
                onClick = onNavigateToCategories
            )
            
            NavigationItem(
                icon = Icons.Default.Favorite,
                label = "Favorites",
                isSelected = false,
                onClick = onNavigateToFavorites
            )
            
            NavigationItem(
                icon = Icons.Default.ShoppingCart,
                label = "Cart",
                isSelected = false,
                onClick = onNavigateToCart
            )
            
            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = onNavigateToProfile
            )
        }
        
        // Bottom safe area
        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Navigation item component
 * Following Single Responsibility Principle - only handles navigation item UI
 */
@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) NavigationSelected else NavigationUnselected,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) NavigationSelected else NavigationUnselected,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

/**
 * Preview for SummaryScreen
 */
@Preview(name = "Summary Screen", showBackground = true)
@Composable
private fun SummaryScreenPreview() {
    HappyBabyStyleTheme {
        SummaryScreen()
    }
}

/**
 * Preview for Thank You Message
 */
@Preview(name = "Thank You Message", showBackground = true)
@Composable
private fun ThankYouMessagePreview() {
    HappyBabyStyleTheme {
        ThankYouMessage()
    }
}

/**
 * Preview for Order Summary Section
 */
@Preview(name = "Order Summary Section", showBackground = true)
@Composable
private fun OrderSummarySectionPreview() {
    HappyBabyStyleTheme {
        OrderSummarySection(
            OrderSummaryInfo(
                orderNumber = "#123456789",
                orderDate = "July 26, 2024",
                total = 75.00,
                status = OrderStatus.PLACED
            )
        )
    }
}

/**
 * Preview for Continue Shopping Button
 */
@Preview(name = "Continue Shopping Button", showBackground = true)
@Composable
private fun ContinueShoppingButtonPreview() {
    HappyBabyStyleTheme {
        ContinueShoppingButton(onClick = {})
    }
}

/**
 * Preview for Bottom Navigation
 */
@Preview(name = "Bottom Navigation", showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    HappyBabyStyleTheme {
        SummaryBottomNavigation(
            onNavigateToHome = {},
            onNavigateToCategories = {},
            onNavigateToFavorites = {},
            onNavigateToCart = {},
            onNavigateToProfile = {}
        )
    }
}