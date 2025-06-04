package com.programobil.collitav1_front.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.programobil.collitav1_front.data.model.Trabajo
import com.programobil.collitav1_front.ui.viewmodels.TrabajoViewModel
import com.programobil.collitav1_front.ui.viewmodels.TrabajoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialPersonalScreen(
    onNavigateBack: () -> Unit,
    viewModel: TrabajoViewModel
) {
    val trabajoState by viewModel.trabajoState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarTrabajos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial Personal") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (trabajoState) {
            is TrabajoState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is TrabajoState.Success -> {
                val trabajos = (trabajoState as TrabajoState.Success).trabajos
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(trabajos) { trabajo ->
                        TrabajoItem(trabajo = trabajo)
                    }
                }
            }
            is TrabajoState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (trabajoState as TrabajoState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun TrabajoItem(trabajo: Trabajo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Fecha: ${trabajo.fecha}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hora Inicio: ${trabajo.horaInicio}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Hora Fin: ${trabajo.horaFin}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tiempo Total: ${trabajo.tiempoTotal}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Cosecha: ${trabajo.cosecha}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Precio: $${trabajo.precioAproximado}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Estado: ${trabajo.estado}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 