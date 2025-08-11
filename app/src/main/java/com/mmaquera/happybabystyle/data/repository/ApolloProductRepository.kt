package com.mmaquera.happybabystyle.data.repository

/**
 * Repositorio de productos usando Apollo GraphQL
 * Implementa Clean Architecture con código generado type-safe
 * 
 * Responsabilidad única: Coordinación de operaciones de productos
 * - Delega construcción de queries al QueryBuilder
 * - Delega mapeo de respuestas al ResponseMapper
 * - Delega filtrado al FilterMapper
 * - Delega manejo de errores al ErrorMapper
 */
/*
class ApolloProductRepository(
    private val apolloClient: ApolloGraphQLClient,
    private val productMapper: ProductMapper,
    private val filterMapper: FilterMapper,
    private val errorMapper: ErrorMapper,
    private val queryBuilder: QueryBuilder,
    private val responseMapper: ResponseMapper
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
            // Construir query usando el builder
            val query = queryBuilder.buildGetProductsQuery(limit, offset)
            
            // Ejecutar query
            val response = apolloClient.getClient().query(query).execute()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(Result.failure(Exception(errorMessage)))
            } else {
                // Mapear respuesta usando el response mapper
                val paginatedResult = responseMapper.mapProductsResponse(response.data)
                
                if (paginatedResult != null) {
                    // Aplicar filtros usando el filter mapper
                    val filteredProducts = filterMapper.applyFilters(
                        paginatedResult.items, 
                        categoryId, 
                        searchQuery, 
                        minPrice, 
                        maxPrice, 
                        inStock
                    )
                    
                    val finalResult = paginatedResult.copy(items = filteredProducts)
                    emit(Result.success(finalResult))
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
            // Construir query usando el builder
            val query = queryBuilder.buildGetProductQuery(productId)
            
            // Ejecutar query
            val response = apolloClient.getClient().query(query).execute()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(Result.failure(Exception(errorMessage)))
            } else {
                // Mapear respuesta usando el response mapper
                val product = responseMapper.mapProductResponse(response.data)
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
            // Construir query usando el builder
            val query = queryBuilder.buildGetProductsByCategoryQuery(categoryId, limit, offset)
            
            // Ejecutar query
            val response = apolloClient.getClient().query(query).execute()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(Result.failure(Exception(errorMessage)))
            } else {
                // Mapear respuesta usando el response mapper
                val paginatedResult = responseMapper.mapProductsByCategoryResponse(response.data)
                
                if (paginatedResult != null) {
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
            // Construir query usando el builder
            val query = queryBuilder.buildGetCategoriesQuery()
            
            // Ejecutar query
            val response = apolloClient.getClient().query(query).execute()
            
            // Verificar errores usando el error mapper
            if (errorMapper.hasErrors(response)) {
                val errorMessage = errorMapper.getErrorMessage(response)
                emit(Result.failure(Exception(errorMessage)))
            } else {
                // Mapear respuesta usando el response mapper
                val categories = responseMapper.mapCategoriesResponse(response.data)
                emit(Result.success(categories))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        emit(Result.failure(exception))
    }
} */