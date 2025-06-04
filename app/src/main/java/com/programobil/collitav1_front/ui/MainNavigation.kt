package com.programobil.collitav1_front.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.ui.screens.*

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(navController)
        }
        composable(Routes.HOME) {
            UserHomeScreen(navController)
        }
        composable(Routes.HISTORIAL) {
            HistorialScreen(navController)
        }
        composable(Routes.DATOS) {
            DatosScreen(navController)
        }
        composable(Routes.IDENTIFICACION) {
            IdentificacionScreen(navController)
        }
        composable(Routes.GANANCIAS) {
            GananciasScreen(navController)
        }
    }
} 