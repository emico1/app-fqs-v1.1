package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton

/** Basada en reportar_mala_experiencia. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportarExperienciaScreen(
    onBack: () -> Unit,
    onEnviar: () -> Unit
) {
    var tipoExpandido by remember { mutableStateOf(false) }
    var tipoProblema by remember { mutableStateOf("") }
    val tipos = listOf("Comportamiento inapropiado", "Problema con una actividad", "Problema de la aplicación", "Otro")
    var nivelSeleccionado by remember { mutableStateOf(2) }
    val niveles = listOf(
        Icons.Filled.SentimentVeryDissatisfied,
        Icons.Filled.SentimentDissatisfied,
        Icons.Filled.SentimentNeutral,
        Icons.Filled.SentimentSatisfied,
        Icons.Filled.SentimentVerySatisfied
    )
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val selectorFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> fotoUri = uri }

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = "Reportar Experiencia", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f), MaterialTheme.shapes.medium)
                    .padding(12.dp)
            ) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text("Tu opinión nos ayuda a mejorar", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Lamentamos que hayas tenido una mala experiencia. Detallar lo sucedido nos permite mantener la calidad y seguridad de la comunidad.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text("Tipo de problema", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            ExposedDropdownMenuBox(expanded = tipoExpandido, onExpandedChange = { tipoExpandido = it }) {
                OutlinedTextField(
                    value = tipoProblema,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecciona una opción...") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpandido) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(expanded = tipoExpandido, onDismissRequest = { tipoExpandido = false }) {
                    tipos.forEach { t ->
                        DropdownMenuItem(text = { Text(t) }, onClick = { tipoProblema = t; tipoExpandido = false })
                    }
                }
            }

            Text("Nivel de insatisfacción", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp, bottom = 12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                niveles.forEachIndexed { index, icono ->
                    NivelSentimiento(
                        icono = icono,
                        seleccionado = nivelSeleccionado == index,
                        onClick = { nivelSeleccionado = index }
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Muy mala", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Neutral", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("", style = MaterialTheme.typography.labelSmall)
            }

            Text("Describe lo sucedido *", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                minLines = 4,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Adjuntar foto o evidencia (Opcional)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp, bottom = 8.dp))
            SecondaryButton(
                text = if (fotoUri == null) "Subir archivo" else "Cambiar archivo",
                onClick = {
                    selectorFoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            )
            fotoUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Foto adjunta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh, MaterialTheme.shapes.medium)
                )
            }
            Text(
                "PNG, JPG, max 5MB",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            PrimaryButton(
                text = "Enviar Reporte",
                enabled = tipoProblema.isNotBlank() && descripcion.isNotBlank(),
                onClick = {
                    Toast.makeText(context, "Gracias, tu reporte fue enviado a nuestro equipo.", Toast.LENGTH_LONG).show()
                    onEnviar()
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun NivelSentimiento(icono: ImageVector, seleccionado: Boolean, onClick: () -> Unit) {
    Icon(
        icono,
        contentDescription = null,
        tint = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .size(32.dp)
            .selectable(selected = seleccionado, onClick = onClick)
    )
}
