package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import coil.compose.AsyncImage
import mx.familiasquesuman.app.data.Asociacion
import mx.familiasquesuman.app.ui.components.BackTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton
import mx.familiasquesuman.app.ui.components.StatusTag
import mx.familiasquesuman.app.ui.components.TertiaryTextButton
import mx.familiasquesuman.app.ui.components.UbicacionRow
import mx.familiasquesuman.app.ui.components.placeholderPhotoUrl
import mx.familiasquesuman.app.ui.theme.colorAcento
import androidx.compose.foundation.shape.CircleShape

/** Basada en perfil_de_asociacion_azul. */
@Composable
fun AsociacionDetalleScreen(
    asociacion: Asociacion,
    onBack: () -> Unit,
    onVerActividad: (String) -> Unit,
    onReportar: () -> Unit
) {
    val context = LocalContext.current
    var notificacionesActivas by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(titulo = asociacion.nombre, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = placeholderPhotoUrl(asociacion.fotoSeed, 800, 480),
                contentDescription = asociacion.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusTag(
                        texto = asociacion.categoria.etiqueta,
                        containerColor = asociacion.categoria.colorAcento(),
                        contentColor = asociacion.categoria.colorAcento()
                    )
                    if (asociacion.verificada) {
                        StatusTag(texto = "✓ Verificado", containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.secondary)
                    }
                }
                Text(asociacion.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                UbicacionRow(texto = asociacion.ubicacion)
                Text(
                    "Más de ${asociacion.familiasBeneficiadas} familias beneficiadas",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AccionContacto(
                        icono = Icons.Filled.Call,
                        texto = "Llamar",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${asociacion.telefono}"))
                            runCatching { context.startActivity(intent) }
                                .onFailure { Toast.makeText(context, "No se encontró una app para llamar.", Toast.LENGTH_SHORT).show() }
                        }
                    )
                    AccionContacto(
                        icono = Icons.AutoMirrored.Filled.Chat,
                        texto = "WhatsApp",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val numero = asociacion.telefono.filter { it.isDigit() }
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$numero"))
                            runCatching { context.startActivity(intent) }
                                .onFailure { Toast.makeText(context, "No se pudo abrir WhatsApp.", Toast.LENGTH_SHORT).show() }
                        }
                    )
                    AccionContacto(
                        icono = Icons.Filled.Mail,
                        texto = "Correo",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:${asociacion.correoContacto}")
                                putExtra(Intent.EXTRA_SUBJECT, "Contacto desde Familias que Suman")
                            }
                            runCatching { context.startActivity(intent) }
                                .onFailure { Toast.makeText(context, "No se encontró una app de correo.", Toast.LENGTH_SHORT).show() }
                        }
                    )
                }

                Text("Nuestra Misión", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                Text(asociacion.mision, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Text("Necesidades Actuales", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    NecesidadCard(Icons.Filled.VolunteerActivism, "Voluntarios", "Para apoyo escolar en tardes.", Modifier.weight(1f))
                    NecesidadCard(Icons.AutoMirrored.Filled.MenuBook, "Materiales", "Libros infantiles y útiles.", Modifier.weight(1f))
                }

                Text("Próximas Actividades", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 28.dp, bottom = 8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    listOf("15 Nov · Taller de Arte Familiar · 17:00 - 19:00", "22 Nov · Lectura al Aire Libre · 10:00 - 12:30").forEachIndexed { idx, texto ->
                        GhostBorderCard(onClick = { onVerActividad(if (idx == 0) "reforestacion-urbana" else "limpieza-playa") }) {
                            Text(texto, style = MaterialTheme.typography.bodyMedium)
                            TertiaryTextButton(text = "Inscribirse", onClick = { onVerActividad(if (idx == 0) "reforestacion-urbana" else "limpieza-playa") }, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }

                Column(modifier = Modifier.padding(top = 28.dp)) {
                    SecondaryButton(
                        text = if (notificacionesActivas) "Notificaciones activas ✓" else "Activar notificaciones",
                        onClick = {
                            notificacionesActivas = !notificacionesActivas
                            val mensaje = if (notificacionesActivas) {
                                "Te avisaremos de nuevas actividades de ${asociacion.nombre}."
                            } else {
                                "Notificaciones desactivadas para ${asociacion.nombre}."
                            }
                            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                        }
                    )
                    Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SecondaryButton(
                            text = "Compartir perfil",
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Conoce a ${asociacion.nombre} en Familias que Suman: ${asociacion.descripcion}")
                                }
                                runCatching { context.startActivity(Intent.createChooser(intent, "Compartir perfil")) }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    TertiaryTextButton(
                        text = "Reportar asociación",
                        onClick = onReportar,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AccionContacto(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icono, contentDescription = texto, tint = MaterialTheme.colorScheme.primary)
        }
        Text(texto, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 6.dp))
    }
}

@Composable
private fun NecesidadCard(icono: androidx.compose.ui.graphics.vector.ImageVector, titulo: String, descripcion: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f), MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f), CircleShape)
                .padding(10.dp)
        ) {
            Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
        }
        Text(titulo, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 10.dp))
        Text(descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}