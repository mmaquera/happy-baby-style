package com.mmaquera.happybabystyle.presentation.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.presentation.viewmodel.GraphQLProductViewModel
import com.mmaquera.happybabystyle.presentation.viewmodel.ProductsUiState
import com.mmaquera.happybabystyle.presentation.viewmodel.CategoriesUiState

/**
 * Pantalla de ejemplo que demuestra la integración con GraphQL
 * 
 * Características:
 * - Lista de productos obtenidos via GraphQL
 * - Filtros por categoría
 * - Búsqueda de productos
 * - Estados de carga y error
 * - Pull to refresh
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphQLProductScreen(
    modifier: Modifier = Modifier,
    viewModel: GraphQLProductViewModel = viewModel()
) {
    
    val productsState by viewModel.productsState.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    
    // Efecto para cargar datos iniciales
    LaunchedEffect(Unit) {
        viewModel.loadCategories()
        viewModel.loadProducts()
    }
    
    // Efecto para búsqueda
    LaunchedEffect(searchQuery) {
        viewModel.searchProducts(searchQuery)
    }
    
    // Efecto para filtro de categoría
    LaunchedEffect(selectedCategoryId) {
        viewModel.filterByCategory(selectedCategoryId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos GraphQL") },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        // Icono de refresh
                    }
                }
            )
        }
    ) { paddingValues ->
        
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            
            // Barra de búsqueda
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { viewModel.searchProducts(it) },
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Buscar productos...")
            }
            
            // Filtros de categoría
            if (categoriesState.categories.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategoryId == null,
                            onClick = { selectedCategoryId = null },
                            label = { Text("Todas") }
                        )
                    }
                    
                    items(categoriesState.categories) { category ->
                        FilterChip(
                            selected = selectedCategoryId == category.id,
                            onClick = { selectedCategoryId = category.id },
                            label = { Text(category.name) }
                        )
                    }
                }
            }
            
            // Contenido principal
            when {
                productsState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                
                productsState.error != null -> {
                    ErrorContent(
                        error = productsState.error!!,
                        onRetry = { viewModel.refresh() }
                    )
                }
                
                productsState.products.isEmpty() -> {
                    EmptyContent(
                        onRefresh = { viewModel.refresh() }
                    )
                }
                
                else -> {
                    ProductList(
                        products = productsState.products,
                        onProductClick = { productId ->
                            // Navegar a detalle del producto
                        },
                        onLoadMore = {
                            if (productsState.hasMore) {
                                viewModel.loadProducts(
                                    categoryId = selectedCategoryId,
                                    searchQuery = searchQuery.takeIf { it.isNotEmpty() },
                                    page = productsState.currentPage + 1
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = placeholder,
        singleLine = true
    )
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error al cargar productos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

@Composable
private fun EmptyContent(
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No se encontraron productos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Intenta ajustar los filtros o busca algo diferente",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRefresh) {
            Text("Actualizar")
        }
    }
}

@Composable
private fun ProductList(
    products: List<com.mmaquera.happybabystyle.domain.model.Product>,
    onProductClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
        
        item {
            if (products.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLoadMore,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cargar más productos")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCard(
    product: com.mmaquera.happybabystyle.domain.model.Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.currentPrice}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                if (product.hasDiscount) {
                    Text(
                        text = "-${product.discountPercentage}%",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating stars
                Text(
                    text = "★ ${product.rating}",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "(${product.reviewCount} reseñas)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 