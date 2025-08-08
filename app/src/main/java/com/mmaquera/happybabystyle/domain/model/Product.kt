package com.mmaquera.happybabystyle.domain.model

import java.time.LocalDateTime

/**
 * Modelo de dominio para Product
 * Entidad central del negocio de e-commerce
 * 
 * Características:
 * - Inmutable (data class)
 * - Validaciones en el dominio
 * - Sin dependencias externas
 * - Business logic encapsulada
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val salePrice: Double? = null,
    val sku: String,
    val images: List<String>,
    val tags: List<String>,
    val isActive: Boolean,
    val stockQuantity: Int,
    val rating: Float,
    val reviewCount: Int,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val currentPrice: Double,
    val hasDiscount: Boolean,
    val discountPercentage: Int,
    val totalStock: Int,
    val isInStock: Boolean,
    val category: Category?
) {
    
    /**
     * Business logic: calcula el precio final del producto
     */
    fun getFinalPrice(): Double {
        return if (hasDiscount && salePrice != null) {
            salePrice
        } else {
            price
        }
    }
    
    /**
     * Business logic: verifica si el producto está disponible
     */
    fun isAvailable(): Boolean {
        return isActive && isInStock && stockQuantity > 0
    }
    
    /**
     * Business logic: calcula el ahorro por descuento
     */
    fun getSavingsAmount(): Double {
        return if (hasDiscount && salePrice != null) {
            price - salePrice
        } else {
            0.0
        }
    }
    
    /**
     * Business logic: obtiene la primera imagen o placeholder
     */
    fun getPrimaryImage(): String {
        return images.firstOrNull() ?: ""
    }
    
    /**
     * Business logic: formatea el precio para mostrar
     */
    fun getFormattedPrice(): String {
        return "$${String.format("%.2f", getFinalPrice())}"
    }
    
    /**
     * Business logic: formatea el precio original si hay descuento
     */
    fun getFormattedOriginalPrice(): String? {
        return if (hasDiscount && salePrice != null) {
            "$${String.format("%.2f", price)}"
        } else {
            null
        }
    }
}