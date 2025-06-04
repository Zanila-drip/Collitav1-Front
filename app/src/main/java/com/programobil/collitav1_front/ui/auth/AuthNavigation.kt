package com.programobil.collitav1_front.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object Register : AuthScreen("register")
}

@Composable
fun AuthNavigation(
    onAuthSuccess: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(AuthScreen.Register.route)
                },
                onLoginSuccess = onAuthSuccess
            )
        }
        
        composable(AuthScreen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = onAuthSuccess
            )
        }
    }
} 