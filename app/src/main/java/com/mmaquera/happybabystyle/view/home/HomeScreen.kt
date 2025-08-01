package com.mmaquera.happybabystyle.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.Background
import com.mmaquera.happybabystyle.ui.theme.CategoryTabBorder
import com.mmaquera.happybabystyle.ui.theme.CategoryTabSelected
import com.mmaquera.happybabystyle.ui.theme.CategoryTabUnselected
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme
import com.mmaquera.happybabystyle.ui.theme.IconPrimary
import com.mmaquera.happybabystyle.ui.theme.IconSecondary
import com.mmaquera.happybabystyle.ui.theme.NavigationBackground
import com.mmaquera.happybabystyle.ui.theme.NavigationBorder
import com.mmaquera.happybabystyle.ui.theme.NavigationSelected
import com.mmaquera.happybabystyle.ui.theme.NavigationUnselected
import com.mmaquera.happybabystyle.ui.theme.PriceText
import com.mmaquera.happybabystyle.ui.theme.PrimaryText
import com.mmaquera.happybabystyle.ui.theme.ProductCardBackground
import com.mmaquera.happybabystyle.ui.theme.SearchBarBackground
import com.mmaquera.happybabystyle.ui.theme.SearchBarText
import com.mmaquera.happybabystyle.ui.theme.SecondaryText

@Composable
fun HomeScreen() {
    var selectedCategory by remember { mutableStateOf("Bodysuits") }
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredProducts = remember(selectedCategory, searchQuery) {
        getProducts().filter { product ->
            val matchesCategory = selectedCategory == "All" || product.type == selectedCategory.lowercase()
            val matchesSearch = searchQuery.isEmpty() || 
                product.name.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        HeaderSection()
        
        // Search Bar
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it }
        )
        
        // Category Tabs
        CategoryTabs(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        
        // Product Grid
        ProductGrid(products = filteredProducts)
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Bottom Navigation
        BottomNavigation()
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Happy Baby Style",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { /* Profile action */ }
                .semantics { contentDescription = "Profile button" },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile",
                tint = IconPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SearchBarBackground)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search icon",
                        tint = SearchBarText,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = "Search for baby clothes",
                            fontSize = 16.sp,
                            color = SearchBarText
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = PrimaryText
                    ),
                    singleLine = true
                )
            }
        }
    }
}

@Composable
private fun CategoryTabs(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("All", "Bodysuits", "Pajamas", "Outfits", "Socks", "Hats")
    val categoryIcons = listOf(
        null, // All doesn't have an icon
        R.drawable.ic_bodysuit,
        R.drawable.ic_pajamas,
        R.drawable.ic_outfit,
        R.drawable.ic_socks,
        R.drawable.ic_hat
    )
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            val isSelected = category == selectedCategory
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onCategorySelected(category) }
                    .padding(vertical = 10.dp)
                    .semantics { contentDescription = "Category $category" }
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (isSelected) {
                                Modifier.border(
                                    width = 3.dp,
                                    color = CategoryTabBorder,
                                    shape = RoundedCornerShape(4.dp)
                                )
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (categoryIcons[index] != null) {
                        Icon(
                            painter = painterResource(id = categoryIcons[index]!!),
                            contentDescription = category,
                            tint = if (isSelected) CategoryTabSelected else CategoryTabUnselected,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        // Show a simple dot for "All" category
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (isSelected) CategoryTabSelected else CategoryTabUnselected,
                                    shape = CircleShape
                                )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = category,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) CategoryTabSelected else CategoryTabUnselected
                )
            }
        }
    }
}

@Composable
private fun ProductGrid(products: List<Product>) {
    if (products.isEmpty()) {
        // Show empty state
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_empty_state),
                    contentDescription = "No products found",
                    tint = SecondaryText,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No products found",
                    style = MaterialTheme.typography.titleMedium,
                    color = SecondaryText
                )
                Text(
                    text = "Try adjusting your search or category filter",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductCard(product = product)
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Product click action */ }
            .semantics { contentDescription = "Product ${product.name} for ${product.price}" }
    ) {
        // Product Image Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(231.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ProductCardBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = when (product.type) {
                        "bodysuit" -> R.drawable.ic_bodysuit
                        "pajamas" -> R.drawable.ic_pajamas
                        "outfit" -> R.drawable.ic_outfit
                        "socks" -> R.drawable.ic_socks
                        "hat" -> R.drawable.ic_hat
                        else -> R.drawable.ic_bodysuit
                    }
                ),
                contentDescription = "${product.type} icon",
                tint = IconSecondary,
                modifier = Modifier.size(48.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = product.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryText
        )
        
        Text(
            text = product.price,
            fontSize = 14.sp,
            color = PriceText
        )
    }
}

@Composable
private fun BottomNavigation() {
    val navItems = listOf(
        NavItem.VectorNavItem("Home", Icons.Default.Home, true),
        NavItem.DrawableNavItem("Categories", R.drawable.ic_categories, false),
        NavItem.VectorNavItem("Favorites", Icons.Default.Favorite, false),
        NavItem.VectorNavItem("Cart", Icons.Default.ShoppingCart, false),
        NavItem.VectorNavItem("Profile", Icons.Default.Person, false)
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavigationBackground)
            .border(
                width = 1.dp,
                color = NavigationBorder,
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            navItems.forEach { navItem ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .semantics { contentDescription = "${navItem.title} navigation item" }
                ) {
                    when (navItem) {
                        is NavItem.VectorNavItem -> {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = navItem.title,
                                tint = if (navItem.isSelected) NavigationSelected else NavigationUnselected,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        is NavItem.DrawableNavItem -> {
                            Icon(
                                painter = painterResource(id = navItem.iconRes),
                                contentDescription = navItem.title,
                                tint = if (navItem.isSelected) NavigationSelected else NavigationUnselected,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = navItem.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (navItem.isSelected) NavigationSelected else NavigationUnselected
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

// Helper function to get products
private fun getProducts(): List<Product> {
    return listOf(
        Product("Cozy Cloud Bodysuit", "$25", "bodysuit"),
        Product("Dreamy Stars Pajamas", "$30", "pajamas"),
        Product("Little Explorer Outfit", "$45", "outfit"),
        Product("Tiny Toes Socks", "$10", "socks"),
        Product("Sunshine Hat", "$15", "hat"),
        Product("Soft Cotton Bodysuit", "$22", "bodysuit"),
        Product("Night Owl Pajamas", "$28", "pajamas"),
        Product("Adventure Outfit", "$42", "outfit"),
        Product("Warm Winter Socks", "$12", "socks"),
        Product("Rainbow Hat", "$18", "hat")
    )
}

// Data classes
data class Product(
    val name: String,
    val price: String,
    val type: String
)

sealed class NavItem(
    open val title: String,
    open val isSelected: Boolean
) {
    data class VectorNavItem(
        override val title: String,
        val icon: androidx.compose.ui.graphics.vector.ImageVector,
        override val isSelected: Boolean
    ) : NavItem(title, isSelected)
    
    data class DrawableNavItem(
        override val title: String,
        val iconRes: Int,
        override val isSelected: Boolean
    ) : NavItem(title, isSelected)
}

// Preview Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HomeScreenPreview() {
    HappyBabyStyleTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HeaderSectionPreview() {
    HappyBabyStyleTheme {
        HeaderSection()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SearchBarPreview() {
    HappyBabyStyleTheme {
        SearchBar(
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CategoryTabsPreview() {
    HappyBabyStyleTheme {
        CategoryTabs(
            selectedCategory = "Bodysuits",
            onCategorySelected = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ProductCardPreview() {
    HappyBabyStyleTheme {
        ProductCard(
            product = Product(
                name = "Cozy Cloud Bodysuit",
                price = "$25",
                type = "bodysuit"
            )
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun BottomNavigationPreview() {
    HappyBabyStyleTheme {
        BottomNavigation()
    }
}