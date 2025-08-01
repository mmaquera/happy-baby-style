package com.mmaquera.happybabystyle.view.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing shipping information
 * Following Single Responsibility Principle - only holds shipping data
 */
data class ShippingInfo(
    val fullName: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = ""
)

/**
 * Data class representing payment method
 * Following Single Responsibility Principle - only holds payment method data
 */
data class PaymentMethod(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)

/**
 * Data class representing checkout item
 * Following Single Responsibility Principle - only holds checkout item data
 */
data class CheckoutItem(
    val id: String,
    val name: String,
    val size: String,
    val price: Double,
    val imageUrl: String
)

/**
 * Data class representing order summary
 * Following Single Responsibility Principle - only holds order summary data
 */
data class CheckoutOrderSummary(
    val subtotal: Double,
    val shipping: Double,
    val total: Double
) {
    companion object {
        fun calculate(items: List<CheckoutItem>): CheckoutOrderSummary {
            val subtotal = items.sumOf { it.price }
            val shipping = 5.0 // Fixed shipping cost
            val total = subtotal + shipping
            return CheckoutOrderSummary(subtotal, shipping, total)
        }
    }
}

/**
 * Interface for checkout repository operations
 * Following Interface Segregation Principle - only checkout-related operations
 */
interface CheckoutRepository {
    fun getCheckoutItems(): List<CheckoutItem>
    fun getPaymentMethods(): List<PaymentMethod>
    fun getShippingInfo(): ShippingInfo
    fun updateShippingInfo(shippingInfo: ShippingInfo)
    fun selectPaymentMethod(paymentMethodId: String)
    fun processPayment(): Boolean
}

/**
 * Repository implementation for checkout
 * Following Single Responsibility Principle - only handles checkout data
 */
class CheckoutRepositoryImpl : CheckoutRepository {
    private val checkoutItems = listOf(
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

    private val paymentMethods = mutableListOf(
        PaymentMethod(id = "1", name = "Credit Card", isSelected = true),
        PaymentMethod(id = "2", name = "PayPal", isSelected = false)
    )

    private var shippingInfo = ShippingInfo(
        fullName = "Maria Garcia",
        address = "123 Baby Street",
        city = "Miami",
        state = "FL",
        zipCode = "33101"
    )

    override fun getCheckoutItems(): List<CheckoutItem> = checkoutItems

    override fun getPaymentMethods(): List<PaymentMethod> = paymentMethods.toList()

    override fun getShippingInfo(): ShippingInfo = shippingInfo

    override fun updateShippingInfo(newShippingInfo: ShippingInfo) {
        shippingInfo = newShippingInfo
    }

    override fun selectPaymentMethod(paymentMethodId: String) {
        // Note: In a real implementation, this would update the database or persistent storage
        // For now, we'll just simulate the selection without modifying the list
    }

    override fun processPayment(): Boolean {
        // Simulate payment processing
        return true
    }
}

/**
 * ViewModel for CheckoutScreen
 * Following Single Responsibility Principle - only manages checkout UI state
 * Following Dependency Inversion Principle - depends on abstraction (CheckoutRepository)
 */
class CheckoutViewModel(
    private val repository: CheckoutRepository = CheckoutRepositoryImpl()
) : ViewModel() {
    
    private val _checkoutItems = MutableStateFlow<List<CheckoutItem>>(emptyList())
    val checkoutItems: StateFlow<List<CheckoutItem>> = _checkoutItems.asStateFlow()
    
    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods: StateFlow<List<PaymentMethod>> = _paymentMethods.asStateFlow()
    
    private val _shippingInfo = MutableStateFlow(ShippingInfo())
    val shippingInfo: StateFlow<ShippingInfo> = _shippingInfo.asStateFlow()
    
    private val _orderSummary = MutableStateFlow(CheckoutOrderSummary(0.0, 0.0, 0.0))
    val orderSummary: StateFlow<CheckoutOrderSummary> = _orderSummary.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isPaymentProcessing = MutableStateFlow(false)
    val isPaymentProcessing: StateFlow<Boolean> = _isPaymentProcessing.asStateFlow()
    
    init {
        loadCheckoutData()
    }
    
    /**
     * Loads checkout data from repository
     * Following Open/Closed Principle - can be extended without modification
     */
    private fun loadCheckoutData() {
        _isLoading.value = true
        try {
            val items = repository.getCheckoutItems()
            val paymentMethods = repository.getPaymentMethods()
            val shippingInfoData = repository.getShippingInfo()
            
            _checkoutItems.value = items
            _paymentMethods.value = paymentMethods
            _shippingInfo.value = shippingInfoData
            _orderSummary.value = CheckoutOrderSummary.calculate(items)
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Updates shipping information
     * Following Single Responsibility Principle - only handles shipping info updates
     */
    fun updateShippingInfo(
        fullName: String = _shippingInfo.value.fullName,
        address: String = _shippingInfo.value.address,
        city: String = _shippingInfo.value.city,
        state: String = _shippingInfo.value.state,
        zipCode: String = _shippingInfo.value.zipCode
    ) {
        val newShippingInfo = ShippingInfo(fullName, address, city, state, zipCode)
        repository.updateShippingInfo(newShippingInfo)
        _shippingInfo.value = newShippingInfo
    }
    
    /**
     * Selects a payment method
     * Following Single Responsibility Principle - only handles payment method selection
     */
    fun selectPaymentMethod(paymentMethodId: String) {
        repository.selectPaymentMethod(paymentMethodId)
        val updatedMethods = _paymentMethods.value.map { method ->
            method.copy(isSelected = method.id == paymentMethodId)
        }
        _paymentMethods.value = updatedMethods
    }
    
    /**
     * Processes payment
     * Following Single Responsibility Principle - only handles payment processing
     */
    fun processPayment() {
        _isPaymentProcessing.value = true
        try {
            val success = repository.processPayment()
            if (success) {
                // Handle successful payment
                // This could navigate to a success screen or clear cart
            }
        } finally {
            _isPaymentProcessing.value = false
        }
    }
    
    /**
     * Validates shipping information
     * Following Single Responsibility Principle - only handles validation
     */
    fun isShippingInfoValid(): Boolean {
        val info = _shippingInfo.value
        return info.fullName.isNotBlank() &&
                info.address.isNotBlank() &&
                info.city.isNotBlank() &&
                info.state.isNotBlank() &&
                info.zipCode.isNotBlank()
    }
    
    /**
     * Checks if checkout is ready for payment
     * Following Single Responsibility Principle - only handles readiness check
     */
    fun isCheckoutReady(): Boolean {
        return isShippingInfoValid() && 
               _paymentMethods.value.any { it.isSelected } &&
               _checkoutItems.value.isNotEmpty()
    }
} 