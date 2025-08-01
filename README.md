# Happy Baby Style - Detail Product Screen

## Overview
This project implements a product detail screen for the Happy Baby Style app using Jetpack Compose and following the MVC (Model-View-Controller) pattern. The design is based on a Figma mockup and includes all the necessary UI components and dummy data.

## Implementation Details

### Architecture Pattern: MVC
- **Model**: `DetailProductViewModel` - Handles data and business logic
- **View**: `DetailProductScreen` - UI components and user interactions
- **Controller**: The ViewModel acts as the controller, managing state and user actions

### Key Features Implemented

1. **Header Section**
   - Back navigation button with custom SVG icon
   - App title "Happy Baby Style"

2. **Product Image Section**
   - Large product image placeholder
   - Rounded corners for modern look

3. **Size Selection**
   - Interactive size chips (0-3M, 3-6M, 6-12M)
   - Visual feedback for selected size
   - State management through ViewModel

4. **Product Description**
   - Detailed product description
   - Proper typography and spacing

5. **Reviews Section**
   - Overall rating display (4.5 stars)
   - Star rating visualization
   - Review count (120 reviews)
   - Detailed rating breakdown with progress bars

6. **Add to Cart Button**
   - Prominent call-to-action button
   - Custom pink color scheme
   - Rounded design

7. **Bottom Navigation**
   - 5 navigation items (Home, Categories, Favorites, Cart, Profile)
   - Custom SVG icons
   - Active state indication

### SVG Icons Created
All icons are exported as SVG and placed in the `drawable` folder:
- `ic_back_arrow.xml` - Back navigation
- `ic_star_filled.xml` - Filled star for ratings
- `ic_star_half.xml` - Half-filled star for ratings
- `ic_home.xml` - Home navigation
- `ic_categories.xml` - Categories navigation
- `ic_favorites.xml` - Favorites navigation
- `ic_cart.xml` - Cart navigation
- `ic_profile.xml` - Profile navigation
- `ic_product_placeholder.xml` - Product image placeholder

### Data Models
- `ProductDetail` - Product information
- `ReviewRating` - Individual rating data
- `DetailProductUiState` - Complete UI state

### Color Scheme
Following the Figma design:
- Primary text: `#171212`
- Secondary text: `#8A6163`
- Background: `#FFFFFF`
- Light background: `#F5F0F0`
- Button color: `#FABAC2`
- Progress bar background: `#E5DBDB`

## How to Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Run the app on an emulator or device
4. The DetailProductScreen will be displayed as the main screen

## Dependencies Used
- Jetpack Compose for UI
- ViewModel for state management
- Coroutines for asynchronous operations
- Material3 for design components

## Future Enhancements
- Real product images from API
- Navigation between screens
- Cart functionality
- User authentication
- Product search and filtering 