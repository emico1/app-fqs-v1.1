package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.data.Asociacion
import mx.familiasquesuman.app.data.Categoria
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.CategoriaBadge
import mx.familiasquesuman.app.ui.components.CategoriaEtiquetaConColor
import mx.familiasquesuman.app.ui.components.FilterChip
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.UbicacionRow

/** Basada en explorar_familias_que_suman_azul (con el filtro de ciudad ya integrado). */
@Composable
fun ExplorarScreen(
    onVerAsociacion: (String) -> Unit,
    onMenu: () -> Unit = {},
    onNotificaciones: () -> Unit = {},
    hayNotificacionesSinLeer: Boolean = false
) {
    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }

    val asociacionesFiltradas = SampleData.asociaciones.filter {
        (categoriaSeleccionada == null || it.categoria == categoriaSeleccionada) &&
                (busqueda.isBlank() || it.nombre.contains(busqueda, ignoreCase = true))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(onMenu = onMenu, onNotificaciones = onNotificaciones, hayNotificacionesSinLeer = hayNotificacionesSinLeer)
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
            Text("Descubre oportunidades", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)

            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar asociaciones o actividades") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                Icon(Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    " Monterrey · Cerca de ti",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Chips de categoría en una fila con scroll horizontal: con 6 categorías + "Todos"
        // no caben en el ancho de un teléfono, así que aquí sí necesitan poder desplazarse.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(texto = "Todos", seleccionado = categoriaSeleccionada == null, onClick = { categoriaSeleccionada = null })
            Categoria.entries.forEach { cat ->
                FilterChip(
                    texto = cat.etiqueta,
                    icono = cat.icono,
                    seleccionado = categoriaSeleccionada == cat,
                    onClick = { categoriaSeleccionada = if (categoriaSeleccionada == cat) null else cat }
                )
            }
        }

        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(asociacionesFiltradas) { asociacion ->
                AsociacionListaCard(asociacion = asociacion, onClick = { onVerAsociacion(asociacion.id) })
            }
        }
    }
}

@Composable
private fun AsociacionListaCard(asociacion: Asociacion, onClick: () -> Unit) {
    GhostBorderCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.Top) {
            CategoriaBadge(categoria = asociacion.categoria, tamano = 44.dp)
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                CategoriaEtiquetaConColor(categoria = asociacion.categoria)
                Text(
                    asociacion.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    asociacion.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )
                UbicacionRow(texto = asociacion.ubicacion)
            }
        }
    }
}