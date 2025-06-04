package com.programobil.collitav1_front.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.programobil.collitav1_front.ui.viewmodels.UserViewModel
import com.programobil.collitav1_front.security.TokenManager
import com.programobil.collitav1_front.ui.navigation.MainScreen
import com.programobil.collitav1_front.di.AppModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentificacionScreen(
    navController: NavController,
    viewModel: UserViewModel = AppModule.provideUserViewModel()
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        val token = TokenManager.getToken()
        if (token == null) {
            // Si no hay token, redirigir al login
            navController.navigate(MainScreen.Auth.route) {
                popUpTo(MainScreen.Home.route) { inclusive = true }
            }
        } else {
            // Si hay token, cargar los datos del usuario
            viewModel.loadUserData(token)
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo CURP
                OutlinedTextField(
                    value = userData.curp,
                    onValueChange = { },
                    label = { Text("CURP") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Badge, contentDescription = "CURP")
                    }
                )

                // Campo Nombre
                OutlinedTextField(
                    value = userData.nombre,
                    onValueChange = { },
                    label = { Text("Nombre(s)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Nombre")
                    }
                )

                // Campo Apellido Paterno
                OutlinedTextField(
                    value = userData.apellidoPaterno,
                    onValueChange = { },
                    label = { Text("Apellido Paterno") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Apellido Paterno")
                    }
                )

                // Campo Apellido Materno
                OutlinedTextField(
                    value = userData.apellidoMaterno,
                    onValueChange = { },
                    label = { Text("Apellido Materno") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Apellido Materno")
                    }
                )

                // Campo Teléfono
                OutlinedTextField(
                    value = userData.telefono,
                    onValueChange = { },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                    }
                )

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

@Preview(showBackground = true)
@Composable
private fun IdentificacionScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        IdentificacionScreen(navController = navController)
    }
} 