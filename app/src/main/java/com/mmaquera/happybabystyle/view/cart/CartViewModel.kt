package com.mmaquera.happybabystyle.view.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing a cart item
 * Following Single Responsibility Principle - only holds cart item data
 */
data class CartItem(
    val id: String,
    val name: String,
    val size: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)

/**
 * Data class representing order summary
 * Following Single Responsibility Principle - only holds order summary data
 */
data class OrderSummary(
    val subtotal: Double,
    val shipping: Double,
    val tax: Double,
    val total: Double
) {
    companion object {
        fun calculate(items: List<CartItem>): OrderSummary {
            val subtotal = items.sumOf { it.price * it.quantity }
            val shipping = 0.0 // Free shipping
            val tax = subtotal * 0.08 // 8% tax
            val total = subtotal + shipping + tax
            return OrderSummary(subtotal, shipping, tax, total)
        }
    }
}

/**
 * Interface for cart repository operations
 * Following Interface Segregation Principle - only cart-related operations
 */
interface CartRepository {
    fun getCartItems(): List<CartItem>
    fun updateItemQuantity(itemId: String, quantity: Int)
    fun removeItem(itemId: String)
    fun clearCart()
}

/**
 * Repository implementation for cart
 * Following Single Responsibility Principle - only handles cart data
 */
class CartRepositoryImpl : CartRepository {
    private val cartItems = mutableListOf(
        CartItem(
            id = "1",
            name = "Cozy Bear Onesie",
            size = "Size 6-12M",
            price = 15.00,
            quantity = 1,
            imageUrl = "ic_product_placeholder_1"
        ),
        CartItem(
            id = "2",
            name = "Rainbow Romper",
            size = "Size 12-18M",
            price = 18.00,
            quantity = 2,
            imageUrl = "ic_product_placeholder_2"
        ),
        CartItem(
            id = "3",
            name = "Starry Night Pajamas",
            size = "Size 0-3M",
            price = 12.00,
            quantity = 1,
            imageUrl = "ic_product_placeholder_3"
        )
    )

    override fun getCartItems(): List<CartItem> = cartItems.toList()

    override fun updateItemQuantity(itemId: String, quantity: Int) {
        val index = cartItems.indexOfFirst { it.id == itemId }
        if (index != -1) {
            if (quantity <= 0) {
                cartItems.removeAt(index)
            } else {
                cartItems[index] = cartItems[index].copy(quantity = quantity)
            }
        }
    }

    override fun removeItem(itemId: String) {
        cartItems.removeAll { it.id == itemId }
    }

    override fun clearCart() {
        cartItems.clear()
    }
}

/**
 * ViewModel for CartScreen
 * Following Single Responsibility Principle - only manages cart UI state
 * Following Dependency Inversion Principle - depends on abstraction (CartRepository)
 */
class CartViewModel(
    private val repository: CartRepository = CartRepositoryImpl()
) : ViewModel() {
    
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()
    
    private val _orderSummary = MutableStateFlow(OrderSummary(0.0, 0.0, 0.0, 0.0))
    val orderSummary: StateFlow<OrderSummary> = _orderSummary.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadCart()
    }
    
    /**
     * Loads cart items from repository
     * Following Open/Closed Principle - can be extended without modification
     */
    private fun loadCart() {
        _isLoading.value = true
        try {
            val items = repository.getCartItems()
            _cartItems.value = items
            _orderSummary.value = OrderSummary.calculate(items)
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Increases item quantity
     * Following Single Responsibility Principle - only handles quantity increase
     */
    fun increaseQuantity(itemId: String) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = currentItems[index]
            currentItems[index] = item.copy(quantity = item.quantity + 1)
            repository.updateItemQuantity(itemId, item.quantity + 1)
            _cartItems.value = currentItems
            _orderSummary.value = OrderSummary.calculate(currentItems)
        }
    }
    
    /**
     * Decreases item quantity
     * Following Single Responsibility Principle - only handles quantity decrease
     */
    fun decreaseQuantity(itemId: String) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = currentItems[index]
            val newQuantity = (item.quantity - 1).coerceAtLeast(0)
            if (newQuantity == 0) {
                currentItems.removeAt(index)
                repository.removeItem(itemId)
            } else {
                currentItems[index] = item.copy(quantity = newQuantity)
                repository.updateItemQuantity(itemId, newQuantity)
            }
            _cartItems.value = currentItems
            _orderSummary.value = OrderSummary.calculate(currentItems)
        }
    }
    
    /**
     * Removes item from cart
     */
    fun removeItem(itemId: String) {
        repository.removeItem(itemId)
        val currentItems = _cartItems.value.filter { it.id != itemId }
        _cartItems.value = currentItems
        _orderSummary.value = OrderSummary.calculate(currentItems)
    }
    
    /**
     * Clears entire cart
     */
    fun clearCart() {
        repository.clearCart()
        _cartItems.value = emptyList()
        _orderSummary.value = OrderSummary(0.0, 0.0, 0.0, 0.0)
    }
    
    /**
     * Proceeds to checkout
     */
    fun checkout() {
        // TODO: Implement checkout logic
        // This would typically navigate to a checkout screen or process payment
    }
} 