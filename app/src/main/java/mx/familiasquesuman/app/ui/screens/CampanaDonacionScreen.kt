package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import mx.familiasquesuman.app.data.ArticuloDonacion
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.StatusTag
import mx.familiasquesuman.app.ui.components.UbicacionRow

/** Basada en campana_de_donacion_azul (donación de artículos físicos, no dinero). */
@Composable
fun CampanaDonacionScreen(onRegistrarEntrega: () -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(titulo = "Donaciones")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text("Campaña de Invierno", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(
                "Ayúdanos a recolectar artículos esenciales para familias en situación de vulnerabilidad durante esta temporada de frío. Tu aporte físico hace la diferencia.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f), MaterialTheme.shapes.medium)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Text(
                    "No procesamos pagos monetarios. Solo recibimos donaciones físicas.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text("Artículos Necesarios", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                val iconos = listOf(Icons.Filled.Checkroom, Icons.Filled.Restaurant, Icons.Filled.Bed)
                SampleData.campanaInvierno.forEachIndexed { index, articulo ->
                    ArticuloCard(icono = iconos[index], articulo = articulo)
                }
            }

            Text("Punto de Entrega", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 12.dp))
            GhostBorderCard {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    UbicacionRow(texto = "Centro Comunitario Central")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=" + Uri.encode("Av. Las Rosas 1234, Centro Comunitario Central"))
                            )
                            runCatching { context.startActivity(intent) }
                                .onFailure { Toast.makeText(context, "No se encontró una app de mapas.", Toast.LENGTH_SHORT).show() }
                        }
                    ) {
                        Icon(Icons.Filled.Directions, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Text("Cómo llegar", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Text("Av. Las Rosas 1234, Salón Principal.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))

                Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("Horario de Recepción", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 6.dp))
                }
                Text("Lunes a Viernes: 09:00 - 18:00", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                Text("Sábados: 10:00 - 14:00", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            PrimaryButton(
                text = "Registrar mi entrega",
                onClick = {
                    Toast.makeText(context, "¡Gracias! Registramos tu entrega en el punto de recepción.", Toast.LENGTH_LONG).show()
                    onRegistrarEntrega()
                },
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun ArticuloCard(icono: ImageVector, articulo: ArticuloDonacion) {
    GhostBorderCard {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(articulo.nombre, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 8.dp))
            }
            StatusTag(
                texto = articulo.prioridad,
                containerColor = if (articulo.prioridad == "Urgente") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
                contentColor = if (articulo.prioridad == "Urgente") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary
            )
        }
        Text(articulo.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 10.dp))
        LinearProgressIndicator(
            progress = { articulo.progreso },
            modifier = Modifier.fillMaxWidth().height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
        Text(
            articulo.progresoTexto,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
