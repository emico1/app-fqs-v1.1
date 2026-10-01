package mx.familiasquesuman.app.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import mx.familiasquesuman.app.data.MiembroComunidad
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.BrandTopBar
import mx.familiasquesuman.app.ui.components.GhostBorderCard
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton
import mx.familiasquesuman.app.ui.components.TertiaryTextButton
import mx.familiasquesuman.app.ui.components.UbicacionRow

@Composable
fun PerfilScreen(
    comunidad: List<MiembroComunidad>,
    fondoAnimadoActivo: Boolean = true,
    onCambiarFondoAnimado: (Boolean) -> Unit = {},
    onProponerIniciativa: () -> Unit,
    onMiComunidad: () -> Unit,
    onMisPropuestas: () -> Unit,
    onPreferencias: () -> Unit,
    onVerActividad: (String) -> Unit,
    onMenu: () -> Unit = {},
    onNotificaciones: () -> Unit = {},
    hayNotificacionesSinLeer: Boolean = false
) {


    val context = LocalContext.current

    val preferencias = remember(context) {
        context.getSharedPreferences(
            "perfil_local",
            Context.MODE_PRIVATE
        )
    }

    var nombrePerfil by remember {
        mutableStateOf(
            preferencias.getString("nombre", "Familia López")
                ?: "Familia López"
        )
    }

    var fotoPerfil by remember {
        mutableStateOf(preferencias.getString("foto", null))
    }

    var mostrarEditarPerfil by remember { mutableStateOf(false) }
    var nombreEditado by remember { mutableStateOf(nombrePerfil) }

    val selectorFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                fotoPerfil = uri.toString()
                preferencias.edit()
                    .putString("foto", fotoPerfil)
                    .apply()
            } catch (_: SecurityException) {
                Toast.makeText(
                    context,
                    "No se pudo guardar el acceso a la foto. Intenta con otra imagen.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    if (mostrarEditarPerfil) {
        AlertDialog(
            onDismissRequest = {
                mostrarEditarPerfil = false
            },
            title = {
                Text("Editar perfil")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombreEditado,
                        onValueChange = { nombreEditado = it },
                        label = { Text("Nombre del perfil") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    SecondaryButton(
                        text = "Cambiar foto",
                        onClick = {
                            selectorFoto.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    )

                    Text(
                        text = "La foto se actualiza al seleccionarla. Para guardar el nombre, pulsa Guardar.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = nombreEditado.isNotBlank(),
                    onClick = {
                        nombrePerfil = nombreEditado.trim()

                        preferencias.edit()
                            .putString("nombre", nombrePerfil)
                            .apply()

                        mostrarEditarPerfil = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarEditarPerfil = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }



    Column(modifier = Modifier.fillMaxSize()) {
        BrandTopBar(
            titulo = "Perfil",
            onMenu = onMenu,
            onNotificaciones = onNotificaciones,
            hayNotificacionesSinLeer = hayNotificacionesSinLeer
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable(
                            onClickLabel = "Cambiar foto de perfil"
                        ) {
                            selectorFoto.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (fotoPerfil != null) {
                        AsyncImage(
                            model = Uri.parse(fotoPerfil),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(
                            text = nombrePerfil.trim()
                                .split(Regex("\\s+"))
                                .take(2)
                                .mapNotNull {
                                    it.firstOrNull()?.uppercaseChar()
                                }
                                .joinToString(""),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = nombrePerfil,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "Verificado",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .size(20.dp)
                    )
                }

                Text(
                    text = "Nivel: Embajadores Comunitarios",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                TextButton(
                    onClick = {
                        nombreEditado = nombrePerfil
                        mostrarEditarPerfil = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = "Editar perfil",
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "5x Naturaleza",
                    "Cuidado Mayor",
                    "Comedores"
                ).forEach { insignia ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer
                                    .copy(alpha = 0.5f),
                                MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 6.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = insignia,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }


            Surface(
                onClick = onMiComunidad,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.5f
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Mi Comunidad",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Gestiona los miembros de tu círculo",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            PrimaryButton(
                text = "Proponer una iniciativa",
                onClick = onProponerIniciativa,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            FilaEnlace(
                titulo = "Mis Propuestas",
                subtitulo = "Revisa el estado de tus iniciativas",
                onClick = onMisPropuestas
            )

            FilaEnlace(
                titulo = "Preferencias",
                subtitulo = "Cómo te gustaría involucrarte",
                onClick = onPreferencias
            )


            EncabezadoSeccion(
                titulo = "Apariencia",
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            GhostBorderCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Fondo animado",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Formas de color moviéndose de fondo en las pantallas principales",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = fondoAnimadoActivo,
                        onCheckedChange = onCambiarFondoAnimado,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }


            EncabezadoSeccion(
                titulo = "Próximas Actividades",
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            SampleData.actividades.take(1).forEach { actividad ->
                GhostBorderCard(
                    onClick = { onVerActividad(actividad.id) }
                ) {
                    Text(
                        text = actividad.titulo,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = actividad.fechaHoraCompleta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    UbicacionRow(texto = actividad.ubicacion)
                }
            }


            EncabezadoSeccion(
                titulo = "Experiencias Pasadas",
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SampleData.experiencias.forEach { experiencia ->
                    GhostBorderCard {
                        Text(
                            text = experiencia.titulo,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = experiencia.fecha,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            SecondaryButton(
                text = "Publicar nueva experiencia",
                onClick = {
                    Toast.makeText(
                        context,
                        "Publicar experiencias todavía no está disponible: falta conectar el backend.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.padding(top = 12.dp)
            )

            // 11. MIEMBROS DE LA COMUNIDAD

            EncabezadoSeccion(
                titulo = "Comunidad",
                onVerTodo = onMiComunidad,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                comunidad.forEach { miembro ->
                    MiembroBurbuja(miembro)
                }
            }
        }
    }
}


@Composable
private fun EncabezadoSeccion(
    titulo: String,
    onVerTodo: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        TertiaryTextButton(
            text = "Ver todo",
            onClick = {
                if (onVerTodo != null) {
                    onVerTodo()
                } else {
                    Toast.makeText(
                        context,
                        "Esta sección todavía no tiene una vista completa.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}

@Composable
private fun FilaEnlace(
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit
) {
    GhostBorderCard(
        onClick = onClick,
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MiembroBurbuja(
    miembro: MiembroComunidad
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = miembro.iniciales,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = miembro.nombre,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}