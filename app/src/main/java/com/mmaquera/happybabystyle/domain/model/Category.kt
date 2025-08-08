package com.mmaquera.happybabystyle.domain.model

import java.time.LocalDateTime

/**
 * Modelo de dominio para Category
 * Representa las categorías de productos en la aplicación
 * 
 * Ejemplos de categorías para Happy Baby Style:
 * - Bodysuits
 * - Hats  
 * - Outfits
 * - Pajamas
 * - Socks
 */
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val slug: String,
    val imageUrl: String?,
    val isActive: Boolean,
    val sortOrder: Int,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    
    /**
     * Business logic: verifica si la categoría está disponible
     */
    fun isAvailable(): Boolean {
        return isActive
    }
    
    /**
     * Business logic: obtiene la URL de imagen o placeholder
     */
    fun getImageUrlOrPlaceholder(): String {
        return imageUrl ?: "placeholder_category_image"
    }
    
    /**
     * Business logic: genera URL amigable para navegación
     */
    fun getFriendlyUrl(): String {
        return "/category/$slug"
    }
}

/**
 * Modelo de dominio para ProductVariant
 * Representa las variaciones de un producto (talla, color, etc.)
 */
data class ProductVariant(
    val id: String,
    val productId: String,
    val size: String?,
    val color: String?,
    val sku: String,
    val price: Double?,
    val stockQuantity: Int,
    val isActive: Boolean,
    val isInStock: Boolean
) {
    
    /**
     * Business logic: verifica si la variante está disponible
     */
    fun isAvailable(): Boolean {
        return isActive && isInStock && stockQuantity > 0
    }
    
    /**
     * Business logic: obtiene descripción de la variante
     */
    fun getVariantDescription(): String {
        val parts = mutableListOf<String>()
        size?.let { parts.add("Talla: $it") }
        color?.let { parts.add("Color: $it") }
        return parts.joinToString(" | ")
    }
    
    /**
     * Business logic: obtiene precio o precio del producto padre
     */
    fun getEffectivePrice(parentPrice: Double): Double {
        return price ?: parentPrice
    }
}

/**
 * Resultado paginado genérico para listas
 */
data class PaginatedResult<T>(
    val items: List<T>,
    val total: Int,
    val hasMore: Boolean
) {
    
    /**
     * Business logic: verifica si hay elementos
     */
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }
    
    /**
     * Business logic: obtiene el número de página actual
     */
    fun getCurrentPage(pageSize: Int): Int {
        return if (pageSize > 0) (items.size / pageSize) + 1 else 1
    }
    
    /**
     * Business logic: calcula páginas totales
     */
    fun getTotalPages(pageSize: Int): Int {
        return if (pageSize > 0) ((total + pageSize - 1) / pageSize) else 1
    }
}