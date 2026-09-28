package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import mx.familiasquesuman.app.data.MiembroComunidad
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.SecondaryButton
import mx.familiasquesuman.app.ui.components.TertiaryTextButton
import mx.familiasquesuman.app.ui.components.UbicacionRow

/** Basada en mi_perfil_con_gestion_de_comunidad_y_experiencias (la versión más completa). */
@Composable
fun PerfilScreen(
    comunidad: List<MiembroComunidad>,
    fondoAnimadoActivo: Boolean = true,
    onCambiarFondoAnimado: (Boolean) -> Unit = {},
    onProponerIniciativa: () -> Unit,
    onMiComunidad: () -> Unit,
    onMisPropuestas: () -> Unit,
    onPreferencias: () -> Unit,
    onVerActividad: (String) -> Unit,
    onMenu: () -> Unit = {},
    onNotificaciones: () -> Unit = {},
    hayNotificacionesSinLeer: Boolean = false
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(titulo = "Perfil", onMenu = onMenu, onNotificaciones = onNotificaciones, hayNotificacionesSinLeer = hayNotificacionesSinLeer)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("FL", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Familia López", style = MaterialTheme.typography.titleLarge)
                        Icon(
                            Icons.Filled.Verified,
                            contentDescription = "Verificado",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                        )
                    }
                    Text(
                        "Nivel: Embajadores Comunitarios",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("5x Naturaleza", "Cuidado Mayor", "Comedores").forEach { insignia ->
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), MaterialTheme.shapes.small)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(insignia, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            GhostBorderCard(onClick = onProponerIniciativa, modifier = Modifier.padding(top = 20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AddCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text("Proponer una iniciativa", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 8.dp))
                }
            }

            FilaEnlace(titulo = "Mi Comunidad", subtitulo = "Gestiona los miembros de tu círculo", onClick = onMiComunidad)
            FilaEnlace(titulo = "Mis Propuestas", subtitulo = "Revisa el estado de tus iniciativas", onClick = onMisPropuestas)
            FilaEnlace(titulo = "Preferencias", subtitulo = "Cómo te gustaría involucrarte", onClick = onPreferencias)

            EncabezadoSeccion(titulo = "Apariencia", modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            GhostBorderCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Fondo animado", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Formas de color moviéndose de fondo en las pantallas principales",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = fondoAnimadoActivo,
                        onCheckedChange = onCambiarFondoAnimado,
                        colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }

            EncabezadoSeccion(titulo = "Próximas Actividades", modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            SampleData.actividades.take(1).forEach { actividad ->
                GhostBorderCard(onClick = { onVerActividad(actividad.id) }) {
                    Text(actividad.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(actividad.fechaHoraCompleta, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                    UbicacionRow(texto = actividad.ubicacion)
                }
            }

            EncabezadoSeccion(titulo = "Experiencias Pasadas", modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SampleData.experiencias.forEach { experiencia ->
                    GhostBorderCard {
                        Text(experiencia.titulo, style = MaterialTheme.typography.bodyMedium)
                        Text(experiencia.fecha, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            SecondaryButton(
                text = "Publicar nueva experiencia",
                onClick = {
                    Toast.makeText(context, "Publicar experiencias todavía no está disponible: falta conectar el backend.", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(top = 12.dp)
            )

            EncabezadoSeccion(
                titulo = "Comunidad",
                onVerTodo = onMiComunidad,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                comunidad.forEach { miembro ->
                    MiembroBurbuja(miembro)
                }
            }
        }
    }
}

@Composable
private fun EncabezadoSeccion(titulo: String, onVerTodo: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        TertiaryTextButton(
            text = "Ver todo",
            onClick = {
                if (onVerTodo != null) {
                    onVerTodo()
                } else {
                    Toast.makeText(context, "Esta sección todavía no tiene una vista completa.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
private fun FilaEnlace(titulo: String, subtitulo: String, onClick: () -> Unit) {
    GhostBorderCard(onClick = onClick, modifier = Modifier.padding(top = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun MiembroBurbuja(miembro: MiembroComunidad) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(miembro.iniciales, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
        Text(miembro.nombre, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
    }
}