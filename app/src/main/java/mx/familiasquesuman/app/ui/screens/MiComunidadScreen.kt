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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import mx.familiasquesuman.app.data.MiembroComunidad
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton

/** Basada en a_adir_miembros_de_la_comunidad: lista + formulario para añadir miembro.
 * La lista vive en AppViewModel (compartida con toda la app), así que agregar o quitar
 * un miembro aquí se refleja también, por ejemplo, en los acompañantes de una actividad. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiComunidadScreen(
    miembros: List<MiembroComunidad>,
    onAgregar: (MiembroComunidad) -> Unit,
    onEliminar: (MiembroComunidad) -> Unit,
    onBack: () -> Unit
) {
    var mostrarFormulario by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = "Mi Comunidad", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "Gestiona y administra los miembros de tu círculo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                miembros.forEach { miembro ->
                    GhostBorderCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(miembro.iniciales, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                            }
                            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                                Text(miembro.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(miembro.relacion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = { onEliminar(miembro) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            PrimaryButton(
                text = "Añadir nuevo miembro",
                onClick = { mostrarFormulario = true },
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }

    if (mostrarFormulario) {
        Dialog(onDismissRequest = { mostrarFormulario = false }) {
            NuevoMiembroFormulario(
                onCancelar = { mostrarFormulario = false },
                onGuardar = { nuevo ->
                    onAgregar(nuevo)
                    mostrarFormulario = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NuevoMiembroFormulario(
    onCancelar: () -> Unit,
    onGuardar: (MiembroComunidad) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var relacionExpandida by remember { mutableStateOf(false) }
    var relacion by remember { mutableStateOf("") }
    val relaciones = listOf("Hijo/a", "Padre/Madre", "Abuelo/a", "Otro")
    var contacto by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    GhostBorderCard {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Añadir nuevo miembro", style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onCancelar) {
                Icon(Icons.Filled.Close, contentDescription = "Cerrar")
            }
        }

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )

        ExposedDropdownMenuBox(
            expanded = relacionExpandida,
            onExpandedChange = { relacionExpandida = it },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            OutlinedTextField(
                value = relacion,
                onValueChange = {},
                readOnly = true,
                label = { Text("Relación") },
                placeholder = { Text("Selecciona una opción") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = relacionExpandida) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(expanded = relacionExpandida, onDismissRequest = { relacionExpandida = false }) {
                relaciones.forEach { r ->
                    DropdownMenuItem(text = { Text(r) }, onClick = { relacion = r; relacionExpandida = false })
                }
            }
        }

        OutlinedTextField(
            value = contacto,
            onValueChange = { contacto = it },
            label = { Text("Correo o Teléfono") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )
        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = { fechaNacimiento = it },
            label = { Text("Fecha de Nacimiento") },
            placeholder = { Text("DD/MM/AAAA") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        )

        Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SecondaryButton(text = "Cancelar", onClick = onCancelar, modifier = Modifier.weight(1f))
            PrimaryButton(
                text = "Guardar",
                enabled = nombre.isNotBlank() && relacion.isNotBlank(),
                onClick = {
                    onGuardar(
                        MiembroComunidad(
                            id = nombre.lowercase().replace(" ", "-"),
                            nombre = nombre,
                            relacion = relacion,
                            iniciales = nombre.take(1).uppercase()
                        )
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
