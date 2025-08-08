package com.mmaquera.happybabystyle.domain.usecase

import com.mmaquera.happybabystyle.domain.model.Category
import com.mmaquera.happybabystyle.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use Case para obtener categorías de productos
 * Implementa lógica de negocio para el manejo de categorías
 * 
 * Principios aplicados:
 * - Single Responsibility: Solo maneja operaciones de categorías
 * - Business Logic: Aplica reglas de negocio como filtrado y ordenamiento
 * - Clean Architecture: Sin dependencias externas
 */
class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {
    
    /**
     * Obtiene todas las categorías activas ordenadas
     * 
     * @return Flow con lista de categorías ordenadas por sortOrder
     */
    suspend operator fun invoke(): Flow<Result<List<Category>>> {
        return productRepository.getCategories()
            .map { result ->
                result.map { categories ->
                    // Business logic: filtrar solo categorías activas y ordenar
                    categories
                        .filter { it.isAvailable() }
                        .sortedBy { it.sortOrder }
                }
            }
    }
    
    /**
     * Obtiene todas las categorías (incluyendo inactivas) para administración
     * 
     * @return Flow con lista completa de categorías
     */
    suspend fun getAllCategories(): Flow<Result<List<Category>>> {
        return productRepository.getCategories()
            .map { result ->
                result.map { categories ->
                    // Ordenar por sortOrder sin filtrar por estado
                    categories.sortedBy { it.sortOrder }
                }
            }
    }
    
    /**
     * Busca categorías por nombre
     * 
     * @param query Texto a buscar en el nombre de la categoría
     * @return Flow con categorías que coinciden con la búsqueda
     */
    suspend fun searchCategories(query: String): Flow<Result<List<Category>>> {
        require(query.isNotBlank()) { "La consulta de búsqueda no puede estar vacía" }
        require(query.length >= 2) { "La consulta debe tener al menos 2 caracteres" }
        
        val normalizedQuery = query.trim().lowercase()
        
        return invoke().map { result ->
            result.map { categories ->
                categories.filter { category ->
                    category.name.lowercase().contains(normalizedQuery) ||
                    category.description.lowercase().contains(normalizedQuery) ||
                    category.slug.lowercase().contains(normalizedQuery)
                }
            }
        }
    }
    
    /**
     * Obtiene categorías populares basadas en algún criterio de negocio
     * Por ahora retorna las primeras 5 categorías activas
     * 
     * @param limit Número máximo de categorías a retornar
     * @return Flow con categorías populares
     */
    suspend fun getPopularCategories(limit: Int = 5): Flow<Result<List<Category>>> {
        require(limit in 1..20) { "El límite debe estar entre 1 y 20" }
        
        return invoke().map { result ->
            result.map { categories ->
                // Business logic: tomar las primeras categorías (las más importantes por sortOrder)
                categories.take(limit)
            }
        }
    }
    
    /**
     * Encuentra una categoría por su slug
     * 
     * @param slug Slug único de la categoría
     * @return Flow con la categoría encontrada o null
     */
    suspend fun getCategoryBySlug(slug: String): Flow<Result<Category?>> {
        require(slug.isNotBlank()) { "El slug no puede estar vacío" }
        
        val normalizedSlug = slug.trim().lowercase()
        
        return invoke().map { result ->
            result.map { categories ->
                categories.find { it.slug.lowercase() == normalizedSlug }
            }
        }
    }
}