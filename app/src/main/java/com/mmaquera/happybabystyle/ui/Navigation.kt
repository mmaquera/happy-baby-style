package com.mmaquera.happybabystyle.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmaquera.happybabystyle.view.home.HomeScreen
import com.mmaquera.happybabystyle.view.login.LoginScreen
import com.mmaquera.happybabystyle.view.signup.SignUpScreen
import com.mmaquera.happybabystyle.view.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen
    ) {
        composable<WelcomeScreen> {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(LoginScreen)
                },
                onSignUpClick = {
                    navController.navigate(SignUpScreen)
                }
            )
        }
        composable<LoginScreen> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(HomeScreen)
                }
            )
        }
        composable<HomeScreen> {
            HomeScreen()
        }
        composable<SignUpScreen> {
            SignUpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(LoginScreen)
                },
                onSignUpSuccess = {
                    navController.navigate(LoginScreen){
                        popUpTo(SignUpScreen){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}