package com.mmaquera.happybabystyle.view.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.*

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = viewModel(),
    onBackPressed: () -> Unit = {}
) {
    val checkoutItems by viewModel.checkoutItems.collectAsState()
    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val shippingInfo by viewModel.shippingInfo.collectAsState()
    val orderSummary by viewModel.orderSummary.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isPaymentProcessing by viewModel.isPaymentProcessing.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        CheckoutHeader(onBackPressed = onBackPressed)
        
        // Shipping Information Section
        ShippingInformationSection(
            shippingInfo = shippingInfo,
            onShippingInfoChanged = { newShippingInfo -> 
                viewModel.updateShippingInfo(
                    fullName = newShippingInfo.fullName,
                    address = newShippingInfo.address,
                    city = newShippingInfo.city,
                    state = newShippingInfo.state,
                    zipCode = newShippingInfo.zipCode
                )
            }
        )
        
        // Payment Method Section
        PaymentMethodSection(
            paymentMethods = paymentMethods,
            onPaymentMethodSelected = { viewModel.selectPaymentMethod(it) }
        )
        
        // Order Summary Section
        OrderSummarySection(
            checkoutItems = checkoutItems,
            orderSummary = orderSummary
        )
        
        // Pay Now Button
        PayNowButton(
            isEnabled = viewModel.isCheckoutReady(),
            isLoading = isPaymentProcessing,
            onPayNowClick = { viewModel.processPayment() }
        )
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
internal fun CheckoutHeader(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable { onBackPressed() }
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Title
        Text(
            text = "Checkout",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // Empty space for balance
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
internal fun ShippingInformationSection(
    shippingInfo: ShippingInfo,
    onShippingInfoChanged: (ShippingInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Title
        Text(
            text = "Shipping Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Form Fields
        ShippingTextField(
            value = shippingInfo.fullName,
            placeholder = "Full Name",
            onValueChange = { onShippingInfoChanged(shippingInfo.copy(fullName = it)) }
        )
        
        ShippingTextField(
            value = shippingInfo.address,
            placeholder = "Address",
            onValueChange = { onShippingInfoChanged(shippingInfo.copy(address = it)) }
        )
        
        ShippingTextField(
            value = shippingInfo.city,
            placeholder = "City",
            onValueChange = { onShippingInfoChanged(shippingInfo.copy(city = it)) }
        )
        
        ShippingTextField(
            value = shippingInfo.state,
            placeholder = "State",
            onValueChange = { onShippingInfoChanged(shippingInfo.copy(state = it)) }
        )
        
        ShippingTextField(
            value = shippingInfo.zipCode,
            placeholder = "Zip Code",
            onValueChange = { onShippingInfoChanged(shippingInfo.copy(zipCode = it)) }
        )
    }
}

@Composable
internal fun ShippingTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp)
            .background(
                color = SurfaceBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = if (value.isBlank()) SecondaryText else PrimaryText
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                        color = SecondaryText
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
internal fun PaymentMethodSection(
    paymentMethods: List<PaymentMethod>,
    onPaymentMethodSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Title
        Text(
            text = "Payment Method",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Payment Methods
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            paymentMethods.forEach { paymentMethod ->
                PaymentMethodItem(
                    paymentMethod = paymentMethod,
                    onSelected = { onPaymentMethodSelected(paymentMethod.id) }
                )
            }
        }
    }
}

@Composable
internal fun PaymentMethodItem(
    paymentMethod: PaymentMethod,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderLight,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelected() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = paymentMethod.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = PrimaryText
        )
        
        Image(
            painter = painterResource(
                id = if (paymentMethod.isSelected) {
                    R.drawable.ic_radio_button_checked
                } else {
                    R.drawable.ic_radio_button_unchecked
                }
            ),
            contentDescription = if (paymentMethod.isSelected) "Selected" else "Not selected",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
internal fun OrderSummarySection(
    checkoutItems: List<CheckoutItem>,
    orderSummary: CheckoutOrderSummary
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Title
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Order Items
        checkoutItems.forEach { item ->
            OrderItem(item = item)
        }
        
        // Order Summary Details
        OrderSummaryDetails(orderSummary = orderSummary)
    }
}

@Composable
internal fun OrderItem(item: CheckoutItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        Image(
            painter = painterResource(
                id = when (item.imageUrl) {
                    "ic_product_placeholder_1" -> R.drawable.ic_product_placeholder_1
                    "ic_product_placeholder_2" -> R.drawable.ic_product_placeholder_2
                    else -> R.drawable.ic_product_placeholder
                }
            ),
            contentDescription = item.name,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        
        // Product Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )
            Text(
                text = item.size,
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText
            )
        }
    }
}

@Composable
internal fun OrderSummaryDetails(orderSummary: CheckoutOrderSummary) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OrderSummaryRow(
            label = "Subtotal",
            value = "$${String.format("%.2f", orderSummary.subtotal)}"
        )
        OrderSummaryRow(
            label = "Shipping",
            value = "$${String.format("%.2f", orderSummary.shipping)}"
        )
        OrderSummaryRow(
            label = "Total",
            value = "$${String.format("%.2f", orderSummary.total)}",
            isTotal = true
        )
    }
}

@Composable
internal fun OrderSummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryText
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isTotal) FontWeight.Medium else FontWeight.Normal,
            color = PrimaryText
        )
    }
}

@Composable
internal fun PayNowButton(
    isEnabled: Boolean,
    isLoading: Boolean,
    onPayNowClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Button(
            onClick = onPayNowClick,
            enabled = isEnabled && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryButton,
                contentColor = PrimaryText,
                disabledContainerColor = Disabled,
                disabledContentColor = SecondaryText
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = PrimaryText,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Pay Now",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



