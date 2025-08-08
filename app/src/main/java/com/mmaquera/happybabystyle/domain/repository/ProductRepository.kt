package com.mmaquera.happybabystyle.domain.repository

import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.domain.model.PaginatedResult
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz del repositorio de productos siguiendo Clean Architecture
 * Define el contrato para operaciones de productos sin especificar implementación
 * 
 * Principios SOLID aplicados:
 * - Interface Segregation: Solo métodos relacionados con productos
 * - Dependency Inversion: Define abstracción, no implementación
 * - Single Responsibility: Solo operaciones de productos
 */
interface ProductRepository {
    
    /**
     * Obtiene productos con filtros opcionales y paginación
     * 
     * @param categoryId ID de categoría para filtrar (opcional)
     * @param searchQuery Texto de búsqueda (opcional)
     * @param minPrice Precio mínimo (opcional)
     * @param maxPrice Precio máximo (opcional)
     * @param inStock Solo productos en stock (opcional)
     * @param limit Límite de productos por página
     * @param offset Offset para paginación
     * @return Flow con resultado paginado de productos
     */
    suspend fun getProducts(
        categoryId: String? = null,
        searchQuery: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        inStock: Boolean? = null,
        limit: Int = 20,
        offset: Int = 0
    ): Flow<Result<PaginatedResult<Product>>>
    
    /**
     * Obtiene un producto específico por su ID
     * 
     * @param productId ID del producto
     * @return Flow con el producto o null si no existe
     */
    suspend fun getProductById(productId: String): Flow<Result<Product?>>
    
    /**
     * Obtiene productos de una categoría específica
     * 
     * @param categoryId ID de la categoría
     * @param limit Límite de productos por página
     * @param offset Offset para paginación
     * @return Flow con resultado paginado de productos
     */
    suspend fun getProductsByCategory(
        categoryId: String,
        limit: Int = 20,
        offset: Int = 0
    ): Flow<Result<PaginatedResult<Product>>>
    
    /**
     * Obtiene todas las categorías disponibles
     * 
     * @return Flow con lista de categorías
     */
    suspend fun getCategories(): Flow<Result<List<Category>>>
}