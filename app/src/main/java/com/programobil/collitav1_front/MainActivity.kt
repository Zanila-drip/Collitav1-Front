package com.programobil.collitav1_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.data.api.ApiService
import com.programobil.collitav1_front.data.repository.TrabajoRepository
import com.programobil.collitav1_front.ui.navigation.MainNavigation
import com.programobil.collitav1_front.ui.theme.Collitav1FrontTheme
import com.programobil.collitav1_front.ui.viewmodels.TrabajoViewModel
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel
import com.programobil.collitav1_front.security.TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar TokenManager
        TokenManager.initialize(this)
        
        // Inicializar el repositorio
        val apiService = ApiService.create()
        val trabajoRepository = TrabajoRepository(apiService)
        
        // Inicializar ViewModels
        val trabajoViewModel = TrabajoViewModel(trabajoRepository)
        val userViewModel = UserViewModel()
        
        setContent {
            Collitav1FrontTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainNavigation(
                        navController = navController,
                        trabajoViewModel = trabajoViewModel,
                        userViewModel = userViewModel
                    )
                }
            }
        }
    }
}