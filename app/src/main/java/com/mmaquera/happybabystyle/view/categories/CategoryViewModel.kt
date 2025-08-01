package com.mmaquera.happybabystyle.view.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CardGiftcard
import com.mmaquera.happybabystyle.ui.theme.CustomIcons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class representing a category item
 * Following Single Responsibility Principle - only holds category data
 */
data class CategoryItem(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val isSelected: Boolean = false
)

/**
 * Interface for category repository operations
 * Following Interface Segregation Principle - only category-related operations
 */
interface CategoryRepository {
    fun getCategories(): List<CategoryItem>
}

/**
 * Repository implementation for categories
 * Following Single Responsibility Principle - only handles category data
 */
class CategoryRepositoryImpl : CategoryRepository {
    override fun getCategories(): List<CategoryItem> {
        return listOf(
            CategoryItem("1", "Bodysuits", CustomIcons.Bodysuit),
            CategoryItem("2", "Tops", Icons.Default.Style),
            CategoryItem("3", "Pajamas", CustomIcons.Pajamas),
            CategoryItem("4", "Bottoms", Icons.Default.Checkroom),
            CategoryItem("5", "Socks", CustomIcons.Socks),
            CategoryItem("6", "Hats", CustomIcons.Hat),
            CategoryItem("7", "Accessories", Icons.Default.Diamond),
            CategoryItem("8", "Shoes", Icons.Default.Sports),
            CategoryItem("9", "Swimwear", Icons.Default.Pool),
            CategoryItem("10", "Outerwear", Icons.Default.AcUnit),
            CategoryItem("11", "Gifts", Icons.Default.CardGiftcard),
            CategoryItem("12", "Sale", Icons.Default.LocalOffer)
        )
    }
}

/**
 * ViewModel for CategoryScreen
 * Following Single Responsibility Principle - only manages category UI state
 * Following Dependency Inversion Principle - depends on abstraction (CategoryRepository)
 */
class CategoryViewModel(
    private val repository: CategoryRepository = CategoryRepositoryImpl()
) : ViewModel() {
    
    private val _categories = MutableStateFlow<List<CategoryItem>>(emptyList())
    val categories: StateFlow<List<CategoryItem>> = _categories.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<CategoryItem?>(null)
    val selectedCategory: StateFlow<CategoryItem?> = _selectedCategory.asStateFlow()
    
    init {
        loadCategories()
    }
    
    /**
     * Loads categories from repository
     * Following Open/Closed Principle - can be extended without modification
     */
    private fun loadCategories() {
        _categories.value = repository.getCategories()
    }
    
    /**
     * Selects a category
     * Following Single Responsibility Principle - only handles selection logic
     */
    fun selectCategory(category: CategoryItem) {
        _selectedCategory.value = category
        // Update categories to reflect selection
        _categories.value = _categories.value.map { 
            it.copy(isSelected = it.id == category.id)
        }
    }
    
    /**
     * Clears category selection
     */
    fun clearSelection() {
        _selectedCategory.value = null
        _categories.value = _categories.value.map { 
            it.copy(isSelected = false)
        }
    }
} 