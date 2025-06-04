package com.programobil.collitav1_front.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.delay
import com.programobil.collitav1_front.data.model.Trabajo
import com.programobil.collitav1_front.ui.viewmodels.TrabajoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrabajoScreen(
    navController: NavController,
    viewModel: TrabajoViewModel
) {
    var isWorking by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf<LocalTime?>(null) }
    var showTimer by remember { mutableStateOf(true) }
    var showStopDialog by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf("00:00:00") }
    var cosechaTotal by remember { mutableStateOf("") }
    var showCosechaDialog by remember { mutableStateOf(false) }
    var precioAproximado by remember { mutableStateOf("") }

    // Efecto para actualizar el contador
    LaunchedEffect(isWorking, showTimer) {
        while (isWorking && showTimer) {
            val now = LocalTime.now()
            val start = startTime ?: now
            val duration = java.time.Duration.between(start, now)
            elapsedTime = String.format(
                "%02d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
            )
            delay(1000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trabajo Actual") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        },
        floatingActionButton = {
            if (!isWorking) {
                FloatingActionButton(
                    onClick = { 
                        startTime = LocalTime.now()
                        isWorking = true 
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Comenzar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botón para ocultar/mostrar el contador
                    FloatingActionButton(
                        onClick = { showTimer = !showTimer },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = if (showTimer) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showTimer) "Ocultar tiempo" else "Mostrar tiempo",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    
                    // Botón para detener
                    FloatingActionButton(
                        onClick = { showStopDialog = true },
                        containerColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "Detener",
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isWorking) {
                    // Contador de tiempo
                    if (showTimer) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = elapsedTime,
                                modifier = Modifier
                                    .padding(horizontal = 24.dp, vertical = 12.dp)
                                    .align(Alignment.CenterHorizontally),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    // Botón para ingresar cosecha
                    Button(
                        onClick = { showCosechaDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Cosecha"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Registrar Cosecha")
                    }

                    // Mostrar cosecha actual si existe
                    if (cosechaTotal.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cosecha Total",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = cosechaTotal,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    // Mensaje cuando no hay trabajo en curso
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Comenzar",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Presiona el botón para comenzar una nueva sesión de trabajo",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo para ingresar cosecha
    if (showCosechaDialog) {
        var cosechaInput by remember { mutableStateOf("") }
        
        AlertDialog(
            onDismissRequest = { showCosechaDialog = false },
            title = { Text("Registrar Cosecha") },
            text = {
                Column {
                    OutlinedTextField(
                        value = cosechaInput,
                        onValueChange = { cosechaInput = it },
                        label = { Text("Cantidad (kg)") },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (cosechaInput.isNotEmpty()) {
                            cosechaTotal = "$cosechaInput kg"
                            showCosechaDialog = false
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCosechaDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de detención
    if (showStopDialog) {
        AlertDialog(
            onDismissRequest = { showStopDialog = false },
            title = { Text("Finalizar Trabajo") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("¿Estás seguro que deseas finalizar la sesión de trabajo?")
                    Text(
                        "Tiempo transcurrido: $elapsedTime",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (cosechaTotal.isNotEmpty()) {
                        Text(
                            "Cosecha total: $cosechaTotal",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val trabajo = Trabajo(
                            fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            horaInicio = startTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "",
                            horaFin = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                            tiempoTotal = elapsedTime,
                            cosecha = cosechaTotal,
                            precioAproximado = precioAproximado.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.guardarTrabajo(trabajo)
                        isWorking = false
                        startTime = null
                        showStopDialog = false
                        navController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Finalizar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStopDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
} 