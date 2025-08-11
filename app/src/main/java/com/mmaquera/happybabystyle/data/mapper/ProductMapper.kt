package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.graphql.fragment.ProductBasicInfo
import com.mmaquera.happybabystyle.graphql.fragment.CategoryBasicInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Mapper dedicado para transformar datos de productos de GraphQL al modelo de dominio
 * Responsabilidad única: Transformación de datos de productos
 */
class ProductMapper {
    
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME
    
    /**
     * Mapea ProductBasicInfo de GraphQL a Product del dominio
     */
    fun mapFromProductBasicInfo(productInfo: ProductBasicInfo): Product {
        return Product(
            id = productInfo.id,
            name = productInfo.name,
            description = productInfo.description ?: "",
            price = productInfo.price.toDouble(),
            salePrice = null, // No disponible en ProductBasicInfo
            sku = productInfo.sku,
            images = productInfo.images,
            tags = emptyList(), // No disponible en ProductBasicInfo
            isActive = productInfo.isActive,
            stockQuantity = productInfo.stockQuantity,
            rating = 0.0f, // No disponible en ProductBasicInfo
            reviewCount = 0, // No disponible en ProductBasicInfo
            createdAt = productInfo.createdAt,
            updatedAt = productInfo.updatedAt,
            currentPrice = productInfo.price.toDouble(), // Usar precio base
            hasDiscount = false, // No disponible en ProductBasicInfo
            discountPercentage = 0, // No disponible en ProductBasicInfo
            totalStock = productInfo.stockQuantity, // Usar stockQuantity
            isInStock = productInfo.stockQuantity > 0,
            category = null // No disponible en ProductBasicInfo
        )
    }
    
    /**
     * Mapea CategoryBasicInfo de GraphQL a Category del dominio
     */
    fun mapFromCategoryBasicInfo(categoryInfo: CategoryBasicInfo): Category {
        return Category(
            id = categoryInfo.id,
            name = categoryInfo.name,
            description = categoryInfo.description ?: "",
            slug = categoryInfo.slug,
            imageUrl = categoryInfo.image,
            isActive = categoryInfo.isActive,
            sortOrder = categoryInfo.sortOrder,
            createdAt = categoryInfo.createdAt,
            updatedAt = categoryInfo.updatedAt
        )
    }
    
    /**
     * Mapea ProductBasicInfo.Category a Category del dominio (si existe)
     * Nota: Esta función puede no ser necesaria dependiendo de la estructura real
     */
    fun mapFromProductCategory(categoryInfo: CategoryBasicInfo): Category {
        return Category(
            id = categoryInfo.id,
            name = categoryInfo.name,
            description = categoryInfo.description ?: "",
            slug = categoryInfo.slug,
            imageUrl = categoryInfo.image,
            isActive = categoryInfo.isActive,
            sortOrder = categoryInfo.sortOrder,
            createdAt = categoryInfo.createdAt,
            updatedAt = categoryInfo.updatedAt
        )
    }
    
    /**
     * Parsea string de fecha a LocalDateTime
     */
    private fun parseDateTime(dateString: String?): LocalDateTime? {
        return try {
            dateString?.let { LocalDateTime.parse(it, dateFormatter) }
        } catch (e: Exception) {
            null
        }
    }
}
