package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import mx.familiasquesuman.app.data.ActividadItem
import mx.familiasquesuman.app.data.MiembroComunidad
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton
import mx.familiasquesuman.app.ui.components.StatusTag
import mx.familiasquesuman.app.ui.components.UbicacionRow

/**
 * Combina actividad_e_inscripcion_azul con la selección de "Miembros de mi comunidad"
 * de inscripcion_con_gestion_de_miembros (la versión más completa de inscripción).
 */
@Composable
fun ActividadDetalleScreen(
    actividad: ActividadItem,
    miembrosComunidad: List<MiembroComunidad>,
    onBack: () -> Unit,
    onIrAMiComunidad: () -> Unit,
    onInscripcionExitosa: () -> Unit
) {
    val context = LocalContext.current
    var acompanantesSeleccionados by remember { mutableStateOf(setOf<String>()) }
    var mostrarExito by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = "Detalle de Actividad", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            StatusTag(texto = actividad.etiquetaAudiencia, containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.secondary)
            Text(actividad.titulo, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            UbicacionRow(texto = actividad.ubicacion)

            Row(modifier = Modifier.padding(top = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoCard(icono = Icons.Filled.CalendarMonth, titulo = "Fecha y Hora", valor = actividad.fechaHoraCompleta, modifier = Modifier.weight(1f))
                InfoCard(icono = Icons.Filled.Groups, titulo = "Cupos Disponibles", valor = "${actividad.cuposDisponibles} de ${actividad.cuposTotales}", modifier = Modifier.weight(1f))
            }

            Text("Acerca de la actividad", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
            Text(actividad.descripcionLarga, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(actividad.recomendaciones, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))

            Text("Inscripción", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
            GhostBorderCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
                        Text("Tú", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    }
                    Text("Titular (Tú)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 12.dp))
                }
            }

            Text("Acompañantes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
            Text("Miembros de mi comunidad", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                miembrosComunidad.forEach { miembro ->
                    val seleccionado = acompanantesSeleccionados.contains(miembro.id)
                    GhostBorderCard(onClick = {
                        acompanantesSeleccionados = if (seleccionado) acompanantesSeleccionados - miembro.id else acompanantesSeleccionados + miembro.id
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = seleccionado, onCheckedChange = {
                                acompanantesSeleccionados = if (seleccionado) acompanantesSeleccionados - miembro.id else acompanantesSeleccionados + miembro.id
                            })
                            Column(modifier = Modifier.padding(start = 4.dp)) {
                                Text(miembro.nombre, style = MaterialTheme.typography.bodyMedium)
                                Text(miembro.relacion, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.PersonAdd, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    "Gestionar mi comunidad",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable(onClick = onIrAMiComunidad)
                )
            }

            PrimaryButton(
                text = "Confirmar Registro",
                onClick = { mostrarExito = true },
                modifier = Modifier.padding(top = 24.dp)
            )
            Text(
                "Al confirmar, aceptas nuestras condiciones de participación.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    if (mostrarExito) {
        Dialog(onDismissRequest = { }) {
            GhostBorderCard {
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                Text("¡Inscripción Exitosa!", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 12.dp))
                Text(
                    "Te has registrado para la ${actividad.titulo}. Te hemos enviado los detalles a tu correo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
                SecondaryButton(
                    text = "Agregar al Calendario",
                    onClick = {
                        val intent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                            putExtra(CalendarContract.Events.TITLE, actividad.titulo)
                            putExtra(CalendarContract.Events.EVENT_LOCATION, actividad.ubicacion)
                            putExtra(CalendarContract.Events.DESCRIPTION, actividad.descripcionCorta)
                        }
                        runCatching { context.startActivity(intent) }
                            .onFailure { Toast.makeText(context, "No se encontró una app de calendario.", Toast.LENGTH_SHORT).show() }
                    }
                )
                PrimaryButton(text = "Cerrar", onClick = { mostrarExito = false; onInscripcionExitosa() }, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
private fun InfoCard(icono: androidx.compose.ui.graphics.vector.ImageVector, titulo: String, valor: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.medium)
            .padding(14.dp)
    ) {
        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(titulo, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 6.dp))
        Text(valor, style = MaterialTheme.typography.titleMedium)
    }
}
