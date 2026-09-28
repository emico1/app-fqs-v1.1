package mx.familiasquesuman.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.ui.nav.Routes
import mx.familiasquesuman.app.ui.nav.TabDestino

/** Contenido del menú lateral (☰): las 4 pestañas principales + Cerrar sesión. */
@Composable
fun AppDrawerContent(
    rutaActual: String?,
    onNavegar: (String) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        Text(
            "Familias que Suman",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

        val items = listOf(
            Triple(TabDestino.INICIO.ruta, "Inicio", Icons.Filled.Home),
            Triple(TabDestino.EXPLORAR.ruta, "Explorar", Icons.Filled.Search),
            Triple(TabDestino.PARTICIPAR.ruta, "Participar", Icons.Filled.VolunteerActivism),
            Triple(TabDestino.PERFIL.ruta, "Perfil", Icons.Filled.Person)
        )
        items.forEach { (ruta, etiqueta, icono) ->
            NavigationDrawerItem(
                label = { Text(etiqueta) },
                selected = rutaActual == ruta,
                icon = { Icon(icono, contentDescription = null) },
                onClick = { onNavegar(ruta) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            selected = false,
            icon = { Icon(Icons.Filled.ExitToApp, contentDescription = null) },
            onClick = onCerrarSesion,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
