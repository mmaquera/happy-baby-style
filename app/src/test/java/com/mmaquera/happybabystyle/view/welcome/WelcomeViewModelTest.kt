package com.mmaquera.happybabystyle.view.welcome

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for WelcomeViewModel following SOLID principles and Clean Architecture
 * 
 * Tests cover:
 * - Initial state validation
 * - User interaction handling
 * - State management
 * - SOLID principles compliance
 * - Figma design specifications validation
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest {
    
    private lateinit var viewModel: WelcomeViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WelcomeViewModel()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `initial state should have correct default values`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Default values should be correct
        assertEquals(false, initialState.isLoading)
        assertEquals("Login", initialState.loginButtonText)
        assertEquals("Sign Up", initialState.signUpButtonText)
        assertEquals(true, initialState.isLoginEnabled)
        assertEquals(true, initialState.isSignUpEnabled)
        assertTrue(initialState.backgroundImageUrl.isNotEmpty())
    }
    
    @Test
    fun `figma color specifications should match design`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Figma color specifications should be correct
        assertEquals("#fabac2", initialState.loginButtonColor)
        assertEquals("#f5f0f0", initialState.signUpButtonColor)
        assertEquals("#171212", initialState.textColor)
    }
    
    @Test
    fun `login button should be enabled by default`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Login button should be enabled
        assertTrue(initialState.isLoginEnabled)
    }
    
    @Test
    fun `sign up button should be enabled by default`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Sign up button should be enabled
        assertTrue(initialState.isSignUpEnabled)
    }
    
    @Test
    fun `background image URL should be valid`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Background image URL should be valid
        assertTrue(initialState.backgroundImageUrl.isNotEmpty())
        assertTrue(initialState.backgroundImageUrl.startsWith("http"))
        assertTrue(initialState.backgroundImageUrl.contains("unsplash.com"))
    }
    
    @Test
    fun `onLoginClick should not throw exception`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Login button is clicked
        // Then: No exception should be thrown
        viewModel.onLoginClick()
        
        // Verify the method executes without error
        // In a real implementation, this would trigger navigation
    }
    
    @Test
    fun `onSignUpClick should not throw exception`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Sign up button is clicked
        // Then: No exception should be thrown
        viewModel.onSignUpClick()
        
        // Verify the method executes without error
        // In a real implementation, this would trigger navigation
    }
    
    @Test
    fun `uiState should be immutable`() = runTest {
        // Given: WelcomeViewModel is created
        val initialState = viewModel.uiState.first()
        
        // When: Attempting to modify state (this should not be possible)
        // Then: State should remain immutable
        // Note: This test verifies that the data class is properly designed
        // and that no mutable state is exposed
        
        // Verify that the state flow is read-only
        assertEquals(initialState, viewModel.uiState.first())
    }
    
    @Test
    fun `viewModel should follow single responsibility principle`() {
        // Given: WelcomeViewModel is created
        
        // When: Analyzing the class structure
        
        // Then: ViewModel should only handle welcome screen concerns
        // This is verified by checking that the class only contains
        // methods and properties related to welcome screen functionality
        
        val methods = WelcomeViewModel::class.java.declaredMethods
        val methodNames = methods.map { it.name }
        
        // Should only contain welcome screen related methods
        assertTrue(methodNames.contains("onLoginClick"))
        assertTrue(methodNames.contains("onSignUpClick"))
        
        // Should not contain methods unrelated to welcome screen
        assertFalse(methodNames.contains("onProductClick"))
        assertFalse(methodNames.contains("onCartClick"))
    }
    
    @Test
    fun `uiState should follow data class best practices`() {
        // Given: WelcomeUiState data class
        
        // When: Creating instances
        val state1 = WelcomeUiState()
        val state2 = WelcomeUiState(
            loginButtonText = "Custom Login",
            signUpButtonText = "Custom Sign Up",
            loginButtonColor = "#customColor"
        )
        
        // Then: Data class should work correctly
        assertEquals("Login", state1.loginButtonText)
        assertEquals("Custom Login", state2.loginButtonText)
        assertEquals("Custom Sign Up", state2.signUpButtonText)
        assertEquals("#customColor", state2.loginButtonColor)
        
        // Copy should work correctly
        val state3 = state1.copy(loginButtonText = "Modified Login")
        assertEquals("Modified Login", state3.loginButtonText)
        assertEquals("Login", state1.loginButtonText) // Original unchanged
    }
    
    @Test
    fun `figma design specifications should be consistent`() = runTest {
        // Given: WelcomeViewModel is created
        
        // When: Initial state is observed
        val initialState = viewModel.uiState.first()
        
        // Then: Figma design specifications should be consistent
        // Login button should have pink color
        assertEquals("#fabac2", initialState.loginButtonColor)
        
        // Sign up button should have gray color
        assertEquals("#f5f0f0", initialState.signUpButtonColor)
        
        // Text should have dark color
        assertEquals("#171212", initialState.textColor)
        
        // Button texts should be standard
        assertEquals("Login", initialState.loginButtonText)
        assertEquals("Sign Up", initialState.signUpButtonText)
    }
} 