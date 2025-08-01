package com.mmaquera.happybabystyle.view.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class ProfileState(
    val user: User = User(),
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class User(
    val name: String = "Olivia Carter",
    val email: String = "olivia.carter@email.com",
    val profileImage: String = ""
)

data class Order(
    val id: String,
    val items: Int,
    val total: Double,
    val status: String = "Delivered"
)

sealed class ProfileEvent {
    object EditProfile : ProfileEvent()
    object ViewOrder : ProfileEvent()
    object Notifications : ProfileEvent()
    object PaymentMethods : ProfileEvent()
    object ShippingAddresses : ProfileEvent()
    object HelpSupport : ProfileEvent()
    object LogOut : ProfileEvent()
}

class ProfileViewModel : ViewModel() {
    
    var state by mutableStateOf(ProfileState())
        private set
    
    init {
        loadProfileData()
    }
    
    private fun loadProfileData() {
        // Dummy data for demonstration
        val dummyOrders = listOf(
            Order(id = "123456", items = 1, total = 25.0),
            Order(id = "789012", items = 2, total = 45.0)
        )
        
        state = state.copy(
            user = User(),
            orders = dummyOrders
        )
    }
    
    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EditProfile -> {
                // Handle edit profile action
            }
            is ProfileEvent.ViewOrder -> {
                // Handle view order action
            }
            is ProfileEvent.Notifications -> {
                // Handle notifications action
            }
            is ProfileEvent.PaymentMethods -> {
                // Handle payment methods action
            }
            is ProfileEvent.ShippingAddresses -> {
                // Handle shipping addresses action
            }
            is ProfileEvent.HelpSupport -> {
                // Handle help & support action
            }
            is ProfileEvent.LogOut -> {
                // Handle logout action
            }
        }
    }
} 