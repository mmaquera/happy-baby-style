# CartScreen Implementation

## Overview
This implementation follows the MVC (Model-View-Controller) pattern with Clean Architecture principles and SOLID design patterns. The CartScreen displays a shopping cart with items, quantity controls, order summary, and checkout functionality.

## Architecture Components

### 1. Data Models (Model Layer)
- **CartItem**: Represents individual items in the cart
  - `id`: Unique identifier
  - `name`: Product name
  - `size`: Product size
  - `price`: Product price
  - `quantity`: Current quantity
  - `imageUrl`: Product image reference

- **OrderSummary**: Represents the order calculation
  - `subtotal`: Sum of all items
  - `shipping`: Shipping cost (free)
  - `tax`: Calculated tax (8%)
  - `total`: Final total amount

### 2. Repository Pattern (Data Layer)
- **CartRepository Interface**: Defines contract for cart operations
  - `getCartItems()`: Retrieve all cart items
  - `updateItemQuantity()`: Update item quantity
  - `removeItem()`: Remove item from cart
  - `clearCart()`: Clear entire cart

- **CartRepositoryImpl**: Concrete implementation with dummy data
  - Follows Single Responsibility Principle
  - Provides sample cart items for testing

### 3. ViewModel (Controller Layer)
- **CartViewModel**: Manages UI state and business logic
  - Uses StateFlow for reactive state management
  - Implements quantity increase/decrease logic
  - Calculates order summary automatically
  - Follows Dependency Inversion Principle

### 4. UI Components (View Layer)
- **CartScreen**: Main composable function
- **CartHeader**: Header with back button and title
- **CartItemCard**: Individual cart item display
- **OrderSummarySection**: Order calculation display
- **CheckoutButton**: Checkout action button
- **BottomNavigation**: Navigation bar

## SOLID Principles Implementation

### Single Responsibility Principle (SRP)
- Each class has a single, well-defined responsibility
- CartItem: Only holds item data
- OrderSummary: Only handles order calculations
- CartViewModel: Only manages cart state
- UI Components: Only handle display logic

### Open/Closed Principle (OCP)
- Repository interface allows for different implementations
- OrderSummary.calculate() can be extended without modification
- UI components are composable and reusable

### Liskov Substitution Principle (LSP)
- CartRepositoryImpl properly implements CartRepository interface
- All implementations can be substituted without breaking functionality

### Interface Segregation Principle (ISP)
- CartRepository interface only contains cart-related operations
- No unnecessary dependencies or methods

### Dependency Inversion Principle (DIP)
- CartViewModel depends on CartRepository abstraction
- High-level modules don't depend on low-level modules

## Design System Integration

### Colors
- Uses established color palette from `Color.kt`
- PrimaryText: #171212
- SecondaryText: #8A6163
- SurfaceBackground: #F5F0F0
- BorderLight: #E5DBDB

### Typography
- Follows Material Design 3 typography
- Uses established font weights and sizes
- Consistent text hierarchy

### Icons
- Uses Material Design icons from `Icons.Default`
- ArrowBack, Home, Category, Favorite, ShoppingCart, Person
- Consistent icon sizing and colors

### Components
- Rounded corners (8dp for cards, 24dp for buttons)
- Consistent spacing (16dp, 8dp, 4dp)
- Proper elevation and shadows

## Features

### Cart Management
- Display cart items with product details
- Quantity controls (+/- buttons)
- Automatic removal when quantity reaches 0
- Real-time order summary calculation

### Order Summary
- Subtotal calculation
- Free shipping display
- Tax calculation (8%)
- Total amount display

### Navigation
- Back button functionality
- Bottom navigation with 5 tabs
- Current screen highlighting

### User Experience
- Responsive design
- Touch-friendly buttons
- Clear visual hierarchy
- Consistent spacing and alignment

## Testing

### Preview
- CartScreenPreview composable for UI testing
- Uses HappyBabyStyleTheme for consistent styling

### Dummy Data
- Sample cart items with realistic data
- Different product types and sizes
- Various quantities for testing

## Future Enhancements

### Planned Features
- Real image loading with Coil
- Persistence with Room database
- Network integration for real products
- Payment processing integration
- Order history tracking

### Technical Improvements
- Unit tests for ViewModel
- Integration tests for Repository
- UI tests for Composable functions
- Error handling and loading states
- Accessibility improvements

## Usage

```kotlin
@Composable
fun MyApp() {
    CartScreen(
        onBackClick = { /* Navigate back */ },
        onHomeClick = { /* Navigate to home */ },
        onCategoriesClick = { /* Navigate to categories */ },
        onFavoritesClick = { /* Navigate to favorites */ },
        onProfileClick = { /* Navigate to profile */ }
    )
}
```

## Dependencies

- **Jetpack Compose**: UI framework
- **ViewModel**: State management
- **StateFlow**: Reactive programming
- **Material Design 3**: Design system
- **Coil**: Image loading (planned)
- **Room**: Database (planned)
- **Hilt**: Dependency injection (planned) 