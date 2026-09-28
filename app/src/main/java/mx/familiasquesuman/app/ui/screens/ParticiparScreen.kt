package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.ActividadResumenCard
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.CampanaResumenCard
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.TertiaryTextButton

/** Basada en participar_familias_que_suman: hub para proponer, ver voluntariados y campañas.
 * Muestra solo un adelanto de cada lista (3 voluntariados, 3 campañas); "Ver todas" manda a
 * TodosVoluntariadosScreen / TodasCampanasScreen con el listado completo. */
@Composable
fun ParticiparScreen(
    onProponerIniciativa: () -> Unit,
    onVerActividad: (String) -> Unit,
    onVerCampana: (String) -> Unit,
    onVerTodosVoluntariados: () -> Unit = {},
    onVerTodasCampanas: () -> Unit = {},
    onMenu: () -> Unit = {},
    onNotificaciones: () -> Unit = {},
    hayNotificacionesSinLeer: Boolean = false
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(titulo = "Participar", onMenu = onMenu, onNotificaciones = onNotificaciones, hayNotificacionesSinLeer = hayNotificacionesSinLeer)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            GhostBorderCard(onClick = onProponerIniciativa) {
                Icon(Icons.Filled.AddReaction, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    "Proponer una asociación o proyecto",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    "¿Conoces una causa que necesite apoyo? Ayúdanos a sumar más familias y crecer nuestra comunidad.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )
                PrimaryButton(text = "Comenzar propuesta", onClick = onProponerIniciativa)
            }

            SeccionConAccion(
                titulo = "Voluntariados Activos",
                onAccion = onVerTodosVoluntariados,
                modifier = Modifier.padding(top = 28.dp, bottom = 12.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SampleData.actividades.take(3).forEach { actividad ->
                    ActividadResumenCard(actividad = actividad, onClick = { onVerActividad(actividad.id) })
                }
            }

            SeccionConAccion(
                titulo = "Campañas de Donación",
                onAccion = onVerTodasCampanas,
                modifier = Modifier.padding(top = 28.dp, bottom = 12.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SampleData.campanas.take(3).forEach { campana ->
                    CampanaResumenCard(campana = campana, onClick = { onVerCampana(campana.id) })
                }
            }
        }
    }
}

@Composable
private fun SeccionConAccion(titulo: String, onAccion: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        TertiaryTextButton(text = "Ver todas", onClick = onAccion)
    }
}