package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.Product
import com.mmaquera.happybabystyle.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use Case para obtener un producto específico por ID
 * Implementa validaciones y lógica de negocio para productos individuales
 * 
 * Principios aplicados:
 * - Single Responsibility: Solo maneja obtención de producto por ID
 * - Dependency Inversion: Depende de abstracción (ProductRepository)
 * - Input Validation: Valida parámetros de entrada
 */
class GetProductByIdUseCase(
    private val productRepository: ProductRepository
) {
    
    /**
     * Obtiene un producto por su ID
     * 
     * @param productId ID del producto a buscar
     * @return Flow con el producto encontrado o null si no existe
     * @throws IllegalArgumentException si el ID es inválido
     */
    suspend operator fun invoke(productId: String): Flow<Result<Product?>> {
        // Validaciones de business logic
        require(productId.isNotBlank()) { "El ID del producto no puede estar vacío" }
        require(productId.trim() == productId) { "El ID del producto no debe tener espacios" }
        
        val trimmedId = productId.trim()
        
        return productRepository.getProductById(trimmedId)
    }
    
    /**
     * Verifica si un producto existe por su ID
     * 
     * @param productId ID del producto a verificar
     * @return Flow con true si existe, false si no
     */
    suspend fun exists(productId: String): Flow<Result<Boolean>> {
        require(productId.isNotBlank()) { "El ID del producto no puede estar vacío" }
        
        return try {
            val result = invoke(productId)
            kotlinx.coroutines.flow.flow {
                result.collect { productResult ->
                    when {
                        productResult.isSuccess -> {
                            emit(Result.success(productResult.getOrNull() != null))
                        }
                        productResult.isFailure -> {
                            emit(Result.failure(productResult.exceptionOrNull()!!))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            kotlinx.coroutines.flow.flow {
                emit(Result.failure(e))
            }
        }
    }
}