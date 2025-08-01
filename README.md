# Happy Baby Style - Android App

## 📱 Overview
Happy Baby Style is a modern Android e-commerce application designed specifically for baby clothing and accessories. Built with Jetpack Compose and following MVVM architecture, the app provides a beautiful, intuitive shopping experience with a warm and welcoming design system.

## 🎨 Design Philosophy
The app features a carefully crafted design system with:
- **Soft, warm colors** inspired by baby aesthetics
- **Rounded typography** for a friendly, approachable feel
- **Custom icons** designed specifically for baby products
- **Responsive layouts** that work across different screen sizes

## 🏗️ Architecture

### MVVM Pattern
- **Model**: Data classes and repositories
- **View**: Jetpack Compose UI components
- **ViewModel**: Business logic and state management

### Key Components
- **BabyStyleComponents**: Reusable UI components with custom styling
- **Theme System**: Comprehensive color and typography system
- **Navigation**: Bottom navigation with 5 main sections

## 📱 Screens Implemented

### 1. 🏠 Home Screen (`HomeScreen.kt`)
- **Product grid** with baby clothing items
- **Search functionality** with real-time filtering
- **Category tabs** (Bodysuits, Hats, Outfits, Pajamas, Socks)
- **Product cards** with images, names, and prices
- **Bottom navigation** integration

### 2. 🔐 Login Screen (`LoginScreen.kt`)
- **Email and password** input fields
- **Remember me** functionality
- **Forgot password** link
- **Sign up** option for new users
- **Social login** buttons (Google, Facebook)

### 3. 👤 Profile Screen (`ProfileScreen.kt`)
- **User information** display
- **Profile editing** capabilities
- **Order history** section
- **Settings and preferences**
- **Help and support** options

### 4. 🛍️ Detail Product Screen (`DetailProductScreen.kt`)
- **Product images** with placeholder support
- **Size selection** (0-3M, 3-6M, 6-12M)
- **Product description** and details
- **Reviews and ratings** system
- **Add to cart** functionality

### 5. 🛒 Cart Screen (Structure Ready)
- Cart functionality structure prepared
- Product management capabilities

### 6. 📂 Categories Screen (Structure Ready)
- Category browsing structure prepared
- Product filtering capabilities

## 🎨 Design System

### Custom Components (`BabyStyleComponents.kt`)
- **BabyStyleText**: Text with rainbow and star effects
- **BabyStyleCard**: Rounded cards with custom styling
- **BabyStyleButton**: Custom buttons with rounded corners
- **RainbowStarIcon**: Custom icon component
- **CategoryChip**: Interactive category selection
- **ProductCard**: Product display component

### Color Palette
- **Primary**: `#FABAC2` (Soft Pink)
- **Secondary**: `#8A6163` (Muted Rose)
- **Background**: `#FFFFFF` (Pure White)
- **Surface**: `#F5F0F0` (Light Gray)
- **Text Primary**: `#171212` (Dark Gray)
- **Text Secondary**: `#8A6163` (Muted Rose)

### Typography
- **Display**: Large, bold text for headers
- **Headline**: Medium-sized headers
- **Body**: Regular text for content
- **Label**: Small text for labels and captions

## 🎯 Icons & Assets

### Custom SVG Icons (25 total)
- **Navigation**: `ic_home`, `ic_categories`, `ic_favorites`, `ic_cart`, `ic_profile`
- **Product Categories**: `ic_bodysuit`, `ic_hat`, `ic_outfit`, `ic_pajamas`, `ic_socks`
- **Actions**: `ic_search`, `ic_back_arrow`, `ic_arrow_right`, `ic_favorites`
- **User Interface**: `ic_edit_profile`, `ic_notifications`, `ic_help_support`
- **E-commerce**: `ic_payment`, `ic_shipping`, `ic_order_history`
- **Ratings**: `ic_star_filled`, `ic_star_half`
- **Placeholders**: `ic_product_placeholder`, `ic_empty_state`

## 🛠️ Technical Stack

### Core Dependencies
- **Jetpack Compose**: Modern UI toolkit
- **Material 3**: Latest Material Design components
- **ViewModel**: State management and lifecycle
- **Coroutines**: Asynchronous programming
- **Kotlin**: Programming language

### Build Configuration
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)
- **Compile SDK**: 36
- **Kotlin Version**: Latest stable
- **Java Version**: 11

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 36
- Kotlin 1.9+
- Java 11

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/mmaquera/happy-baby-style.git
   ```

2. Open the project in Android Studio

3. Sync Gradle files:
   ```bash
   ./gradlew build
   ```

4. Run the app on an emulator or device:
   ```bash
   ./gradlew installDebug
   ```

## 📱 App Features

### Current Features
- ✅ Complete UI design system
- ✅ 4 fully implemented screens (Home, Login, Profile, Detail)
- ✅ Custom component library
- ✅ Responsive layouts
- ✅ Search and filtering
- ✅ Category navigation
- ✅ Product browsing
- ✅ User profile management

### Planned Features
- 🔄 Cart functionality
- 🔄 Checkout process
- 🔄 User authentication
- 🔄 Product reviews
- 🔄 Wishlist management
- 🔄 Push notifications
- 🔄 Offline support

## 🎨 UI/UX Highlights

### Baby-Friendly Design
- **Soft color palette** that's easy on the eyes
- **Large touch targets** for easy interaction
- **Clear typography** for readability
- **Intuitive navigation** with familiar patterns

### Accessibility
- **Semantic descriptions** for screen readers
- **High contrast** text and icons
- **Scalable text** for different font sizes
- **Touch-friendly** button sizes

## 📊 Project Structure
```
app/src/main/java/com/mmaquera/happybabystyle/
├── MainActivity.kt                    # Main app entry point
├── ui/theme/
│   ├── BabyStyleComponents.kt        # Custom UI components
│   ├── Color.kt                      # Color definitions
│   ├── Theme.kt                      # Theme configuration
│   └── Type.kt                       # Typography definitions
└── view/
    ├── home/                         # Home screen
    ├── login/                        # Login screen
    ├── profile/                      # Profile screen
    ├── detail/                       # Product detail screen
    ├── cart/                         # Cart screen (structure)
    └── categories/                   # Categories screen (structure)
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Marco Maquera**
- GitHub: [@mmaquera](https://github.com/mmaquera)

## 🙏 Acknowledgments

- **Figma Design**: Original design inspiration
- **Jetpack Compose**: Modern Android UI toolkit
- **Material Design**: Design system guidelines
- **Android Community**: Continuous support and resources 