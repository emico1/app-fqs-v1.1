package mx.familiasquesuman.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import mx.familiasquesuman.app.data.EstadoPropuesta
import mx.familiasquesuman.app.data.MiembroComunidad
import mx.familiasquesuman.app.data.Propuesta
import mx.familiasquesuman.app.data.SampleData

/**
 * Estado en memoria compartido entre pantallas (vive mientras la app esté abierta,
 * se pierde al cerrarla — todavía no hay backend que lo persista de verdad).
 *
 * Sin esto, cada pantalla tenía su propio `remember { mutableStateOf(...) }`, así que
 * agregar un miembro en Mi Comunidad o enviar una propuesta no se reflejaba en ningún
 * otro lado (se "olvidaba" al navegar). Con un ViewModel a nivel de la Activity, todas
 * las pantallas leen y escriben la misma lista.
 *
 * Es AndroidViewModel (en vez de ViewModel a secas) únicamente para tener Context y poder
 * guardar la preferencia de "fondo animado" en SharedPreferences — esa sí sobrevive a cerrar
 * la app, a diferencia del resto del estado de aquí arriba.
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("familias_que_suman_prefs", Context.MODE_PRIVATE)

    /** Si las formas de color de fondo se animan/muestran. Empieza en true; el usuario lo
     * puede apagar desde Perfil > Preferencias visuales, y queda guardado entre sesiones. */
    var fondoAnimadoActivo by mutableStateOf(prefs.getBoolean(CLAVE_FONDO_ANIMADO, true))
        private set

    fun cambiarFondoAnimado(activo: Boolean) {
        fondoAnimadoActivo = activo
        prefs.edit().putBoolean(CLAVE_FONDO_ANIMADO, activo).apply()
    }

    val comunidad = mutableStateListOf(*SampleData.miembrosComunidad.toTypedArray())

    fun agregarMiembro(miembro: MiembroComunidad) {
        comunidad.add(miembro)
    }

    fun eliminarMiembro(miembro: MiembroComunidad) {
        comunidad.remove(miembro)
    }

    val propuestas = mutableStateListOf(*SampleData.propuestas.toTypedArray())

    /** Se llama al enviar el formulario de "Proponer Iniciativa": agrega la propuesta real
     * (con estado inicial "En revisión") al principio de la lista que ve Mis Propuestas. */
    fun agregarPropuesta(titulo: String, descripcion: String): Propuesta {
        val nueva = Propuesta(
            id = "propuesta-${propuestas.size + 1}-${System.currentTimeMillis()}",
            titulo = titulo,
            descripcion = descripcion,
            fecha = "Recién enviada",
            estado = EstadoPropuesta.EN_REVISION
        )
        propuestas.add(0, nueva)
        return nueva
    }

    fun propuestaPorId(id: String?): Propuesta =
        propuestas.firstOrNull { it.id == id } ?: propuestas.first()

    /** Notificaciones de muestra; "leída" se puede togglear localmente. */
    val notificaciones = mutableStateListOf(
        Notificacion("n1", "Tu propuesta fue recibida", "\"Limpieza del Parque Central\" está en revisión.", leida = false),
        Notificacion("n2", "Nueva actividad cerca de ti", "Jornada de Reforestación Urbana el sábado 24 Oct.", leida = false),
        Notificacion("n3", "Recordatorio", "Tienes una inscripción confirmada para mañana.", leida = true)
    )

    fun marcarLeida(id: String) {
        val idx = notificaciones.indexOfFirst { it.id == id }
        if (idx >= 0) notificaciones[idx] = notificaciones[idx].copy(leida = true)
    }

    /** Se llama al deslizar una notificación en NotificacionesScreen para descartarla. */
    fun eliminarNotificacion(id: String) {
        notificaciones.removeAll { it.id == id }
    }

    val hayNotificacionesSinLeer: Boolean
        get() = notificaciones.any { !it.leida }

    companion object {
        private const val CLAVE_FONDO_ANIMADO = "fondo_animado_activo"
    }
}

data class Notificacion(
    val id: String,
    val titulo: String,
    val cuerpo: String,
    val leida: Boolean
)