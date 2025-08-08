package com.mmaquera.happybabystyle.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Checkroom
import com.mmaquera.happybabystyle.ui.theme.CustomIcons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Main CategoryScreen composable
 * Following Single Responsibility Principle - only handles UI composition
 */
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = viewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    
    Scaffold(
        topBar = { CategoryTopBar() },
        bottomBar = { CategoryBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            CategoryGrid(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = { viewModel.selectCategory(it) }
            )
        }
    }
}

/**
 * Top bar for the category screen
 * Following Single Responsibility Principle - only handles top bar UI
 */
@Composable
private fun CategoryTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title
        Text(
            text = "Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // Back button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable { /* Handle back navigation */ }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF171212),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Category grid component
 * Following Single Responsibility Principle - only handles grid layout
 */
@Composable
private fun CategoryGrid(
    categories: List<CategoryItem>,
    selectedCategory: CategoryItem?,
    onCategoryClick: (CategoryItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                isSelected = category.id == selectedCategory?.id,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

/**
 * Individual category card component
 * Following Single Responsibility Principle - only handles card UI
 */
@Composable
private fun CategoryCard(
    category: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF8A6166) else Color(0xFFE5DBDB),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(17.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category icon
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = if (isSelected) Color(0xFF8A6166) else Color(0xFF171212),
                modifier = Modifier.size(24.dp)
            )
            
            // Category name
            Text(
                text = category.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFF8A6166) else Color(0xFF171212)
            )
        }
    }
}

/**
 * Bottom navigation bar for the category screen
 * Following Single Responsibility Principle - only handles bottom navigation
 */
@Composable
private fun CategoryBottomBar() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFF5F0F2),
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
            ),
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = false,
                onClick = { /* Navigate to Home */ }
            )
            
            BottomNavItem(
                icon = Icons.Default.Style,
                label = "Categories",
                isSelected = true,
                onClick = { /* Already on Categories */ }
            )
            
            BottomNavItem(
                icon = Icons.Default.Favorite,
                label = "Favorites",
                isSelected = false,
                onClick = { /* Navigate to Favorites */ }
            )
            
            BottomNavItem(
                icon = Icons.Default.ShoppingCart,
                label = "Cart",
                isSelected = false,
                onClick = { /* Navigate to Cart */ }
            )
            
            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = { /* Navigate to Profile */ }
            )
        }
    }
}

/**
 * Bottom navigation item component
 * Following Single Responsibility Principle - only handles navigation item UI
 */
@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFF171212) else Color(0xFF8A6166),
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) Color(0xFF171212) else Color(0xFF8A6166)
        )
    }
}

/**
 * Preview parameter provider for CategoryScreen
 * Following Single Responsibility Principle - only provides preview data
 */
class CategoryScreenPreviewParameterProvider : PreviewParameterProvider<CategoryViewModel> {
    override val values = sequenceOf(
        CategoryViewModel(CategoryRepositoryImpl())
    )
}

/**
 * Preview for CategoryScreen with default state
 */
@Preview(
    name = "CategoryScreen - Default",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}

/**
 * Preview for CategoryScreen with selected category
 */
@Preview(
    name = "CategoryScreen - With Selection",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryScreenWithSelectionPreview() {
    // Use remember to create the ViewModel for preview
    val viewModel = remember { CategoryViewModel(CategoryRepositoryImpl()) }
    // Simulate selection
    LaunchedEffect(Unit) {
        viewModel.selectCategory(CategoryItem("1", "Bodysuits", CustomIcons.Bodysuit))
    }
    CategoryScreen(viewModel = viewModel)
}

/**
 * Preview for CategoryTopBar
 */
@Preview(
    name = "CategoryTopBar",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryTopBarPreview() {
    CategoryTopBar()
}

/**
 * Preview for CategoryCard - Default state
 */
@Preview(
    name = "CategoryCard - Default",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryCardDefaultPreview() {
    CategoryCard(
        category = CategoryItem("1", "Bodysuits", CustomIcons.Bodysuit),
        isSelected = false,
        onClick = {}
    )
}

/**
 * Preview for CategoryCard - Selected state
 */
@Preview(
    name = "CategoryCard - Selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryCardSelectedPreview() {
    CategoryCard(
        category = CategoryItem("1", "Bodysuits", CustomIcons.Bodysuit),
        isSelected = true,
        onClick = {}
    )
}

/**
 * Preview for CategoryGrid
 */
@Preview(
    name = "CategoryGrid",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryGridPreview() {
    val categories = listOf(
        CategoryItem("1", "Bodysuits", CustomIcons.Bodysuit),
        CategoryItem("2", "Tops", Icons.Default.Style),
        CategoryItem("3", "Pajamas", CustomIcons.Pajamas),
        CategoryItem("4", "Bottoms", Icons.Default.Checkroom)
    )
    
    CategoryGrid(
        categories = categories,
        selectedCategory = categories.first(),
        onCategoryClick = {}
    )
}

/**
 * Preview for CategoryBottomBar
 */
@Preview(
    name = "CategoryBottomBar",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryBottomBarPreview() {
    CategoryBottomBar()
}

/**
 * Preview for BottomNavItem - Default state
 */
@Preview(
    name = "BottomNavItem - Default",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun BottomNavItemDefaultPreview() {
    BottomNavItem(
        icon = Icons.Default.Home,
        label = "Home",
        isSelected = false,
        onClick = {}
    )
}

/**
 * Preview for BottomNavItem - Selected state
 */
@Preview(
    name = "BottomNavItem - Selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun BottomNavItemSelectedPreview() {
    BottomNavItem(
        icon = Icons.Default.Style,
        label = "Categories",
        isSelected = true,
        onClick = {}
    )
}

/**
 * Preview for CategoryScreen in dark theme
 */
@Preview(
    name = "CategoryScreen - Dark Theme",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
fun CategoryScreenDarkPreview() {
    CategoryScreen()
}

/**
 * Preview for CategoryScreen with long category names
 */
@Preview(
    name = "CategoryScreen - Long Names",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CategoryScreenLongNamesPreview() {
    val viewModel = remember { 
        CategoryViewModel(object : CategoryRepository {
            override fun getCategories(): List<CategoryItem> {
                return listOf(
                    CategoryItem("1", "Very Long Category Name", CustomIcons.Bodysuit),
                    CategoryItem("2", "Another Very Long Category", Icons.Default.Style),
                    CategoryItem("3", "Super Long Category Name Here", CustomIcons.Pajamas),
                    CategoryItem("4", "Extremely Long Category Name", Icons.Default.Checkroom)
                )
            }
        })
    }
    CategoryScreen(viewModel = viewModel)
}