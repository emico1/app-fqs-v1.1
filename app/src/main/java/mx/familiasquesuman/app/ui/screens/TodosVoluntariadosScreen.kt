package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.ActividadResumenCard
import mx.familiasquesuman.app.ui.components.BackTopBar

/** Lista completa de voluntariados/actividades activas — a la que llega el "Ver todas" de
 * la sección "Voluntariados Activos" en Participar. */
@Composable
fun TodosVoluntariadosScreen(
    onBack: () -> Unit,
    onVerActividad: (String) -> Unit
) {
    androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = "Voluntariados Activos", onBack = onBack)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(SampleData.actividades, key = { it.id }) { actividad ->
                ActividadResumenCard(actividad = actividad, onClick = { onVerActividad(actividad.id) })
            }
        }
    }
}