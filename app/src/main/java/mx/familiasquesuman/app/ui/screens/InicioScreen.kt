package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.data.ActividadItem
import mx.familiasquesuman.app.data.Campana
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.AsociacionDestacadaCard
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.TertiaryTextButton
import mx.familiasquesuman.app.ui.components.UbicacionRow

/** Basada en inicio_familias_que_suman_azul. */
@Composable
fun InicioScreen(
    onVerAsociacion: (String) -> Unit,
    onVerActividad: (String) -> Unit,
    onVerTodasAsociaciones: () -> Unit,
    onVerCampana: (String) -> Unit,
    onMenu: () -> Unit = {},
    onNotificaciones: () -> Unit = {},
    hayNotificacionesSinLeer: Boolean = false
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(onMenu = onMenu, onNotificaciones = onNotificaciones, hayNotificacionesSinLeer = hayNotificacionesSinLeer)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            SeccionHeader(
                titulo = "Asociaciones Recomendadas",
                accion = "Ver todas",
                onAccion = onVerTodasAsociaciones
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp)
            ) {
                items(SampleData.asociaciones.take(3)) { asociacion ->
                    AsociacionDestacadaCard(asociacion = asociacion, onClick = { onVerAsociacion(asociacion.id) })
                }
            }

            SeccionHeader(
                titulo = "Actividades Próximas",
                accion = "Ver calendario",
                onAccion = {
                    android.widget.Toast.makeText(context, "El calendario completo todavía no está disponible.", android.widget.Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(top = 32.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SampleData.actividades.take(2).forEach { actividad ->
                    ActividadFilaCard(actividad = actividad, onClick = { onVerActividad(actividad.id) })
                }
            }

            SeccionHeader(
                titulo = "Campañas Activas",
                accion = null,
                onAccion = {},
                modifier = Modifier.padding(top = 32.dp)
            )
            CampanaCard(campana = SampleData.campanas.first(), onClick = { onVerCampana(SampleData.campanas.first().id) })

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SeccionHeader(
    titulo: String,
    accion: String?,
    onAccion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        if (accion != null) {
            TertiaryTextButton(text = accion, onClick = onAccion)
        }
    }
}

@Composable
private fun ActividadFilaCard(actividad: ActividadItem, onClick: () -> Unit) {
    GhostBorderCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.medium)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(actividad.dia, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold)
                Text(actividad.mes, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onTertiary)
            }
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(actividad.titulo, style = MaterialTheme.typography.titleMedium)
                Text(actividad.etiquetaAudiencia, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                UbicacionRow(texto = actividad.ubicacion)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun CampanaCard(campana: Campana, onClick: () -> Unit) {
    GhostBorderCard(onClick = onClick) {
        Text(campana.titulo, style = MaterialTheme.typography.titleMedium)
        Text(
            campana.descripcion,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )
        LinearProgressIndicator(
            progress = { campana.progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh, MaterialTheme.shapes.extraLarge),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = androidx.compose.ui.graphics.Color.Transparent,
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(campana.recaudadoTexto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(campana.metaTexto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(text = "Donar", onClick = onClick, modifier = Modifier.weight(1f))
            val context = androidx.compose.ui.platform.LocalContext.current
            IconButton(onClick = {
                val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_TEXT, "Apoya la campaña \"${campana.titulo}\" en Familias que Suman: ${campana.descripcion}")
                }
                runCatching { context.startActivity(android.content.Intent.createChooser(intent, "Compartir campaña")) }
            }) {
                Icon(Icons.Filled.Share, contentDescription = "Compartir")
            }
        }
    }
}