package com.mmaquera.happybabystyle.view.checkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Preview data provider following Single Responsibility Principle
 * Only responsible for providing sample data for previews
 */
object CheckoutPreviewData {
    
    // Sample shipping information
    val sampleShippingInfo = ShippingInfo(
        fullName = "Maria Garcia",
        address = "123 Baby Street",
        city = "Miami",
        state = "FL",
        zipCode = "33101"
    )
    
    // Sample payment methods
    val samplePaymentMethods = listOf(
        PaymentMethod(id = "1", name = "Credit Card", isSelected = true),
        PaymentMethod(id = "2", name = "PayPal", isSelected = false)
    )
    
    // Sample checkout items
    val sampleCheckoutItems = listOf(
        CheckoutItem(
            id = "1",
            name = "Baby Romper",
            size = "Size: 6-12 Months",
            price = 25.00,
            imageUrl = "ic_product_placeholder_1"
        ),
        CheckoutItem(
            id = "2",
            name = "Baby Dress",
            size = "Size: 12-18 Months",
            price = 20.00,
            imageUrl = "ic_product_placeholder_2"
        )
    )
    
    // Sample order summary
    val sampleOrderSummary = CheckoutOrderSummary(
        subtotal = 45.00,
        shipping = 5.00,
        total = 50.00
    )
    
    // Sample single item for individual previews
    val sampleSingleItem = CheckoutItem(
        id = "1",
        name = "Baby Romper",
        size = "Size: 6-12 Months",
        price = 25.00,
        imageUrl = "ic_product_placeholder_1"
    )
}

/**
 * Preview wrapper following Open/Closed Principle
 * Can be extended without modifying existing code
 */
@Composable
private fun CheckoutPreviewWrapper(
    content: @Composable () -> Unit
) {
    HappyBabyStyleTheme {
        content()
    }
}

// ==================== MAIN SCREEN PREVIEWS ====================

@Preview(
    name = "Checkout Screen - Complete",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun CheckoutScreenCompletePreview() {
    CheckoutPreviewWrapper {
        CheckoutScreen()
    }
}

@Preview(
    name = "Checkout Screen - Empty State",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun CheckoutScreenEmptyPreview() {
    CheckoutPreviewWrapper {
        CheckoutScreen(
            viewModel = CheckoutViewModel(CheckoutRepositoryEmptyImpl())
        )
    }
}

// ==================== HEADER PREVIEWS ====================

@Preview(
    name = "Checkout Header",
    showBackground = true,
    widthDp = 360
)
@Composable
fun CheckoutHeaderPreview() {
    CheckoutPreviewWrapper {
        CheckoutHeader(onBackPressed = {})
    }
}

// ==================== SHIPPING SECTION PREVIEWS ====================

@Preview(
    name = "Shipping Information Section - Filled",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ShippingInformationSectionFilledPreview() {
    CheckoutPreviewWrapper {
        ShippingInformationSection(
            shippingInfo = CheckoutPreviewData.sampleShippingInfo,
            onShippingInfoChanged = {}
        )
    }
}

@Preview(
    name = "Shipping Information Section - Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ShippingInformationSectionEmptyPreview() {
    CheckoutPreviewWrapper {
        ShippingInformationSection(
            shippingInfo = ShippingInfo(),
            onShippingInfoChanged = {}
        )
    }
}

@Preview(
    name = "Shipping TextField - Empty",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ShippingTextFieldEmptyPreview() {
    CheckoutPreviewWrapper {
        ShippingTextField(
            value = "",
            placeholder = "Full Name",
            onValueChange = {}
        )
    }
}

@Preview(
    name = "Shipping TextField - Filled",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ShippingTextFieldFilledPreview() {
    CheckoutPreviewWrapper {
        ShippingTextField(
            value = CheckoutPreviewData.sampleShippingInfo.fullName,
            placeholder = "Full Name",
            onValueChange = {}
        )
    }
}

// ==================== PAYMENT SECTION PREVIEWS ====================

@Preview(
    name = "Payment Method Section",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PaymentMethodSectionPreview() {
    CheckoutPreviewWrapper {
        PaymentMethodSection(
            paymentMethods = CheckoutPreviewData.samplePaymentMethods,
            onPaymentMethodSelected = {}
        )
    }
}

@Preview(
    name = "Payment Method Item - Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PaymentMethodItemSelectedPreview() {
    CheckoutPreviewWrapper {
        PaymentMethodItem(
            paymentMethod = CheckoutPreviewData.samplePaymentMethods.first(),
            onSelected = {}
        )
    }
}

@Preview(
    name = "Payment Method Item - Unselected",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PaymentMethodItemUnselectedPreview() {
    CheckoutPreviewWrapper {
        PaymentMethodItem(
            paymentMethod = CheckoutPreviewData.samplePaymentMethods.last(),
            onSelected = {}
        )
    }
}

// ==================== ORDER SUMMARY PREVIEWS ====================

@Preview(
    name = "Order Summary Section",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderSummarySectionPreview() {
    CheckoutPreviewWrapper {
        OrderSummarySection(
            checkoutItems = CheckoutPreviewData.sampleCheckoutItems,
            orderSummary = CheckoutPreviewData.sampleOrderSummary
        )
    }
}

@Preview(
    name = "Order Summary Section - Single Item",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderSummarySectionSingleItemPreview() {
    CheckoutPreviewWrapper {
        OrderSummarySection(
            checkoutItems = listOf(CheckoutPreviewData.sampleSingleItem),
            orderSummary = CheckoutOrderSummary(
                subtotal = 25.00,
                shipping = 5.00,
                total = 30.00
            )
        )
    }
}

@Preview(
    name = "Order Item",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderItemPreview() {
    CheckoutPreviewWrapper {
        OrderItem(item = CheckoutPreviewData.sampleSingleItem)
    }
}

@Preview(
    name = "Order Summary Details",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderSummaryDetailsPreview() {
    CheckoutPreviewWrapper {
        OrderSummaryDetails(
            orderSummary = CheckoutPreviewData.sampleOrderSummary
        )
    }
}

@Preview(
    name = "Order Summary Row - Regular",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderSummaryRowRegularPreview() {
    CheckoutPreviewWrapper {
        OrderSummaryRow(
            label = "Subtotal",
            value = "$${String.format("%.2f", CheckoutPreviewData.sampleOrderSummary.subtotal)}",
            isTotal = false
        )
    }
}

@Preview(
    name = "Order Summary Row - Total",
    showBackground = true,
    widthDp = 360
)
@Composable
fun OrderSummaryRowTotalPreview() {
    CheckoutPreviewWrapper {
        OrderSummaryRow(
            label = "Total",
            value = "$${String.format("%.2f", CheckoutPreviewData.sampleOrderSummary.total)}",
            isTotal = true
        )
    }
}

// ==================== BUTTON PREVIEWS ====================

@Preview(
    name = "Pay Now Button - Enabled",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PayNowButtonEnabledPreview() {
    CheckoutPreviewWrapper {
        PayNowButton(
            isEnabled = true,
            isLoading = false,
            onPayNowClick = {}
        )
    }
}

@Preview(
    name = "Pay Now Button - Disabled",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PayNowButtonDisabledPreview() {
    CheckoutPreviewWrapper {
        PayNowButton(
            isEnabled = false,
            isLoading = false,
            onPayNowClick = {}
        )
    }
}

@Preview(
    name = "Pay Now Button - Loading",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PayNowButtonLoadingPreview() {
    CheckoutPreviewWrapper {
        PayNowButton(
            isEnabled = true,
            isLoading = true,
            onPayNowClick = {}
        )
    }
}

// ==================== EDGE CASES PREVIEWS ====================

@Preview(
    name = "Pay Now Button - Disabled Loading",
    showBackground = true,
    widthDp = 360
)
@Composable
fun PayNowButtonDisabledLoadingPreview() {
    CheckoutPreviewWrapper {
        PayNowButton(
            isEnabled = false,
            isLoading = true,
            onPayNowClick = {}
        )
    }
}

/**
 * Empty repository implementation for testing empty states
 * Following Interface Segregation Principle
 */
class CheckoutRepositoryEmptyImpl : CheckoutRepository {
    override fun getCheckoutItems(): List<CheckoutItem> = emptyList()
    override fun getPaymentMethods(): List<PaymentMethod> = emptyList()
    override fun getShippingInfo(): ShippingInfo = ShippingInfo()
    override fun updateShippingInfo(shippingInfo: ShippingInfo) {}
    override fun selectPaymentMethod(paymentMethodId: String) {}
    override fun processPayment(): Boolean = false
} 