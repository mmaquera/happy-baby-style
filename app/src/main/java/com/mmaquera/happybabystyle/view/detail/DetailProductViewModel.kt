package com.mmaquera.happybabystyle.view.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductDetail(
    val id: String = "1",
    val name: String = "Adorable Baby Outfit",
    val description: String = "This adorable baby outfit is perfect for your little one's first adventures. Made from soft, breathable cotton, it ensures comfort all day long. The set includes a charming top and matching bottoms, designed with playful patterns and easy-to-use snaps for quick changes. Available in various sizes to fit newborns to toddlers, this outfit is a must-have for any baby's wardrobe.",
    val imageUrl: String = "",
    val sizes: List<String> = listOf("0-3M", "3-6M", "6-12M"),
    val selectedSize: String = "0-3M",
    val rating: Float = 4.5f,
    val reviewCount: Int = 120,
    val price: Double = 29.99,
    val isInCart: Boolean = false
)

data class ReviewRating(
    val stars: Int,
    val percentage: Int,
    val count: Int
)

data class DetailProductUiState(
    val product: ProductDetail = ProductDetail(),
    val reviewRatings: List<ReviewRating> = listOf(
        ReviewRating(5, 40, 48),
        ReviewRating(4, 30, 36),
        ReviewRating(3, 15, 18),
        ReviewRating(2, 10, 12),
        ReviewRating(1, 5, 6)
    ),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailProductViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetailProductUiState())
    val uiState: StateFlow<DetailProductUiState> = _uiState.asStateFlow()
    
    init {
        loadProductDetail()
    }
    
    private fun loadProductDetail() {
        // Simulate loading product data
        _uiState.value = _uiState.value.copy(
            product = ProductDetail(),
            isLoading = false
        )
    }
    
    fun selectSize(size: String) {
        _uiState.value = _uiState.value.copy(
            product = _uiState.value.product.copy(selectedSize = size)
        )
    }
    
    fun addToCart() {
        _uiState.value = _uiState.value.copy(
            product = _uiState.value.product.copy(isInCart = true)
        )
        // Here you would typically call a repository method to add to cart
    }
    
    fun getProductName(): String = _uiState.value.product.name
    
    fun getProductDescription(): String = _uiState.value.product.description
    
    fun getProductSizes(): List<String> = _uiState.value.product.sizes
    
    fun getSelectedSize(): String = _uiState.value.product.selectedSize
    
    fun getProductRating(): Float = _uiState.value.product.rating
    
    fun getReviewCount(): Int = _uiState.value.product.reviewCount
    
    fun getReviewRatings(): List<ReviewRating> = _uiState.value.reviewRatings
    
    fun getProductPrice(): Double = _uiState.value.product.price
} 