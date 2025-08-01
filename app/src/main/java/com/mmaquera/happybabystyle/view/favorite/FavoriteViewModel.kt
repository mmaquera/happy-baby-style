package com.mmaquera.happybabystyle.view.favorite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para FavoriteScreen siguiendo principios SOLID y Clean Architecture
 * 
 * Single Responsibility: Gestiona solo datos de productos favoritos y estado UI
 * Open/Closed: Extensible para futuras funcionalidades sin modificar código existente
 * Liskov Substitution: Sigue contrato de ViewModel
 * Interface Segregation: Funcionalidad específica y enfocada
 * Dependency Inversion: Depende de abstracciones (StateFlow) en lugar de implementaciones concretas
 */
class FavoriteViewModel : ViewModel() {
    
    // Estado UI siguiendo mejores prácticas de Google
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    /**
     * Carga datos de productos favoritos
     * En una app real, esto obtendría datos de un repositorio
     */
    private fun loadFavorites() {
        val favorites = listOf(
            FavoriteProduct(
                id = "1",
                name = "Cozy Cloud Onesie",
                imageRes = "ic_product_placeholder_1",
                price = "$24.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "2",
                name = "Dreamy Star Pajamas",
                imageRes = "ic_product_placeholder_2",
                price = "$29.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "3",
                name = "Little Explorer Romper",
                imageRes = "ic_product_placeholder_3",
                price = "$19.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "4",
                name = "Tiny Treasures Set",
                imageRes = "ic_product_placeholder_1",
                price = "$34.99",
                isFavorite = true
            )
        )
        
        _uiState.value = _uiState.value.copy(
            favorites = favorites,
            isLoading = false
        )
    }
    
    /**
     * Elimina un producto de favoritos
     * @param productId ID del producto a eliminar
     */
    fun removeFromFavorites(productId: String) {
        val currentFavorites = _uiState.value.favorites.toMutableList()
        currentFavorites.removeAll { it.id == productId }
        
        _uiState.value = _uiState.value.copy(
            favorites = currentFavorites
        )
    }
    
    /**
     * Alterna el estado de favorito de un producto
     * @param productId ID del producto a alternar
     */
    fun toggleFavorite(productId: String) {
        val currentFavorites = _uiState.value.favorites.map { product ->
            if (product.id == productId) {
                product.copy(isFavorite = !product.isFavorite)
            } else {
                product
            }
        }
        
        _uiState.value = _uiState.value.copy(
            favorites = currentFavorites
        )
    }
    
    /**
     * Busca productos en favoritos
     * @param query Término de búsqueda
     */
    fun searchFavorites(query: String) {
        if (query.isBlank()) {
            loadFavorites() // Restaurar lista completa si búsqueda está vacía
            return
        }
        
        val allFavorites = listOf(
            FavoriteProduct(
                id = "1",
                name = "Cozy Cloud Onesie",
                imageRes = "ic_product_placeholder_1",
                price = "$24.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "2",
                name = "Dreamy Star Pajamas",
                imageRes = "ic_product_placeholder_2",
                price = "$29.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "3",
                name = "Little Explorer Romper",
                imageRes = "ic_product_placeholder_3",
                price = "$19.99",
                isFavorite = true
            ),
            FavoriteProduct(
                id = "4",
                name = "Tiny Treasures Set",
                imageRes = "ic_product_placeholder_1",
                price = "$34.99",
                isFavorite = true
            )
        )
        
        val filteredFavorites = allFavorites.filter { product ->
            product.name.contains(query, ignoreCase = true)
        }
        
        _uiState.value = _uiState.value.copy(
            favorites = filteredFavorites
        )
    }
    
    /**
     * Ordena productos favoritos por nombre
     */
    fun sortFavoritesByName() {
        val sortedFavorites = _uiState.value.favorites.sortedBy { it.name }
        _uiState.value = _uiState.value.copy(
            favorites = sortedFavorites
        )
    }
    
    /**
     * Ordena productos favoritos por precio
     */
    fun sortFavoritesByPrice() {
        val sortedFavorites = _uiState.value.favorites.sortedBy { 
            it.price.replace("$", "").toDoubleOrNull() ?: 0.0 
        }
        _uiState.value = _uiState.value.copy(
            favorites = sortedFavorites
        )
    }
}

/**
 * Clase de datos para estado UI siguiendo mejores prácticas de Google
 * Inmutable y representa el estado completo de la UI
 */
data class FavoriteUiState(
    val favorites: List<FavoriteProduct> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = ""
)

/**
 * Clase de datos representando un producto favorito
 * Inmutable y sigue mejores prácticas de data classes
 */
data class FavoriteProduct(
    val id: String,
    val name: String,
    val imageRes: String,
    val price: String,
    val isFavorite: Boolean
) 