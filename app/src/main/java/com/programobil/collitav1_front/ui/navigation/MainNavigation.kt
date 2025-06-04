package com.programobil.collitav1_front.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.ui.auth.AuthNavigation
import com.programobil.collitav1_front.ui.screens.DatosScreen
import com.programobil.collitav1_front.ui.screens.HistorialPersonalScreen
import com.programobil.collitav1_front.ui.screens.IdentificacionScreen
import com.programobil.collitav1_front.ui.screens.UserHomeScreen
import com.programobil.collitav1_front.di.AppModule

sealed class MainScreen(val route: String) {
    object Auth : MainScreen("auth")
    object Home : MainScreen("home")
    object Datos : MainScreen("datos")
    object HistorialPersonal : MainScreen("historial_personal")
    object Identificacion : MainScreen("identificacion")
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.Auth.route
    ) {
        composable(MainScreen.Auth.route) {
            AuthNavigation(
                onAuthSuccess = {
                    navController.navigate(MainScreen.Home.route) {
                        popUpTo(MainScreen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(MainScreen.Home.route) {
            UserHomeScreen(navController = navController)
        }

        composable(MainScreen.Datos.route) {
            DatosScreen(
                navController = navController,
                viewModel = AppModule.provideUserViewModel()
            )
        }

        composable(MainScreen.HistorialPersonal.route) {
            HistorialPersonalScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(MainScreen.Identificacion.route) {
            IdentificacionScreen(
                navController = navController,
                viewModel = AppModule.provideUserViewModel()
            )
        }
    }
} 