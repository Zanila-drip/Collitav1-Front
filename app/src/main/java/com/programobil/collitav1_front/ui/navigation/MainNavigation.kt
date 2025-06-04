package com.programobil.collitav1_front.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.ui.Routes
import com.programobil.collitav1_front.ui.screens.*

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(navController = navController)
        }
        composable(Routes.HOME) {
            UserHomeScreen(navController = navController)
        }
        composable(Routes.DATOS) {
            DatosScreen(navController = navController)
        }
        composable(Routes.IDENTIFICACION) {
            IdentificacionScreen(navController = navController)
        }
        composable(Routes.GANANCIAS) {
            GananciasScreen(navController = navController)
        }
        composable(Routes.HISTORIAL_PERSONAL) {
            HistorialScreen(navController = navController)
        }
        composable(Routes.FINALIZAR) {
            FinalizarScreen(navController = navController)
        }
    }
} 