package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.PaginatedResult
import com.mmaquera.happybabystyle.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use Case para obtener productos con filtros
 * Implementa lógica de negocio específica para búsqueda de productos
 * 
 * Principios aplicados:
 * - Single Responsibility: Solo maneja obtención de productos
 * - Dependency Inversion: Depende de abstracción (ProductRepository)
 * - Clean Architecture: Lógica de negocio pura sin dependencias externas
 */
class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    
    /**
     * Ejecuta la búsqueda de productos con filtros opcionales
     * 
     * @param categoryId Filtrar por categoría
     * @param searchQuery Texto de búsqueda en nombre/descripción
     * @param minPrice Precio mínimo
     * @param maxPrice Precio máximo  
     * @param inStock Solo productos disponibles
     * @param page Número de página (empezando en 1)
     * @param pageSize Cantidad de productos por página
     * @return Flow con resultado paginado de productos
     */
    suspend operator fun invoke(
        categoryId: String? = null,
        searchQuery: String? = null,
        minPrice: Double? = null,
        maxPrice: Double? = null,
        inStock: Boolean? = null,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<PaginatedResult<Product>>> {
        
        // Validaciones de business logic
        require(page >= 1) { "La página debe ser mayor a 0" }
        require(pageSize in 1..100) { "El tamaño de página debe estar entre 1 y 100" }
        
        if (minPrice != null && maxPrice != null) {
            require(minPrice <= maxPrice) { "El precio mínimo no puede ser mayor al máximo" }
        }
        
        minPrice?.let { require(it >= 0) { "El precio mínimo no puede ser negativo" } }
        maxPrice?.let { require(it >= 0) { "El precio máximo no puede ser negativo" } }
        
        // Calcular offset para paginación
        val offset = (page - 1) * pageSize
        
        // Normalizar query de búsqueda
        val normalizedQuery = searchQuery?.trim()?.takeIf { it.isNotEmpty() }
        
        return productRepository.getProducts(
            categoryId = categoryId,
            searchQuery = normalizedQuery,
            minPrice = minPrice,
            maxPrice = maxPrice,
            inStock = inStock,
            limit = pageSize,
            offset = offset
        )
    }
    
    /**
     * Versión simplificada para obtener productos destacados
     */
    suspend fun getFeaturedProducts(limit: Int = 10): Flow<Result<PaginatedResult<Product>>> {
        require(limit in 1..50) { "El límite debe estar entre 1 y 50" }
        
        return productRepository.getProducts(
            categoryId = null,
            searchQuery = null,
            minPrice = null,
            maxPrice = null,
            inStock = true, // Solo productos disponibles para destacados
            limit = limit,
            offset = 0
        )
    }
    
    /**
     * Búsqueda de productos por texto
     */
    suspend fun searchProducts(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<PaginatedResult<Product>>> {
        require(query.trim().isNotEmpty()) { "La consulta de búsqueda no puede estar vacía" }
        require(query.length >= 2) { "La consulta debe tener al menos 2 caracteres" }
        
        return invoke(
            searchQuery = query,
            page = page,
            pageSize = pageSize
        )
    }
}