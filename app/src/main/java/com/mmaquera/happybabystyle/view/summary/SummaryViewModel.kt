package com.mmaquera.happybabystyle.view.summary

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

/**
 * Data class representing order summary information
 * Following Single Responsibility Principle - only holds order summary data
 */
data class OrderSummaryInfo(
    val orderNumber: String,
    val orderDate: String,
    val total: Double,
    val status: OrderStatus
)

/**
 * Enum representing order status
 * Following Single Responsibility Principle - only defines order status
 */
enum class OrderStatus {
    PLACED,
    CONFIRMED,
    SHIPPED,
    DELIVERED
}

/**
 * Data class representing UI state for summary screen
 * Following Single Responsibility Principle - only holds UI state data
 */
data class SummaryUiState(
    val orderSummary: OrderSummaryInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Interface for summary repository operations
 * Following Interface Segregation Principle - only summary-related operations
 */
interface SummaryRepository {
    fun getOrderSummary(): OrderSummaryInfo
}

/**
 * Repository implementation for summary
 * Following Single Responsibility Principle - only handles summary data
 */
class SummaryRepositoryImpl : SummaryRepository {
    override fun getOrderSummary(): OrderSummaryInfo {
        // Dummy data following the Figma design
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        val currentDate = dateFormat.format(Date())
        
        return OrderSummaryInfo(
            orderNumber = "#123456789",
            orderDate = currentDate,
            total = 75.00,
            status = OrderStatus.PLACED
        )
    }
}

/**
 * ViewModel for SummaryScreen
 * Following Single Responsibility Principle - only manages summary UI state
 * Following Dependency Inversion Principle - depends on abstraction (SummaryRepository)
 */
class SummaryViewModel(
    private val repository: SummaryRepository = SummaryRepositoryImpl()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SummaryUiState(isLoading = true))
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()
    
    init {
        loadOrderSummary()
    }
    
    /**
     * Loads order summary from repository
     * Following Open/Closed Principle - can be extended without modification
     */
    private fun loadOrderSummary() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            val orderSummary = repository.getOrderSummary()
            _uiState.value = SummaryUiState(
                orderSummary = orderSummary,
                isLoading = false
            )
        } catch (e: Exception) {
            _uiState.value = SummaryUiState(
                isLoading = false,
                error = e.message ?: "Unknown error occurred"
            )
        }
    }
    
    /**
     * Refreshes order summary
     * Following Single Responsibility Principle - only handles refresh
     */
    fun refreshOrderSummary() {
        loadOrderSummary()
    }
    
    /**
     * Continues shopping action
     * Following Single Responsibility Principle - only handles navigation
     */
    fun continueShopping() {
        // TODO: Implement navigation to home screen
        // This would typically navigate to the home screen
    }
} 