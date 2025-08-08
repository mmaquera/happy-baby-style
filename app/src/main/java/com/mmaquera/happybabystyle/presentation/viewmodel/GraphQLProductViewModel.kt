package com.mmaquera.happybabystyle.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.domain.model.PaginatedResult
import com.mmaquera.happybabystyle.domain.usecase.GetProductsUseCase
import com.mmaquera.happybabystyle.domain.usecase.GetProductByIdUseCase
import com.mmaquera.happybabystyle.domain.usecase.GetCategoriesUseCase
import com.mmaquera.happybabystyle.util.ApolloServiceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel de ejemplo que demuestra el uso de GraphQL
 * Implementa Clean Architecture con GraphQL como fuente de datos
 * 
 * Características:
 * - Obtiene productos usando GraphQL
 * - Maneja estado de carga y errores
 * - Implementa búsqueda y filtros
 * - Cache automático con GraphQL
 */
class GraphQLProductViewModel : ViewModel() {
    
    // Use Cases - Ahora sin dependencia de Context
    private val getProductsUseCase: GetProductsUseCase = ApolloServiceProvider.getProductsUseCase()
    private val getProductByIdUseCase: GetProductByIdUseCase = ApolloServiceProvider.getProductByIdUseCase()
    private val getCategoriesUseCase: GetCategoriesUseCase = ApolloServiceProvider.getCategoriesUseCase()
    
    // State para productos
    private val _productsState = MutableStateFlow(ProductsUiState())
    val productsState: StateFlow<ProductsUiState> = _productsState.asStateFlow()
    
    // State para categorías
    private val _categoriesState = MutableStateFlow(CategoriesUiState())
    val categoriesState: StateFlow<CategoriesUiState> = _categoriesState.asStateFlow()
    
    // State para producto individual
    private val _productDetailState = MutableStateFlow(ProductDetailUiState())
    val productDetailState: StateFlow<ProductDetailUiState> = _productDetailState.asStateFlow()
    
    init {
        // Cargar datos iniciales
        loadCategories()
        loadProducts()
    }
    
    /**
     * Carga todas las categorías disponibles
     */
    fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = _categoriesState.value.copy(isLoading = true)
            
            getCategoriesUseCase()
                .catch { exception ->
                    _categoriesState.value = _categoriesState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { categories ->
                            _categoriesState.value = _categoriesState.value.copy(
                                isLoading = false,
                                categories = categories,
                                error = null
                            )
                        },
                        onFailure = { exception ->
                            _categoriesState.value = _categoriesState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Error al cargar categorías"
                            )
                        }
                    )
                }
        }
    }
    
    /**
     * Carga productos con filtros opcionales
     */
    fun loadProducts(
        categoryId: String? = null,
        searchQuery: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        inStock: Boolean? = null,
        page: Int = 1,
        pageSize: Int = 20
    ) {
        viewModelScope.launch {
            _productsState.value = _productsState.value.copy(isLoading = true)
            
            getProductsUseCase(
                categoryId = categoryId,
                searchQuery = searchQuery,
                minPrice = minPrice,
                maxPrice = maxPrice,
                inStock = inStock,
                page = page,
                pageSize = pageSize
            )
                .catch { exception ->
                    _productsState.value = _productsState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { paginatedResult ->
                            _productsState.value = _productsState.value.copy(
                                isLoading = false,
                                products = paginatedResult.items,
                                totalProducts = paginatedResult.total,
                                hasMore = paginatedResult.hasMore,
                                currentPage = page,
                                error = null
                            )
                        },
                        onFailure = { exception ->
                            _productsState.value = _productsState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Error al cargar productos"
                            )
                        }
                    )
                }
        }
    }
    
    /**
     * Busca productos por texto
     */
    fun searchProducts(query: String) {
        if (query.trim().length >= 2) {
            loadProducts(searchQuery = query.trim())
        } else if (query.trim().isEmpty()) {
            // Limpiar búsqueda
            loadProducts()
        }
    }
    
    /**
     * Filtra productos por categoría
     */
    fun filterByCategory(categoryId: String?) {
        loadProducts(categoryId = categoryId)
    }
    
    /**
     * Carga un producto específico por ID
     */
    fun loadProductDetail(productId: String) {
        viewModelScope.launch {
            _productDetailState.value = _productDetailState.value.copy(isLoading = true)
            
            getProductByIdUseCase(productId)
                .catch { exception ->
                    _productDetailState.value = _productDetailState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error desconocido"
                    )
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { product ->
                            _productDetailState.value = _productDetailState.value.copy(
                                isLoading = false,
                                product = product,
                                error = if (product == null) "Producto no encontrado" else null
                            )
                        },
                        onFailure = { exception ->
                            _productDetailState.value = _productDetailState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Error al cargar producto"
                            )
                        }
                    )
                }
        }
    }
    
    /**
     * Limpia errores de UI
     */
    fun clearError() {
        _productsState.value = _productsState.value.copy(error = null)
        _categoriesState.value = _categoriesState.value.copy(error = null)
        _productDetailState.value = _productDetailState.value.copy(error = null)
    }
    
    /**
     * Recarga todos los datos
     */
    fun refresh() {
        loadCategories()
        loadProducts()
    }
}

/**
 * Estado de UI para lista de productos
 */
data class ProductsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val totalProducts: Int = 0,
    val hasMore: Boolean = false,
    val currentPage: Int = 1,
    val error: String? = null
)

/**
 * Estado de UI para categorías
 */
data class CategoriesUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)

/**
 * Estado de UI para detalle de producto
 */
data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)