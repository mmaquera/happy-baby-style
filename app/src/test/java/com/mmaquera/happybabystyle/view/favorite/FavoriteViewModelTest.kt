package com.mmaquera.happybabystyle.view.favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para FavoriteViewModel
 * Siguiendo mejores prácticas de testing y principios SOLID
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {
    
    private lateinit var viewModel: FavoriteViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = FavoriteViewModel()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `estado inicial debe tener 4 productos favoritos`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        
        assertEquals(4, estadoInicial.favorites.size)
        assertFalse(estadoInicial.isLoading)
        assertEquals(null, estadoInicial.error)
        assertEquals("", estadoInicial.searchQuery)
    }
    
    @Test
    fun `productos favoritos deben tener datos correctos`() = runTest {
        val estado = viewModel.uiState.first()
        val primerProducto = estado.favorites.first()
        
        assertEquals("1", primerProducto.id)
        assertEquals("Cozy Cloud Onesie", primerProducto.name)
        assertEquals("ic_product_placeholder_1", primerProducto.imageRes)
        assertEquals("$24.99", primerProducto.price)
        assertTrue(primerProducto.isFavorite)
    }
    
    @Test
    fun `toggleFavorite debe cambiar estado de favorito`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val primerProducto = estadoInicial.favorites.first()
        
        // Inicialmente debe ser favorito
        assertTrue(primerProducto.isFavorite)
        
        // Alternar favorito
        viewModel.toggleFavorite(primerProducto.id)
        
        val estadoActualizado = viewModel.uiState.first()
        val productoActualizado = estadoActualizado.favorites.first { it.id == primerProducto.id }
        
        // Ahora debe no ser favorito
        assertFalse(productoActualizado.isFavorite)
    }
    
    @Test
    fun `toggleFavorite debe afectar solo al producto especificado`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val primerProducto = estadoInicial.favorites.first()
        val segundoProducto = estadoInicial.favorites[1]
        
        // Alternar primer producto
        viewModel.toggleFavorite(primerProducto.id)
        
        val estadoActualizado = viewModel.uiState.first()
        val primerProductoActualizado = estadoActualizado.favorites.first { it.id == primerProducto.id }
        val segundoProductoActualizado = estadoActualizado.favorites.first { it.id == segundoProducto.id }
        
        // Primer producto debe estar alternado
        assertFalse(primerProductoActualizado.isFavorite)
        
        // Segundo producto debe permanecer sin cambios
        assertEquals(segundoProducto.isFavorite, segundoProductoActualizado.isFavorite)
    }
    
    @Test
    fun `removeFromFavorites debe eliminar producto especificado`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val cantidadInicial = estadoInicial.favorites.size
        val primerProducto = estadoInicial.favorites.first()
        
        // Eliminar primer producto
        viewModel.removeFromFavorites(primerProducto.id)
        
        val estadoActualizado = viewModel.uiState.first()
        
        // Debe tener un producto menos
        assertEquals(cantidadInicial - 1, estadoActualizado.favorites.size)
        
        // Producto eliminado no debe estar en la lista
        assertTrue(estadoActualizado.favorites.none { it.id == primerProducto.id })
    }
    
    @Test
    fun `removeFromFavorites no debe afectar otros productos`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val primerProducto = estadoInicial.favorites.first()
        val segundoProducto = estadoInicial.favorites[1]
        
        // Eliminar primer producto
        viewModel.removeFromFavorites(primerProducto.id)
        
        val estadoActualizado = viewModel.uiState.first()
        
        // Segundo producto debe seguir en la lista
        assertTrue(estadoActualizado.favorites.any { it.id == segundoProducto.id })
        
        // Segundo producto debe tener los mismos datos
        val segundoProductoActualizado = estadoActualizado.favorites.first { it.id == segundoProducto.id }
        assertEquals(segundoProducto.name, segundoProductoActualizado.name)
        assertEquals(segundoProducto.imageRes, segundoProductoActualizado.imageRes)
        assertEquals(segundoProducto.price, segundoProductoActualizado.price)
        assertEquals(segundoProducto.isFavorite, segundoProductoActualizado.isFavorite)
    }
    
    @Test
    fun `searchFavorites debe filtrar productos por nombre`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        assertEquals(4, estadoInicial.favorites.size)
        
        // Buscar "Cozy"
        viewModel.searchFavorites("Cozy")
        
        val estadoFiltrado = viewModel.uiState.first()
        assertEquals(1, estadoFiltrado.favorites.size)
        assertEquals("Cozy Cloud Onesie", estadoFiltrado.favorites.first().name)
    }
    
    @Test
    fun `searchFavorites debe ser case insensitive`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        assertEquals(4, estadoInicial.favorites.size)
        
        // Buscar "COZY" (mayúsculas)
        viewModel.searchFavorites("COZY")
        
        val estadoFiltrado = viewModel.uiState.first()
        assertEquals(1, estadoFiltrado.favorites.size)
        assertEquals("Cozy Cloud Onesie", estadoFiltrado.favorites.first().name)
    }
    
    @Test
    fun `searchFavorites con query vacío debe restaurar lista completa`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        assertEquals(4, estadoInicial.favorites.size)
        
        // Buscar algo específico
        viewModel.searchFavorites("Cozy")
        assertEquals(1, viewModel.uiState.first().favorites.size)
        
        // Buscar con query vacío
        viewModel.searchFavorites("")
        
        val estadoRestaurado = viewModel.uiState.first()
        assertEquals(4, estadoRestaurado.favorites.size)
    }
    
    @Test
    fun `sortFavoritesByName debe ordenar productos alfabéticamente`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val nombresOriginales = estadoInicial.favorites.map { it.name }
        
        // Ordenar por nombre
        viewModel.sortFavoritesByName()
        
        val estadoOrdenado = viewModel.uiState.first()
        val nombresOrdenados = estadoOrdenado.favorites.map { it.name }
        
        // Verificar que esté ordenado alfabéticamente
        assertEquals(nombresOriginales.sorted(), nombresOrdenados)
    }
    
    @Test
    fun `sortFavoritesByPrice debe ordenar productos por precio`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val preciosOriginales = estadoInicial.favorites.map { it.price }
        
        // Ordenar por precio
        viewModel.sortFavoritesByPrice()
        
        val estadoOrdenado = viewModel.uiState.first()
        val preciosOrdenados = estadoOrdenado.favorites.map { it.price }
        
        // Verificar que esté ordenado por precio (de menor a mayor)
        val preciosNumericos = preciosOrdenados.map { it.replace("$", "").toDouble() }
        assertEquals(preciosNumericos.sorted(), preciosNumericos)
    }
    
    @Test
    fun `removeFromFavorites con id inexistente no debe cambiar estado`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val cantidadInicial = estadoInicial.favorites.size
        
        // Intentar eliminar producto inexistente
        viewModel.removeFromFavorites("id-inexistente")
        
        val estadoActualizado = viewModel.uiState.first()
        
        // Debe tener la misma cantidad de productos
        assertEquals(cantidadInicial, estadoActualizado.favorites.size)
        
        // Todos los productos deben ser los mismos
        assertEquals(estadoInicial.favorites, estadoActualizado.favorites)
    }
    
    @Test
    fun `toggleFavorite con id inexistente no debe cambiar estado`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        
        // Intentar alternar producto inexistente
        viewModel.toggleFavorite("id-inexistente")
        
        val estadoActualizado = viewModel.uiState.first()
        
        // Estado debe permanecer igual
        assertEquals(estadoInicial.favorites, estadoActualizado.favorites)
    }
    
    @Test
    fun `todos los productos deben tener ids únicos`() = runTest {
        val estado = viewModel.uiState.first()
        val ids = estado.favorites.map { it.id }
        
        // Todos los IDs deben ser únicos
        assertEquals(ids.size, ids.toSet().size)
    }
    
    @Test
    fun `todos los productos deben tener recursos de imagen válidos`() = runTest {
        val estado = viewModel.uiState.first()
        
        estado.favorites.forEach { producto ->
            // Todos los productos deben tener recurso de imagen no vacío
            assertTrue(producto.imageRes.isNotEmpty())
            
            // Recurso de imagen debe empezar con prefijo esperado
            assertTrue(producto.imageRes.startsWith("ic_product_placeholder_"))
        }
    }
    
    @Test
    fun `todos los productos deben tener precios válidos`() = runTest {
        val estado = viewModel.uiState.first()
        
        estado.favorites.forEach { producto ->
            // Todos los productos deben tener precio no vacío
            assertTrue(producto.price.isNotEmpty())
            
            // Precio debe empezar con signo de dólar
            assertTrue(producto.price.startsWith("$"))
        }
    }
    
    @Test
    fun `estado debe ser inmutable después de operaciones`() = runTest {
        val estadoInicial = viewModel.uiState.first()
        val productosIniciales = estadoInicial.favorites.toList()
        
        // Realizar operación
        viewModel.toggleFavorite(productosIniciales.first().id)
        
        val estadoActualizado = viewModel.uiState.first()
        
        // Los productos originales no deben haber cambiado
        assertEquals(true, productosIniciales.first().isFavorite)
        
        // Solo el nuevo estado debe reflejar el cambio
        assertFalse(estadoActualizado.favorites.first { it.id == productosIniciales.first().id }.isFavorite)
    }
} 