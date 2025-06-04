package com.programobil.collitav1_front.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import com.programobil.collitav1_front.ui.Routes
import kotlinx.coroutines.delay

data class HistorialItem(
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaCierre: LocalTime,
    val tiempoTrabajo: String,
    val cosechaTotal: String,
    val ganancia: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    navController: NavController
) {
    var showDetails by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<HistorialItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(getHistorialItems()) { item ->
                HistorialCard(
                    item = item,
                    onShowDetails = {
                        selectedItem = item
                        showDetails = true
                    }
                )
            }
        }

        if (showDetails && selectedItem != null) {
            AlertDialog(
                onDismissRequest = { showDetails = false },
                title = { Text("Detalles del Registro") },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DetailRow(
                            icon = Icons.Default.CalendarToday,
                            label = "Fecha",
                            value = selectedItem!!.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        )
                        DetailRow(
                            icon = Icons.Default.AccessTime,
                            label = "Hora Inicio",
                            value = selectedItem!!.horaInicio.format(DateTimeFormatter.ofPattern("HH:mm"))
                        )
                        DetailRow(
                            icon = Icons.Default.AccessTime,
                            label = "Hora Fin",
                            value = selectedItem!!.horaCierre.format(DateTimeFormatter.ofPattern("HH:mm"))
                        )
                        DetailRow(
                            icon = Icons.Default.Timer,
                            label = "Tiempo Trabajado",
                            value = selectedItem!!.tiempoTrabajo
                        )
                        DetailRow(
                            icon = Icons.Default.LocalFlorist,
                            label = "Cosecha Total",
                            value = selectedItem!!.cosechaTotal
                        )
                        DetailRow(
                            icon = Icons.Default.AttachMoney,
                            label = "Ganancia",
                            value = selectedItem!!.ganancia
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showDetails = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

@Composable
fun DetailRow(
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
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialCard(
    item: HistorialItem,
    onShowDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.fecha.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES")),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                Text(
                    text = "$${item.ganancia}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tiempo",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = item.tiempoTrabajo,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text(
                        text = "Cosecha",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "${item.cosechaTotal}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onShowDetails,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Detalles",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ver Detalles")
                }
            }
        }
    }
}

fun getHistorialItems(): List<HistorialItem> {
    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE")

    return (0..6).map { daysAgo ->
        val date = currentDate.minusDays(daysAgo.toLong())
        val startTime = date.atTime(8, 0)
        val endTime = date.atTime(16, 0)
        val duration = "8h 00m"
        val cosecha = (20..50).random()
        val ganancia = cosecha * 2.5

        HistorialItem(
            fecha = date,
            horaInicio = startTime.toLocalTime(),
            horaCierre = endTime.toLocalTime(),
            tiempoTrabajo = duration,
            cosechaTotal = "${cosecha} kg",
            ganancia = "$${ganancia}"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistorialScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        HistorialScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun HistorialCardPreview() {
    MaterialTheme {
        HistorialCard(
            item = HistorialItem(
                fecha = LocalDate.now(),
                horaInicio = LocalTime.of(8, 0),
                horaCierre = LocalTime.of(16, 0),
                tiempoTrabajo = "8h 00m",
                cosechaTotal = "30 kg",
                ganancia = "$75.00"
            ),
            onShowDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailRowPreview() {
    MaterialTheme {
        DetailRow(
            icon = Icons.Default.CalendarToday,
            label = "Fecha",
            value = "01/01/2024"
        )
    }
} 