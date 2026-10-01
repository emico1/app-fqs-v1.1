package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.PrimaryButton

private data class TipoPropuesta(val etiqueta: String, val icono: ImageVector)

/** Basada en proponer_iniciativa_azul_2 (Paso 1 de 3: información básica). */
@Composable
fun ProponerIniciativaScreen(
    onEnviar: (titulo: String, descripcion: String) -> Unit,
    onBack: () -> Unit
) {
    val tipos = listOf(
        TipoPropuesta("Actividad", Icons.Filled.Event),
        TipoPropuesta("Iniciativa vecinal", Icons.Filled.Groups),
        TipoPropuesta("Asociación", Icons.Filled.Apartment)
    )
    var tipoSeleccionado by remember { mutableStateOf(tipos.first().etiqueta) }
    var nombreIniciativa by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(
            titulo = "Proponer Iniciativa",
            onBack = onBack
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text("Nueva Iniciativa", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 6.dp))
            }
            Text("Proponer Iniciativa", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
            Text(
                "Comparte tu idea para mejorar la comunidad. Nuestro equipo la revisará para ayudar a hacerla realidad.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            Text("Paso 1 de 3", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            LinearProgressIndicator(
                progress = { 1f / 3f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 20.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
            )

            Text("Información Básica", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 12.dp))
            Text("Tipo de propuesta", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                tipos.forEach { tipo ->
                    val activo = tipoSeleccionado == tipo.etiqueta
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .selectable(selected = activo, onClick = { tipoSeleccionado = tipo.etiqueta })
                            .background(
                                if (activo) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceContainerLowest,
                                MaterialTheme.shapes.medium
                            )
                            .border(
                                1.dp,
                                if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                MaterialTheme.shapes.medium
                            )
                            .padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(tipo.icono, contentDescription = null, tint = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            tipo.etiqueta,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = nombreIniciativa,
                onValueChange = { nombreIniciativa = it },
                label = { Text("Nombre de la iniciativa") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción breve") },
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            OutlinedTextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text("Ciudad / Ubicación") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )

            Text("Datos de contacto", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 20.dp, bottom = 8.dp))
            OutlinedTextField(
                value = contacto,
                onValueChange = { contacto = it },
                label = { Text("Correo") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            )

            PrimaryButton(
                text = "Continuar",
                enabled = nombreIniciativa.isNotBlank() && descripcion.isNotBlank() && ciudad.isNotBlank() && contacto.isNotBlank(),
                onClick = { onEnviar(nombreIniciativa, descripcion) },
                modifier = Modifier.padding(top = 24.dp)
            )

            val context = androidx.compose.ui.platform.LocalContext.current
            Text(
                "¿Necesitas ayuda? Contactar soporte",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .clickable {
                        val intent = android.content.Intent(android.content.Intent.ACTION_SENDTO).apply {
                            data = android.net.Uri.parse("mailto:soporte@familiasquesuman.mx")
                            putExtra(android.content.Intent.EXTRA_SUBJECT, "Ayuda con una propuesta")
                        }
                        runCatching { context.startActivity(intent) }
                    }
            )
        }
    }
}
