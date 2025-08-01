# Checkout Module

This module implements the checkout functionality for the Happy Baby Style app, following SOLID principles and Clean Architecture patterns.

## Architecture Overview

### MVC Pattern Implementation
- **Model**: Data classes and repository layer
- **View**: CheckoutScreen composable
- **Controller**: CheckoutViewModel

### SOLID Principles Applied

#### 1. Single Responsibility Principle (SRP)
- `ShippingInfo`: Only holds shipping data
- `PaymentMethod`: Only holds payment method data
- `CheckoutItem`: Only holds checkout item data
- `CheckoutOrderSummary`: Only holds order summary data
- `CheckoutRepository`: Only handles checkout data operations
- `CheckoutViewModel`: Only manages checkout UI state

#### 2. Open/Closed Principle (OCP)
- Repository interface allows for different implementations
- ViewModel can be extended without modifying existing code
- UI components are composable and reusable

#### 3. Liskov Substitution Principle (LSP)
- Repository implementations can be substituted without breaking functionality
- Data classes are immutable and can be safely copied

#### 4. Interface Segregation Principle (ISP)
- `CheckoutRepository` interface only contains checkout-related operations
- UI components have focused, specific responsibilities

#### 5. Dependency Inversion Principle (DIP)
- ViewModel depends on `CheckoutRepository` abstraction
- UI depends on ViewModel abstraction
- Dependencies are injected rather than created internally

## Components

### Data Classes
- `ShippingInfo`: Represents shipping address information
- `PaymentMethod`: Represents available payment methods
- `CheckoutItem`: Represents items in the checkout
- `CheckoutOrderSummary`: Represents order totals and calculations

### Repository Layer
- `CheckoutRepository`: Interface defining checkout operations
- `CheckoutRepositoryImpl`: Implementation with dummy data

### ViewModel
- `CheckoutViewModel`: Manages UI state and business logic
- Handles data loading, validation, and payment processing
- Provides reactive state management using StateFlow

### UI Components
- `CheckoutScreen`: Main checkout screen composable
- `CheckoutHeader`: Header with back button and title
- `ShippingInformationSection`: Form for shipping details
- `PaymentMethodSection`: Payment method selection
- `OrderSummarySection`: Order items and totals
- `PayNowButton`: Payment processing button

## Features

### Shipping Information
- Form fields for full name, address, city, state, and zip code
- Real-time validation
- Pre-filled with dummy data

### Payment Methods
- Credit Card and PayPal options
- Radio button selection
- Visual feedback for selected method

### Order Summary
- Displays checkout items with images and details
- Shows subtotal, shipping, and total
- Automatic calculation based on items

### Payment Processing
- "Pay Now" button with loading state
- Validation before enabling payment
- Simulated payment processing

## Usage

```kotlin
@Composable
fun MyScreen() {
    CheckoutScreen(
        onBackPressed = { /* Handle back navigation */ }
    )
}
```

## Dependencies

- Jetpack Compose for UI
- ViewModel for state management
- StateFlow for reactive programming
- Material Design 3 components
- Custom theme and typography

## Testing

The implementation is designed to be easily testable:
- Repository can be mocked for unit tests
- ViewModel logic is separated from UI
- UI components are pure composables
- State management is predictable and testable

## Future Enhancements

- Integration with real payment gateways
- Address validation and autocomplete
- Multiple shipping options
- Order confirmation and tracking
- Integration with cart system 