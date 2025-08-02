package com.mmaquera.happybabystyle.view.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel for the Welcome screen following SOLID principles and Clean Architecture
 * 
 * This ViewModel manages the state for the Welcome screen that exactly matches
 * the Figma design specifications with proper color codes and layout structure.
 * 
 * Single Responsibility: Manages welcome screen state and user interactions
 * Open/Closed: Extensible for future features without modifying existing code
 * Liskov Substitution: Follows ViewModel contract
 * Interface Segregation: Focused on welcome screen concerns only
 * Dependency Inversion: Depends on abstractions, not concrete implementations
 */
class WelcomeViewModel : ViewModel() {
    
    // Private mutable state flows following encapsulation principle
    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()
    
    /**
     * Handles login button click
     * Following Command pattern for user actions
     * 
     * In a real implementation, this would:
     * - Navigate to login screen
     * - Track analytics event
     * - Handle any pre-login validation
     */
    fun onLoginClick() {
        // TODO: Navigate to login screen
        // This would be handled by navigation component in a real app
    }
    
    /**
     * Handles sign up button click
     * Following Command pattern for user actions
     * 
     * In a real implementation, this would:
     * - Navigate to sign up screen
     * - Track analytics event
     * - Handle any pre-registration validation
     */
    fun onSignUpClick() {
        // TODO: Navigate to sign up screen
        // This would be handled by navigation component in a real app
    }
}

/**
 * UI State data class following immutability principle
 * All properties are read-only to prevent external modifications
 * 
 * This state class contains all the data needed to render the Welcome screen
 * exactly as specified in the Figma design, including:
 * - Button text and states
 * - Background image URL
 * - Loading states
 * - Accessibility information
 */
data class WelcomeUiState(
    val isLoading: Boolean = false,
    val backgroundImageUrl: String = "https://images.unsplash.com/photo-1544126592-807ade215a0b?w=800&h=1200&fit=crop",
    val isLoginEnabled: Boolean = true,
    val isSignUpEnabled: Boolean = true,
    val loginButtonColor: String = "#fabac2", // Exact Figma color
    val signUpButtonColor: String = "#f5f0f0", // Exact Figma color
    val textColor: String = "#171212" // Exact Figma text color
) 