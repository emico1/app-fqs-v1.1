package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.FilterChip
import mx.familiasquesuman.app.ui.components.FormasFlotantesFondo
import mx.familiasquesuman.app.ui.components.PrimaryButton

private data class Interes(val etiqueta: String, val icono: ImageVector)

/**
 * Dos usos de la misma pantalla:
 * - Onboarding (primer uso, `esEdicion = false`): pide crear username y aceptar el aviso
 *   de privacidad, sin botón de regreso (viene de Registro, es un paso obligatorio).
 * - Preferencias (`esEdicion = true`, se abre desde Perfil): la misma selección de
 *   intereses y temas, pero SIN pedir username/privacidad otra vez (ya se aceptó al
 *   registrarse) y con flecha de regreso + botón "Guardar cambios".
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingPreferenciasScreen(
    onEmpezar: () -> Unit,
    esEdicion: Boolean = false,
    onBack: () -> Unit = {},
    animacionesActivas: Boolean = true
) {
    val intereses = listOf(
        Interes("Donar", Icons.Filled.VolunteerActivism),
        Interes("Participar", Icons.Filled.Diversity3),
        Interes("Proponer", Icons.Filled.Lightbulb)
    )
    var seleccionado by remember { mutableStateOf(setOf("Participar")) }
    var username by remember { mutableStateOf("") }
    // En modo edición el aviso ya se aceptó al registrarse, así que no vuelve a pedirse.
    var aceptaAviso by remember { mutableStateOf(esEdicion) }
    var temasSeleccionados by remember { mutableStateOf(setOf<String>()) }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        FormasFlotantesFondo(activo = animacionesActivas)
        Column(modifier = Modifier.fillMaxSize()) {
            if (esEdicion) {
                BackTopBar(titulo = "Preferencias", onBack = onBack)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .let {
                        // Sin BackTopBar (modo onboarding) esta pantalla es la primera cosa que
                        // se ve, así que tiene que reservar el espacio de la barra de estado ella
                        // misma; con BackTopBar ese espacio ya lo reserva la barra de arriba.
                        if (esEdicion) it else it.windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.statusBars)
                    }
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = if (esEdicion) 16.dp else 32.dp)
            ) {
                if (!esEdicion) {
                    Text(
                        "Personaliza tu experiencia",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Cuéntanos cómo te gustaría involucrarte para sugerirte las mejores oportunidades.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
                    )
                }

                Text("¿Qué te interesa más?", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 28.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    intereses.forEach { interes ->
                        val activo = seleccionado.contains(interes.etiqueta)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .selectable(
                                    selected = activo,
                                    onClick = {
                                        seleccionado = if (activo) seleccionado - interes.etiqueta else seleccionado + interes.etiqueta
                                    }
                                )
                                .background(
                                    if (activo) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceContainerLowest,
                                    MaterialTheme.shapes.medium
                                )
                                .border(
                                    1.dp,
                                    if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                    MaterialTheme.shapes.medium
                                )
                                .padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                interes.icono,
                                contentDescription = null,
                                tint = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                interes.etiqueta,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }

                if (!esEdicion) {
                    Text("Crea tu username", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        leadingIcon = { Text("@", style = MaterialTheme.typography.titleMedium) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Text(
                        "Este nombre será público en la comunidad.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )
                }

                Text(
                    "Temas de interés",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Opcional",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SampleData.temasInteres.forEach { tema ->
                        FilterChip(
                            texto = tema,
                            seleccionado = temasSeleccionados.contains(tema),
                            onClick = {
                                temasSeleccionados = if (temasSeleccionados.contains(tema)) temasSeleccionados - tema else temasSeleccionados + tema
                            }
                        )
                    }
                }

                if (!esEdicion) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 16.dp)
                            .selectable(selected = aceptaAviso, onClick = { aceptaAviso = !aceptaAviso }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = aceptaAviso, onCheckedChange = { aceptaAviso = it })
                        Text(
                            "He leído y acepto el aviso de privacidad y los términos de uso.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                PrimaryButton(
                    text = if (esEdicion) "Guardar cambios" else "Empezar",
                    enabled = aceptaAviso,
                    onClick = onEmpezar,
                    modifier = Modifier.padding(top = if (esEdicion) 24.dp else 0.dp, bottom = if (esEdicion) 16.dp else 0.dp)
                )
            }
        }
    }
}