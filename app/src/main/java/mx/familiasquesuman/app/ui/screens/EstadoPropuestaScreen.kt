package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.data.Propuesta
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.StatusTag

private data class PasoProgreso(val icono: ImageVector, val titulo: String, val descripcion: String, val completado: Boolean)

/** Basada en estado_de_revision_de_propuesta. */
@Composable
fun EstadoPropuestaScreen(
    propuesta: Propuesta,
    onBack: () -> Unit
) {
    val pasos = listOf(
        PasoProgreso(Icons.Filled.Check, "Enviada", "Tu propuesta fue recibida exitosamente.", true),
        PasoProgreso(Icons.Filled.Sync, "En Revisión", "Estamos analizando la viabilidad de la iniciativa.", true),
        PasoProgreso(Icons.Filled.ThumbUp, "Aprobada", "Lista para buscar financiamiento o voluntarios.", false)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = "Estado de tu Propuesta", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            StatusTag(
                texto = "Iniciativa Comunitaria",
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.primary
            )
            Text(propuesta.titulo, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            Text(propuesta.estado.etiqueta, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
            Text(
                "Nuestro equipo está validando los detalles de tu iniciativa para asegurar que cumple con todos los requisitos. Te avisaremos pronto una vez completada la revisión.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
            )

            Text(
                "PROGRESO DE LA PROPUESTA",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            pasos.forEachIndexed { index, paso ->
                Row {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    if (paso.completado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHigh,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                paso.icono,
                                contentDescription = null,
                                tint = if (paso.completado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        if (index != pasos.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(36.dp)
                                    .background(if (paso.completado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(start = 16.dp, bottom = 24.dp)) {
                        Text(paso.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(paso.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
