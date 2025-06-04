package com.programobil.collitav1_front.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import com.programobil.collitav1_front.ui.navigation.MainScreen
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel
import com.programobil.collitav1_front.security.TokenManager
import com.programobil.collitav1_front.di.AppModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen(
    navController: NavController,
    viewModel: UserViewModel = AppModule.provideUserViewModel()
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        val token = TokenManager.getToken()
        if (token != null) {
            viewModel.loadUserData(token)
        } else {
            // Si no hay token, mostrar error
            viewModel.loadUserData("") // Esto activará el manejo de error en el ViewModel
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Datos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error ?: "Error desconocido",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Fila superior: icono de perfil y saludo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "¡Hola! ${userData.nombre}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                // Información del usuario
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoRow(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = userData.email
                        )
                        InfoRow(
                            icon = Icons.Default.Phone,
                            label = "Teléfono",
                            value = userData.telefono
                        )
                    }
                }

                // Card 1: Identificación
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Identificación",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { navController.navigate(MainScreen.Identificacion.route) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Mi Identificación")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))

                // Card 2: Historial
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Historial",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { navController.navigate(MainScreen.HistorialPersonal.route) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Historial")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Texto de Cerrar Sesión
                TextButton(
                    onClick = { 
                        TokenManager.clearToken()
                        navController.navigate(MainScreen.Auth.route) {
                            popUpTo(MainScreen.Home.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Cerrar Sesión",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 