package com.programobil.collitav1_front.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel
import com.programobil.collitav1_front.di.AppModule
import com.programobil.collitav1_front.ui.navigation.MainScreen
import com.programobil.collitav1_front.security.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentificacionScreen(
    navController: NavController,
    viewModel: UserViewModel = AppModule.provideUserViewModel()
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    // Cargar datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        try {
            val token = TokenManager.getToken()
            if (token != null) {
                viewModel.loadUserData(token)
            } else {
                navController.navigate(MainScreen.Auth.route) {
                    popUpTo(MainScreen.Datos.route) { inclusive = true }
                }
            }
        } catch (e: Exception) {
            // Si hay un error al obtener el token, redirigir al login
            navController.navigate(MainScreen.Auth.route) {
                popUpTo(MainScreen.Datos.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Identificación") },
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = error ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = { navController.navigate(MainScreen.Auth.route) }
                    ) {
                        Text("Volver al inicio de sesión")
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título de la sección
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Tarjeta de información personal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Campo CURP
                        InfoField(
                            label = "CURP",
                            value = userData.curp,
                            icon = Icons.Default.Badge
                        )

                        // Campo Nombre
                        InfoField(
                            label = "Nombre(s)",
                            value = userData.nombre,
                            icon = Icons.Default.Person
                        )

                        // Campo Apellido Paterno
                        InfoField(
                            label = "Apellido Paterno",
                            value = userData.apellidoPaterno,
                            icon = Icons.Default.Person
                        )

                        // Campo Apellido Materno
                        InfoField(
                            label = "Apellido Materno",
                            value = userData.apellidoMaterno,
                            icon = Icons.Default.Person
                        )

                        // Campo Teléfono
                        InfoField(
                            label = "Teléfono",
                            value = userData.telefono,
                            icon = Icons.Default.Phone
                        )

                        // Campo Email
                        InfoField(
                            label = "Correo Electrónico",
                            value = userData.email,
                            icon = Icons.Default.Email
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje informativo
                Text(
                    text = "Esta información es proporcionada por el sistema y no puede ser modificada.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoField(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IdentificacionScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        IdentificacionScreen(navController = navController)
    }
} 