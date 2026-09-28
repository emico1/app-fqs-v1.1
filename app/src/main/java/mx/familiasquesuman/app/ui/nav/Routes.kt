package mx.familiasquesuman.app.ui.nav

/** Todas las rutas de navegación de la app en un solo lugar. */
object Routes {
    const val BIENVENIDA = "bienvenida"
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val ONBOARDING_PREFERENCIAS = "onboarding_preferencias"
    const val PREFERENCIAS = "preferencias"

    const val INICIO = "inicio"
    const val EXPLORAR = "explorar"
    const val PARTICIPAR = "participar"
    const val PERFIL = "perfil"

    const val ASOCIACION_DETALLE = "asociacion/{asociacionId}"
    fun asociacionDetalle(id: String) = "asociacion/$id"

    const val ACTIVIDAD_DETALLE = "actividad/{actividadId}"
    fun actividadDetalle(id: String) = "actividad/$id"

    const val MI_COMUNIDAD = "mi_comunidad"
    const val PROPONER_INICIATIVA = "proponer_iniciativa"
    const val PROPUESTA_ENVIADA = "propuesta_enviada"
    const val MIS_PROPUESTAS = "mis_propuestas"

    const val ESTADO_PROPUESTA = "estado_propuesta/{propuestaId}"
    fun estadoPropuesta(id: String) = "estado_propuesta/$id"

    const val CAMPANA_DONACION = "campana_donacion"
    const val REPORTAR_EXPERIENCIA = "reportar_experiencia"
    const val NOTIFICACIONES = "notificaciones"

    const val TODOS_VOLUNTARIADOS = "todos_voluntariados"
    const val TODAS_CAMPANAS = "todas_campanas"
}

/** Pestañas de la barra de navegación inferior. */
enum class TabDestino(val ruta: String, val etiqueta: String) {
    INICIO(Routes.INICIO, "Inicio"),
    EXPLORAR(Routes.EXPLORAR, "Explorar"),
    PARTICIPAR(Routes.PARTICIPAR, "Participar"),
    PERFIL(Routes.PERFIL, "Perfil")
}