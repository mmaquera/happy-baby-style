package com.mmaquera.happybabystyle.view.orderdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.*

/**
 * Main OrderDetailScreen composable
 * Following Single Responsibility Principle - only handles UI composition
 */
@Composable
fun OrderDetailScreen(
    orderId: String = "123456789",
    onBackPressed: () -> Unit = {},
    viewModel: OrderDetailViewModel = viewModel()
) {
    val orderDetails by viewModel.orderDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    LaunchedEffect(orderId) {
        viewModel.loadOrderDetails(orderId)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        OrderDetailHeader(onBackPressed = onBackPressed)
        
        // Content
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        } else if (error != null) {
            ErrorState(
                message = error!!,
                onRetry = { viewModel.loadOrderDetails(orderId) }
            )
        } else {
            orderDetails?.let { details ->
                OrderDetailContent(
                    orderDetails = details,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}

/**
 * Header component with back button and title
 * Following Single Responsibility Principle - only handles header UI
 */
@Composable
private fun OrderDetailHeader(
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = PrimaryText,
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Title
        Text(
            text = "Order Details",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = PrimaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        
        // Empty space for balance
        Spacer(modifier = Modifier.width(48.dp))
    }
}

/**
 * Main content component
 * Following Single Responsibility Principle - only handles content composition
 */
@Composable
private fun OrderDetailContent(
    orderDetails: OrderDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        // Order info
        OrderInfoSection(
            orderNumber = orderDetails.orderNumber,
            orderDate = orderDetails.orderDate
        )
        
        // Order items
        OrderItemsSection(items = orderDetails.items)
        
        // Payment section
        PaymentSection(payment = orderDetails.payment)
        
        // Shipping address
        ShippingAddressSection(address = orderDetails.shippingAddress)
        
        // Order status
        OrderStatusSection(statusSteps = orderDetails.statusSteps)
    }
}

/**
 * Order information section
 * Following Single Responsibility Principle - only handles order info display
 */
@Composable
private fun OrderInfoSection(
    orderNumber: String,
    orderDate: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Order #$orderNumber",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = PrimaryText,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        
        Text(
            text = "Placed on $orderDate",
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryText
        )
    }
}

/**
 * Order items section
 * Following Single Responsibility Principle - only handles items display
 */
@Composable
private fun OrderItemsSection(items: List<OrderItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        items.forEach { item ->
            OrderItemCard(item = item)
        }
    }
}

/**
 * Individual order item card
 * Following Single Responsibility Principle - only handles item display
 */
@Composable
private fun OrderItemCard(item: OrderItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product image
        Image(
            painter = painterResource(
                id = when (item.imageUrl) {
                    "ic_product_placeholder_1" -> R.drawable.ic_product_placeholder_1
                    "ic_socks" -> R.drawable.ic_socks
                    "ic_hat" -> R.drawable.ic_hat
                    else -> R.drawable.ic_product_placeholder
                }
            ),
            contentDescription = item.name,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Product details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = PrimaryText
            )
            
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText
            )
        }
    }
}

/**
 * Payment section
 * Following Single Responsibility Principle - only handles payment display
 */
@Composable
private fun PaymentSection(payment: OrderPayment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Payment",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = PrimaryText,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PaymentBreakdown(payment = payment)
    }
}

/**
 * Payment breakdown component
 * Following Single Responsibility Principle - only handles payment breakdown
 */
@Composable
private fun PaymentBreakdown(payment: OrderPayment) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PaymentRow(
            label = "Subtotal",
            amount = payment.subtotal
        )
        
        PaymentRow(
            label = "Shipping",
            amount = payment.shipping
        )
        
        PaymentRow(
            label = "Tax",
            amount = payment.tax
        )
        
        HorizontalDivider(
            color = BorderLight,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        PaymentRow(
            label = "Total",
            amount = payment.total,
            isTotal = true
        )
    }
}

/**
 * Payment row component
 * Following Single Responsibility Principle - only handles payment row display
 */
@Composable
private fun PaymentRow(
    label: String,
    amount: Double,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isTotal) {
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            } else {
                MaterialTheme.typography.bodyMedium
            },
            color = if (isTotal) PrimaryText else SecondaryText
        )
        
        Text(
            text = "$${String.format("%.2f", amount)}",
            style = if (isTotal) {
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                )
            } else {
                MaterialTheme.typography.bodyMedium
            },
            color = PrimaryText
        )
    }
}

/**
 * Shipping address section
 * Following Single Responsibility Principle - only handles address display
 */
@Composable
private fun ShippingAddressSection(address: ShippingAddress) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Shipping Address",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = PrimaryText,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = address.name,
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryText
        )
        
        Text(
            text = address.street,
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryText
        )
        
        Text(
            text = "${address.city}, ${address.state} ${address.zipCode}",
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryText
        )
    }
}

/**
 * Order status section
 * Following Single Responsibility Principle - only handles status display
 */
@Composable
private fun OrderStatusSection(statusSteps: List<OrderStatusStep>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Order Status",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = PrimaryText,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        OrderStatusTimeline(statusSteps = statusSteps)
    }
}

/**
 * Order status timeline component
 * Following Single Responsibility Principle - only handles timeline display
 */
@Composable
private fun OrderStatusTimeline(statusSteps: List<OrderStatusStep>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        statusSteps.forEachIndexed { index, step ->
            OrderStatusStep(
                step = step,
                isLast = index == statusSteps.size - 1
            )
        }
    }
}

/**
 * Individual order status step
 * Following Single Responsibility Principle - only handles step display
 */
@Composable
private fun OrderStatusStep(
    step: OrderStatusStep,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Status icon and line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {
            // Icon
            Icon(
                imageVector = when {
                    step.title.contains("Placed") -> Icons.Default.ShoppingCart
                    step.title.contains("Shipped") -> Icons.Default.LocalShipping
                    step.title.contains("Delivered") -> Icons.Default.CheckCircle
                    else -> Icons.Default.CheckCircle
                },
                contentDescription = step.title,
                tint = if (step.isCompleted) Primary else SecondaryText,
                modifier = Modifier.size(24.dp)
            )
            
            // Connecting line (except for last item)
            if (!isLast) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(32.dp)
                        .background(
                            color = if (step.isCompleted) Primary else SecondaryText.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(1.dp)
                        )
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // Status details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = step.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = PrimaryText
            )
            
            Text(
                text = step.date,
                style = MaterialTheme.typography.bodyLarge,
                color = SecondaryText
            )
        }
    }
}

/**
 * Error state component
 * Following Single Responsibility Principle - only handles error display
 */
@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryText
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryText,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text("Retry")
        }
    }
}

/**
 * Preview for OrderDetailScreen
 */
@Preview(name = "Order Detail Screen", showBackground = true)
@Composable
private fun OrderDetailScreenPreview() {
    HappyBabyStyleTheme {
        OrderDetailScreen()
    }
}

/**
 * Preview for OrderDetailHeader
 */
@Preview(name = "Order Detail Header", showBackground = true)
@Composable
private fun OrderDetailHeaderPreview() {
    HappyBabyStyleTheme {
        OrderDetailHeader(onBackPressed = {})
    }
}

/**
 * Preview for OrderItemCard
 */
@Preview(name = "Order Item Card", showBackground = true)
@Composable
private fun OrderItemCardPreview() {
    HappyBabyStyleTheme {
        OrderItemCard(
            item = OrderItem(
                id = "1",
                name = "1 x Blue Romper",
                description = "Baby Romper - Size 6M",
                quantity = 1,
                price = 25.00,
                imageUrl = "ic_product_placeholder_1"
            )
        )
    }
}

/**
 * Preview for PaymentBreakdown
 */
@Preview(name = "Payment Breakdown", showBackground = true)
@Composable
private fun PaymentBreakdownPreview() {
    HappyBabyStyleTheme {
        PaymentBreakdown(
            payment = OrderPayment(
                subtotal = 45.00,
                shipping = 5.00,
                tax = 2.50,
                total = 52.50
            )
        )
    }
}

/**
 * Preview for OrderStatusStep
 */
@Preview(name = "Order Status Step", showBackground = true)
@Composable
private fun OrderStatusStepPreview() {
    HappyBabyStyleTheme {
        OrderStatusStep(
            step = OrderStatusStep(
                title = "Order Placed",
                date = "June 15, 2024",
                isCompleted = true,
                isCurrent = false
            ),
            isLast = false
        )
    }
}