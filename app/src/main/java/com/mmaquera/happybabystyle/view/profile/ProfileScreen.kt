package com.mmaquera.happybabystyle.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mmaquera.happybabystyle.R
import com.mmaquera.happybabystyle.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val state = viewModel.state
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Header
        ProfileHeader()
        
        // Profile Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Profile Info Section
            item {
                ProfileInfoSection(state.user)
            }
            
            // Account Information Section
            item {
                SectionHeader("Account Information")
                AccountInfoItem(
                    icon = R.drawable.ic_edit_profile,
                    title = "Edit Profile",
                    onClick = { viewModel.handleEvent(ProfileEvent.EditProfile) }
                )
            }
            
            // Order History Section
            item {
                SectionHeader("Order History")
                state.orders.forEach { order ->
                    OrderHistoryItem(
                        order = order,
                        onClick = { viewModel.handleEvent(ProfileEvent.ViewOrder) }
                    )
                }
            }
            
            // Settings Section
            item {
                SectionHeader("Settings")
                SettingsItem(
                    icon = R.drawable.ic_notifications,
                    title = "Notifications",
                    onClick = { viewModel.handleEvent(ProfileEvent.Notifications) }
                )
                SettingsItem(
                    icon = R.drawable.ic_payment,
                    title = "Payment Methods",
                    onClick = { viewModel.handleEvent(ProfileEvent.PaymentMethods) }
                )
                SettingsItem(
                    icon = R.drawable.ic_shipping,
                    title = "Shipping Addresses",
                    onClick = { viewModel.handleEvent(ProfileEvent.ShippingAddresses) }
                )
                SettingsItem(
                    icon = R.drawable.ic_help_support,
                    title = "Help & Support",
                    onClick = { viewModel.handleEvent(ProfileEvent.HelpSupport) }
                )
            }
            
            // Log Out Button
            item {
                LogOutButton(
                    onClick = { viewModel.handleEvent(ProfileEvent.LogOut) }
                )
            }
        }
    }
}

@Composable
private fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(48.dp))
            
            Text(
                text = "Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { /* Handle settings click */ }
                    .background(SurfaceBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoSection(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(SurfaceBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile",
                modifier = Modifier.size(64.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // User Name
        Text(
            text = user.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
        
        // User Email
        Text(
            text = user.email,
            fontSize = 16.sp,
            color = SecondaryText
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryText,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
private fun AccountInfoItem(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Title
        Text(
            text = title,
            fontSize = 16.sp,
            color = PrimaryText,
            modifier = Modifier.weight(1f)
        )
        
        // Arrow
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Arrow",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun OrderHistoryItem(
    order: Order,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_order_history),
                contentDescription = "Order",
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Order Info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Order #${order.id}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )
            
            Text(
                text = "${order.items} item${if (order.items > 1) "s" else ""} • $${order.total}",
                fontSize = 14.sp,
                color = SecondaryText
            )
        }
        
        // Arrow
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Arrow",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SettingsItem(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Title
        Text(
            text = title,
            fontSize = 16.sp,
            color = PrimaryText,
            modifier = Modifier.weight(1f)
        )
        
        // Arrow
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Arrow",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun LogOutButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SurfaceBackground
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Log Out",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
        }
    }
}

@Preview(name = "Profile Screen", showBackground = true)
@Composable
fun ProfileScreenPreview() {
    HappyBabyStyleTheme {
        ProfileScreen()
    }
}