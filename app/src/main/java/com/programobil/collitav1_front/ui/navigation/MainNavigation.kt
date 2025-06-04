package com.programobil.collitav1_front.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.programobil.collitav1_front.ui.auth.LoginScreen
import com.programobil.collitav1_front.ui.auth.RegisterScreen
import com.programobil.collitav1_front.ui.screens.*
import com.programobil.collitav1_front.ui.viewmodels.TrabajoViewModel
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel

sealed class MainScreen(val route: String) {
    object Auth : MainScreen("auth")
    object Login : MainScreen("login")
    object Register : MainScreen("register")
    object Home : MainScreen("home")
    object Datos : MainScreen("datos")
    object HistorialPersonal : MainScreen("historial_personal")
    object Identificacion : MainScreen("identificacion")
    object Trabajo : MainScreen("trabajo")
}

@Composable
fun MainNavigation(
    navController: NavHostController,
    trabajoViewModel: TrabajoViewModel,
    userViewModel: UserViewModel
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.Login.route
    ) {
        composable(MainScreen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(MainScreen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(MainScreen.Home.route) {
                        popUpTo(MainScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(MainScreen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(MainScreen.Home.route) {
                        popUpTo(MainScreen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(MainScreen.Home.route) {
            UserHomeScreen(
                navController = navController,
                viewModel = trabajoViewModel
            )
        }
        composable(MainScreen.Datos.route) {
            DatosScreen(
                navController = navController,
                viewModel = userViewModel
            )
        }
        composable(MainScreen.HistorialPersonal.route) {
            HistorialPersonalScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = trabajoViewModel
            )
        }
        composable(MainScreen.Identificacion.route) {
            IdentificacionScreen(
                navController = navController,
                viewModel = userViewModel
            )
        }
        composable(MainScreen.Trabajo.route) {
            TrabajoScreen(
                navController = navController,
                viewModel = trabajoViewModel
            )
        }
    }
} 