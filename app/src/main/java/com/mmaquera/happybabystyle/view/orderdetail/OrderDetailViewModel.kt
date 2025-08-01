package com.mmaquera.happybabystyle.view.orderdetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing an order item
 * Following Single Responsibility Principle - only holds order item data
 */
data class OrderItem(
    val id: String,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String
)

/**
 * Data class representing order payment details
 * Following Single Responsibility Principle - only holds payment data
 */
data class OrderPayment(
    val subtotal: Double,
    val shipping: Double,
    val tax: Double,
    val total: Double
)

/**
 * Data class representing shipping address
 * Following Single Responsibility Principle - only holds address data
 */
data class ShippingAddress(
    val name: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)

/**
 * Data class representing order status step
 * Following Single Responsibility Principle - only holds status step data
 */
data class OrderStatusStep(
    val title: String,
    val date: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean
)

/**
 * Data class representing order details
 * Following Single Responsibility Principle - only holds order details data
 */
data class OrderDetails(
    val orderNumber: String,
    val orderDate: String,
    val items: List<OrderItem>,
    val payment: OrderPayment,
    val shippingAddress: ShippingAddress,
    val statusSteps: List<OrderStatusStep>
)

/**
 * Interface for order repository operations
 * Following Interface Segregation Principle - only order-related operations
 */
interface OrderRepository {
    fun getOrderDetails(orderId: String): OrderDetails
}

/**
 * Repository implementation for order details
 * Following Single Responsibility Principle - only handles order data
 */
class OrderRepositoryImpl : OrderRepository {
    override fun getOrderDetails(orderId: String): OrderDetails {
        // Dummy data based on Figma design
        return OrderDetails(
            orderNumber = "123456789",
            orderDate = "June 15, 2024",
            items = listOf(
                OrderItem(
                    id = "1",
                    name = "1 x Blue Romper",
                    description = "Baby Romper - Size 6M",
                    quantity = 1,
                    price = 25.00,
                    imageUrl = "ic_product_placeholder_1"
                ),
                OrderItem(
                    id = "2",
                    name = "2 x Pink Socks",
                    description = "Baby Socks - Size 0-3M",
                    quantity = 2,
                    price = 8.00,
                    imageUrl = "ic_socks"
                ),
                OrderItem(
                    id = "3",
                    name = "1 x Yellow Hat",
                    description = "Baby Hat - Size 0-6M",
                    quantity = 1,
                    price = 12.00,
                    imageUrl = "ic_hat"
                )
            ),
            payment = OrderPayment(
                subtotal = 45.00,
                shipping = 5.00,
                tax = 2.50,
                total = 52.50
            ),
            shippingAddress = ShippingAddress(
                name = "Sophia Clark",
                street = "123 Main Street",
                city = "Anytown",
                state = "CA",
                zipCode = "91234"
            ),
            statusSteps = listOf(
                OrderStatusStep(
                    title = "Order Placed",
                    date = "June 15, 2024",
                    isCompleted = true,
                    isCurrent = false
                ),
                OrderStatusStep(
                    title = "Shipped",
                    date = "June 16, 2024",
                    isCompleted = true,
                    isCurrent = false
                ),
                OrderStatusStep(
                    title = "Delivered",
                    date = "June 18, 2024",
                    isCompleted = true,
                    isCurrent = true
                )
            )
        )
    }
}

/**
 * ViewModel for OrderDetailScreen
 * Following Single Responsibility Principle - only manages order detail UI state
 * Following Dependency Inversion Principle - depends on abstraction (OrderRepository)
 */
class OrderDetailViewModel(
    private val repository: OrderRepository = OrderRepositoryImpl()
) : ViewModel() {
    
    private val _orderDetails = MutableStateFlow<OrderDetails?>(null)
    val orderDetails: StateFlow<OrderDetails?> = _orderDetails.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Loads order details from repository
     * Following Open/Closed Principle - can be extended without modification
     */
    fun loadOrderDetails(orderId: String) {
        _isLoading.value = true
        _error.value = null
        
        try {
            val details = repository.getOrderDetails(orderId)
            _orderDetails.value = details
        } catch (e: Exception) {
            _error.value = "Failed to load order details: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Clears error state
     * Following Single Responsibility Principle - only handles error clearing
     */
    fun clearError() {
        _error.value = null
    }
} 