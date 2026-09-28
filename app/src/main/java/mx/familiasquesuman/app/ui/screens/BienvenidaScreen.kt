package mx.familiasquesuman.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mx.familiasquesuman.app.ui.components.FormasFlotantesFondo
import mx.familiasquesuman.app.ui.components.PrimaryButton
import mx.familiasquesuman.app.ui.components.SecondaryButton
import mx.familiasquesuman.app.ui.theme.CategoriaAzul
import mx.familiasquesuman.app.ui.theme.CategoriaNaranja
import mx.familiasquesuman.app.ui.theme.CategoriaVerde

/**
 * Pantalla de bienvenida / splash. No estaba como pantalla independiente en el mockup
 * (los flujos de Stitch arrancan directo en login/onboarding), pero es el punto de
 * entrada natural de la app: marca + propuesta de valor + dos caminos (entrar / crear cuenta).
 */
@Composable
fun BienvenidaScreen(
    onIniciarSesion: () -> Unit,
    onCrearCuenta: () -> Unit,
    animacionesActivas: Boolean = true
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        FormasFlotantesFondo(activo = animacionesActivas)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.statusBars)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .size(96.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Diversity3,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(
                    "Familias que Suman",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    "Conectamos familias con asociaciones verificadas para sumar tiempo, recursos e ideas a su comunidad.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.10f)
                            )
                        ),
                        MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    IlustracionCirculo(icono = Icons.Filled.Eco, color = CategoriaVerde, tamano = 64.dp)
                    IlustracionCirculo(icono = Icons.Filled.VolunteerActivism, color = CategoriaAzul, tamano = 88.dp, modifier = Modifier.padding(top = 0.dp))
                    IlustracionCirculo(icono = Icons.Filled.Lightbulb, color = CategoriaNaranja, tamano = 64.dp)
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(text = "Crear cuenta", onClick = onCrearCuenta)
                SecondaryButton(
                    text = "Ya tengo cuenta",
                    onClick = onIniciarSesion,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

/** Círculo de color con ícono, usado para armar la pequeña "ilustración" de bienvenida
 * (reemplaza el placeholder de texto que quedaba plano y con look de borrador). */
@Composable
private fun IlustracionCirculo(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color,
    tamano: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(tamano)
            .background(color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icono,
            contentDescription = null,
            tint = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier.size(tamano / 2)
        )
    }
}