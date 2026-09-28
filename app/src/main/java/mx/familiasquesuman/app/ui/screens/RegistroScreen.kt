package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.FormasFlotantesFondo
import mx.familiasquesuman.app.ui.components.PrimaryButton

/** Basada en los campos de registro de inicio_con_registro_directo_azul. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onRegistrarse: () -> Unit,
    onBack: () -> Unit,
    animacionesActivas: Boolean = true
) {
    var nombre by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var ciudadExpandida by remember { mutableStateOf(false) }
    var ciudad by remember { mutableStateOf("") }
    val ciudades = listOf("Monterrey", "Guadalajara", "Ciudad de México")
    var tipoCuenta by remember { mutableStateOf("Familiar") }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        FormasFlotantesFondo(activo = animacionesActivas)
        Column(modifier = Modifier.fillMaxSize()) {
            BackTopBar(titulo = "Crea tu cuenta", onBack = onBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = contacto,
                    onValueChange = { contacto = it },
                    label = { Text("Correo o teléfono") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = ciudadExpandida,
                    onExpandedChange = { ciudadExpandida = it },
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    OutlinedTextField(
                        value = ciudad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ciudad") },
                        placeholder = { Text("Selecciona tu ciudad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ciudadExpandida) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = ciudadExpandida, onDismissRequest = { ciudadExpandida = false }) {
                        ciudades.forEach { c ->
                            DropdownMenuItem(text = { Text(c) }, onClick = { ciudad = c; ciudadExpandida = false })
                        }
                    }
                }

                Text(
                    "Tipo de cuenta",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
                listOf("Familiar", "Individual").forEach { opcion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = tipoCuenta == opcion,
                                onClick = { tipoCuenta = opcion }
                            ),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoCuenta == opcion,
                            onClick = { tipoCuenta = opcion }
                        )
                        Text(opcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                PrimaryButton(
                    text = "Registrarse",
                    enabled = nombre.isNotBlank() && contacto.isNotBlank() && ciudad.isNotBlank(),
                    onClick = onRegistrarse,
                    modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                )
            }
        }
    }
}