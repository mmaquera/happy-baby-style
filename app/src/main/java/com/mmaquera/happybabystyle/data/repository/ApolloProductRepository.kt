package com.mmaquera.happybabystyle.data.repository

import com.mmaquera.happybabystyle.data.network.ApolloGraphQLClient
import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.domain.model.PaginatedResult
import com.mmaquera.happybabystyle.domain.repository.ProductRepository
import com.mmaquera.happybabystyle.graphql.GetProductsQuery
import com.mmaquera.happybabystyle.graphql.GetProductQuery
import com.mmaquera.happybabystyle.graphql.GetCategoriesQuery
import com.mmaquera.happybabystyle.graphql.GetProductsByCategoryQuery
import com.mmaquera.happybabystyle.graphql.fragment.ProductInfo
import com.mmaquera.happybabystyle.graphql.fragment.CategoryInfo
import com.mmaquera.happybabystyle.graphql.type.PaginationInput
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
/**
 * Repositorio de productos usando Apollo GraphQL
 * Implementa Clean Architecture con código generado type-safe
 * 
 * Características:
 * - Código generado type-safe por Apollo
 * - Cache automático
 * - Optimistic updates
 * - Error handling robusto
 */
class ApolloProductRepository(
    private val apolloClient: ApolloGraphQLClient
) : ProductRepository {
    
    override suspend fun getProducts(
        categoryId: String?,
        searchQuery: String?,
        minPrice: Double?,
        maxPrice: Double?,
        inStock: Boolean?,
        limit: Int,
        offset: Int
    ): Flow<Result<PaginatedResult<Product>>> = flow {
        try {
            val paginationInput = PaginationInput(
                limit = Optional.present(limit),
                offset = Optional.present(offset)
            )
            
            val query = GetProductsQuery(
                pagination = Optional.present(paginationInput)
            )
            
            val response = apolloClient.getClient().query(query).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error desconocido"
                emit(Result.failure(Exception(errorMessage)))
            } else {
                val productsData = response.data?.products
                if (productsData != null) {
                    val products = productsData.products?.mapNotNull { productData ->
                        productData?.productInfo?.let { mapToProduct(it) }
                    } ?: emptyList()
                    
                    // Aplicar filtros manualmente por ahora
                    val filteredProducts = applyFilters(products, categoryId, searchQuery, minPrice, maxPrice, inStock)
                    
                    val paginatedResult = PaginatedResult(
                        items = filteredProducts,
                        total = productsData.total,
                        hasMore = productsData.hasMore
                    )
                    
                    emit(Result.success(paginatedResult))
                } else {
                    emit(Result.failure(Exception("No se encontraron datos de productos")))
                }
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
    
    override suspend fun getProductById(productId: String): Flow<Result<Product?>> = flow {
        try {
            val query = GetProductQuery(id = productId)
            
            val response = apolloClient.getClient().query(query).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error desconocido"
                emit(Result.failure(Exception(errorMessage)))
            } else {
                val productData = response.data?.product?.productInfo
                val product = productData?.let { mapToProduct(it) }
                emit(Result.success(product))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
    
    override suspend fun getProductsByCategory(
        categoryId: String,
        limit: Int,
        offset: Int
    ): Flow<Result<PaginatedResult<Product>>> = flow {
        try {
            val paginationInput = PaginationInput(
                limit = Optional.present(limit),
                offset = Optional.present(offset)
            )
            
            val query = GetProductsByCategoryQuery(
                categoryId = categoryId,
                pagination = Optional.present(paginationInput)
            )
            
            val response = apolloClient.getClient().query(query).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error desconocido"
                emit(Result.failure(Exception(errorMessage)))
            } else {
                val productsData = response.data?.productsByCategory
                if (productsData != null) {
                    val products = productsData.products?.mapNotNull { productData ->
                        productData?.productInfo?.let { mapToProduct(it) }
                    } ?: emptyList()
                    
                    val paginatedResult = PaginatedResult(
                        items = products,
                        total = productsData.total,
                        hasMore = productsData.hasMore
                    )
                    
                    emit(Result.success(paginatedResult))
                } else {
                    emit(Result.failure(Exception("No se encontraron productos para la categoría")))
                }
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
    
    override suspend fun getCategories(): Flow<Result<List<Category>>> = flow {
        try {
            val query = GetCategoriesQuery()
            
            val response = apolloClient.getClient().query(query).execute()
            
            if (response.hasErrors()) {
                val errorMessage = response.errors?.firstOrNull()?.message ?: "Error desconocido"
                emit(Result.failure(Exception(errorMessage)))
            } else {
                val categoriesData = response.data?.categories
                val categories = categoriesData?.mapNotNull { categoryData ->
                    categoryData?.categoryInfo?.let { mapToCategory(it) }
                } ?: emptyList()
                
                emit(Result.success(categories))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
    
    // ===== FUNCIONES DE MAPPING =====
    
    private fun mapToProduct(productInfo: ProductInfo): Product {
        return Product(
            id = productInfo.id,
            name = productInfo.name,
            description = productInfo.description ?: "",
            price = productInfo.price.toDouble(),
            salePrice = productInfo.salePrice?.toDouble(),
            sku = productInfo.sku,
            images = productInfo.images,
            tags = productInfo.tags,
            isActive = productInfo.isActive,
            stockQuantity = productInfo.stockQuantity,
            rating = productInfo.rating.toFloat(),
            reviewCount = productInfo.reviewCount,
            createdAt = productInfo.createdAt,
            updatedAt = productInfo.updatedAt,
            currentPrice = productInfo.currentPrice.toDouble(),
            hasDiscount = productInfo.hasDiscount,
            discountPercentage = productInfo.discountPercentage,
            totalStock = productInfo.totalStock,
            isInStock = productInfo.isInStock,
            category = productInfo.category?.let { mapToCategory(it) }
        )
    }
    
    private fun mapToCategory(categoryInfo: ProductInfo.Category): Category {
        return Category(
            id = categoryInfo.id,
            name = categoryInfo.name,
            description = "", // No disponible en ProductInfo.Category
            slug = categoryInfo.slug,
            imageUrl = categoryInfo.imageUrl,
            isActive = true, // Asumir activo por defecto
            sortOrder = 0, // No disponible
            createdAt = null, // No disponible
            updatedAt = null // No disponible
        )
    }
    
    private fun mapToCategory(categoryInfo: CategoryInfo): Category {
        return Category(
            id = categoryInfo.id,
            name = categoryInfo.name,
            description = categoryInfo.description ?: "",
            slug = categoryInfo.slug,
            imageUrl = categoryInfo.imageUrl,
            isActive = categoryInfo.isActive,
            sortOrder = categoryInfo.sortOrder,
            createdAt = categoryInfo.createdAt,
            updatedAt = categoryInfo.updatedAt
        )
    }
    
    /**
     * Aplica filtros manuales a la lista de productos
     * Implementación temporal hasta que se implementen filtros en el servidor
     */
    private fun applyFilters(
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
} 