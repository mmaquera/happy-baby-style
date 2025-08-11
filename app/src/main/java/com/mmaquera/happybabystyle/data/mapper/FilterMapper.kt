package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.domain.model.Product

/**
 * Mapper dedicado para aplicar filtros a productos
 * Responsabilidad única: Filtrado de productos según criterios
 */
class FilterMapper {
    
    /**
     * Aplica filtros a la lista de productos
     */
    fun applyFilters(
        products: List<Product>,
        categoryId: String?,
        searchQuery: String?,
        minPrice: Double?,
        maxPrice: Double?,
        inStock: Boolean?
    ): List<Product> {
        return products.filter { product ->
            // Filtro por categoría
            if (categoryId != null && product.category?.id != categoryId) return@filter false
            
            // Filtro por búsqueda
            if (searchQuery != null) {
                val query = searchQuery.lowercase()
                val matchesName = product.name.lowercase().contains(query)
                val matchesDescription = product.description?.lowercase()?.contains(query) ?: false
                if (!matchesName && !matchesDescription) return@filter false
            }
            
            // Filtro por precio mínimo
            if (minPrice != null && product.currentPrice < minPrice) return@filter false
            
            // Filtro por precio máximo
            if (maxPrice != null && product.currentPrice > maxPrice) return@filter false
            
            // Filtro por stock
            if (inStock == true && !product.isInStock) return@filter false
            
            true
        }
    }
    
    /**
     * Aplica filtro de búsqueda por texto
     */
    fun applySearchFilter(products: List<Product>, searchQuery: String): List<Product> {
        if (searchQuery.isBlank()) return products
        
        val query = searchQuery.lowercase()
        return products.filter { product ->
            product.name.lowercase().contains(query) ||
            product.description?.lowercase()?.contains(query) == true ||
            product.tags.any { it.lowercase().contains(query) }
        }
    }
    
    /**
     * Aplica filtro por rango de precios
     */
    fun applyPriceRangeFilter(products: List<Product>, minPrice: Double?, maxPrice: Double?): List<Product> {
        return products.filter { product ->
            val price = product.currentPrice
            (minPrice == null || price >= minPrice) &&
            (maxPrice == null || price <= maxPrice)
        }
    }
    
    /**
     * Aplica filtro por categoría
     */
    fun applyCategoryFilter(products: List<Product>, categoryId: String?): List<Product> {
        if (categoryId == null) return products
        return products.filter { it.category?.id == categoryId }
    }
    
    /**
     * Aplica filtro por disponibilidad de stock
     */
    fun applyStockFilter(products: List<Product>, inStock: Boolean?): List<Product> {
        if (inStock == null) return products
        return products.filter { it.isInStock == inStock }
    }
}
