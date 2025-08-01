package com.mmaquera.happybabystyle.view.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R

@Composable
fun DetailProductScreen(
    onBackClick: () -> Unit = {},
    viewModel: DetailProductViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        HeaderSection(onBackClick = onBackClick)
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image
            ProductImageSection()
            
            // Size Section
            SizeSection(
                sizes = uiState.product.sizes,
                selectedSize = uiState.product.selectedSize,
                onSizeSelected = viewModel::selectSize
            )
            
            // Description Section
            DescriptionSection(description = uiState.product.description)
            
            // Reviews Section
            ReviewsSection(
                rating = uiState.product.rating,
                reviewCount = uiState.product.reviewCount,
                reviewRatings = uiState.reviewRatings
            )
        }
        
        // Bottom Section
        BottomSection(
            onAddToCart = viewModel::addToCart
        )
    }
}

@Composable
private fun HeaderSection(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { onBackClick() }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
        }
        
        // Title
        Text(
            text = "Happy Baby Style",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        
        // Empty space for balance
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun ProductImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(585.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_product_placeholder),
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
private fun SizeSection(
    sizes: List<String>,
    selectedSize: String,
    onSizeSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Size",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sizes.forEach { size ->
                SizeChip(
                    size = size,
                    isSelected = size == selectedSize,
                    onClick = { onSizeSelected(size) }
                )
            }
        }
    }
}

@Composable
private fun SizeChip(
    size: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Color(0xFF171212)
                else Color(0xFFF5F0F0)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = size,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF171212)
        )
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Description",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Text(
            text = description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF171212),
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun ReviewsSection(
    rating: Float,
    reviewCount: Int,
    reviewRatings: List<ReviewRating>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Reviews",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF171212),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Rating Summary
            RatingSummary(rating = rating, reviewCount = reviewCount)
            
            // Rating Breakdown
            RatingBreakdown(reviewRatings = reviewRatings)
        }
    }
}

@Composable
private fun RatingSummary(rating: Float, reviewCount: Int) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = rating.toString(),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF171212),
            letterSpacing = (-1).sp
        )
        
        // Stars
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(5) { index ->
                val starIcon = when {
                    index < rating.toInt() -> R.drawable.ic_star_filled
                    index == rating.toInt() && rating % 1 > 0 -> R.drawable.ic_star_half
                    else -> R.drawable.ic_star_filled
                }
                
                Image(
                    painter = painterResource(id = starIcon),
                    contentDescription = "Star",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        
        Text(
            text = "$reviewCount reviews",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF171212)
        )
    }
}

@Composable
private fun RatingBreakdown(reviewRatings: List<ReviewRating>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        reviewRatings.forEach { rating ->
            RatingBar(
                stars = rating.stars,
                percentage = rating.percentage,
                count = rating.count
            )
        }
    }
}

@Composable
private fun RatingBar(
    stars: Int,
    percentage: Int,
    count: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Star number
        Text(
            text = stars.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF171212),
            modifier = Modifier.width(20.dp)
        )
        
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    color = Color(0xFFE5DBDB),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = percentage / 100f)
                    .background(
                        color = Color(0xFF171212),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
        
        // Percentage
        Text(
            text = "$percentage%",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF8A6163),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun BottomSection(onAddToCart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Add to Cart Button
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFABAC2)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Add to Cart",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF171212)
            )
        }
        
        // Bottom Navigation
        BottomNavigation()
    }
}

@Composable
private fun BottomNavigation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Divider
        HorizontalDivider(
            color = Color(0xFFF5F0F0),
            thickness = 1.dp
        )
        
        // Navigation Items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = R.drawable.ic_home,
                label = "Home",
                isSelected = true
            )
            
            NavigationItem(
                icon = R.drawable.ic_categories,
                label = "Categories",
                isSelected = false
            )
            
            NavigationItem(
                icon = R.drawable.ic_favorites,
                label = "Favorites",
                isSelected = false
            )
            
            NavigationItem(
                icon = R.drawable.ic_cart,
                label = "Cart",
                isSelected = false
            )
            
            NavigationItem(
                icon = R.drawable.ic_profile,
                label = "Profile",
                isSelected = false
            )
        }
        
        // Bottom spacing
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun NavigationItem(
    icon: Int,
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color(0xFF171212) else Color(0xFF8A6163)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailProductScreenPreview() {
    DetailProductScreen()
}