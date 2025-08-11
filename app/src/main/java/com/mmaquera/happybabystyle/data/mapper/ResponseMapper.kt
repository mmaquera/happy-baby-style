package com.mmaquera.happybabystyle.data.mapper

import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.domain.model.PaginatedResult
import com.mmaquera.happybabystyle.graphql.GetProductsQuery
import com.mmaquera.happybabystyle.graphql.GetProductQuery
import com.mmaquera.happybabystyle.graphql.GetCategoriesQuery
import com.mmaquera.happybabystyle.graphql.GetProductsByCategoryQuery
import com.mmaquera.happybabystyle.graphql.fragment.ProductBasicInfo
import com.mmaquera.happybabystyle.graphql.fragment.CategoryBasicInfo

/**
 * Mapper dedicado al mapeo de respuestas GraphQL a modelos del dominio
 * Responsabilidad única: Transformación de respuestas GraphQL
 */
/*
class ResponseMapper(
    private val productMapper: ProductMapper
) {
    
    /**
     * Mapea respuesta de productos a PaginatedResult
     */
    fun mapProductsResponse(response: GetProductsQuery.Data?): PaginatedResult<Product>? {
        val productsData = response?.products ?: return null
        
        val products = productsData.products?.mapNotNull { productData ->
            productData?.productBasicInfo?.let { productMapper.mapFromProductBasicInfo(it) }
        } ?: emptyList()
        
        return PaginatedResult(
            items = products,
            total = productsData.total,
            hasMore = productsData.hasMore
        )
    }
    
    /**
     * Mapea respuesta de producto individual
     */
    fun mapProductResponse(response: GetProductQuery.Data?): Product? {
        val productData = response?.product?.productBasicInfo
        return productData?.let { productMapper.mapFromProductBasicInfo(it) }
    }
    
    /**
     * Mapea respuesta de productos por categoría
     */
    fun mapProductsByCategoryResponse(response: GetProductsByCategoryQuery.Data?): PaginatedResult<Product>? {
        val productsData = response?.productsByCategory ?: return null
        
        val products = productsData.products?.mapNotNull { productData ->
            productData?.productBasicInfo?.let { productMapper.mapFromProductBasicInfo(it) }
        } ?: emptyList()
        
        return PaginatedResult(
            items = products,
            total = productsData.total,
            hasMore = productsData.hasMore
        )
    }
    
    /**
     * Mapea respuesta de categorías
     */
    fun mapCategoriesResponse(response: GetCategoriesQuery.Data?): List<Category> {
        val categoriesData = response?.categories ?: return emptyList()
        
        return categoriesData.mapNotNull { categoryData ->
            categoryData?.categoryBasicInfo?.let { productMapper.mapFromCategoryBasicInfo(it) }
        }
    }
    
    /**
     * Mapea lista de ProductBasicInfo a lista de Product
     */
    fun mapProductBasicInfoList(productInfoList: List<ProductBasicInfo?>): List<Product> {
        return productInfoList.mapNotNull { productInfo ->
            productInfo?.let { productMapper.mapFromProductBasicInfo(it) }
        }
    }
    
    /**
     * Mapea lista de CategoryBasicInfo a lista de Category
     */
    fun mapCategoryBasicInfoList(categoryInfoList: List<CategoryBasicInfo?>): List<Category> {
        return categoryInfoList.mapNotNull { categoryInfo ->
            categoryInfo?.let { productMapper.mapFromCategoryBasicInfo(it) }
        }
    }
}
*/