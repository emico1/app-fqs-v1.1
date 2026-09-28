package mx.familiasquesuman.app.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pets
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Todo lo de este archivo es DATA DE MUESTRA (hardcodeada) para poder navegar y ver
 * la interfaz completa sin backend todavía. Cuando conectemos la API, esto se
 * reemplaza por los modelos reales / respuestas de Retrofit.
 */

enum class Categoria(val etiqueta: String, val icono: ImageVector) {
    MEDIO_AMBIENTE("Medio Ambiente", Icons.Filled.Eco),
    ADULTOS_MAYORES("Adultos Mayores", Icons.Filled.Elderly),
    NINOS("Niños", Icons.Filled.ChildCare),
    MASCOTAS("Mascotas", Icons.Filled.Pets),
    ARTE_CULTURA("Arte y Cultura", Icons.Filled.Palette),
    COMUNIDAD("Comunidad", Icons.Filled.Diversity3)
}

data class Asociacion(
    val id: String,
    val nombre: String,
    val categoria: Categoria,
    val descripcion: String,
    val ubicacion: String,
    val verificada: Boolean = true,
    val familiasBeneficiadas: Int = 0,
    val mision: String = "",
    val fotoSeed: String,
    // Datos de contacto de muestra (todavía no vienen de un backend real), usados para
    // que "Llamar" / "WhatsApp" / "Correo" abran apps reales del teléfono con estos datos.
    val telefono: String = "+52 81 1234 5678",
    val correo: String = "",
    val latitud: Double = 25.6866,
    val longitud: Double = -100.3161
) {
    val correoContacto: String get() = correo.ifBlank { "contacto@$id.org" }
}

data class ActividadItem(
    val id: String,
    val titulo: String,
    val asociacion: String,
    val dia: String,
    val mes: String,
    val fechaHoraCompleta: String,
    val ubicacion: String,
    val etiquetaAudiencia: String,
    val descripcionCorta: String,
    val descripcionLarga: String,
    val recomendaciones: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int
)

data class Campana(
    val id: String,
    val titulo: String,
    val organizacion: String,
    val descripcion: String,
    val recaudadoTexto: String,
    val metaTexto: String,
    val progreso: Float,
    val urgente: Boolean = false,
    val diasRestantes: Int? = null
)

data class ArticuloDonacion(
    val nombre: String,
    val descripcion: String,
    val prioridad: String,
    val progresoTexto: String,
    val progreso: Float
)

data class MiembroComunidad(
    val id: String,
    val nombre: String,
    val relacion: String,
    val iniciales: String
)

data class Propuesta(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val estado: EstadoPropuesta
)

enum class EstadoPropuesta(val etiqueta: String) {
    EN_REVISION("En revisión"),
    APROBADA("Aprobada"),
    NECESITA_CAMBIOS("Necesita cambios"),
    COMPLETADA("Completada")
}

data class Experiencia(
    val titulo: String,
    val fecha: String
)

object SampleData {

    val asociaciones = listOf(
        Asociacion(
            id = "raices-vivas",
            nombre = "Raíces Vivas",
            categoria = Categoria.MEDIO_AMBIENTE,
            descripcion = "Reforestación urbana y talleres de huertos para todas las edades.",
            ubicacion = "Parque Metropolitano",
            familiasBeneficiadas = 50,
            mision = "Reforestemos Juntos: únete a nuestra jornada familiar para plantar árboles nativos y aprender sobre el cuidado del ecosistema local.",
            fotoSeed = "raices-vivas"
        ),
        Asociacion(
            id = "generaciones-unidas",
            nombre = "Generaciones Unidas",
            categoria = Categoria.ADULTOS_MAYORES,
            descripcion = "Acompañamiento y actividades recreativas en residencias de ancianos.",
            ubicacion = "Centro 'Luz de Vida'",
            familiasBeneficiadas = 35,
            mision = "Tardes de Cuentos: acompaña a nuestros abuelos compartiendo historias y creando lazos intergeneracionales llenos de sabiduría.",
            fotoSeed = "generaciones-unidas"
        ),
        Asociacion(
            id = "colores-de-barrio",
            nombre = "Colores de Barrio",
            categoria = Categoria.ARTE_CULTURA,
            descripcion = "Recuperación de espacios públicos a través del arte participativo.",
            ubicacion = "Plaza Central",
            familiasBeneficiadas = 40,
            mision = "Murales Comunitarios: participa en la revitalización de espacios públicos a través del arte colaborativo. Materiales incluidos.",
            fotoSeed = "colores-de-barrio"
        ),
        Asociacion(
            id = "refugio-esperanza",
            nombre = "Refugio Esperanza",
            categoria = Categoria.MASCOTAS,
            descripcion = "Paseo de Peludos: brinda amor y ejercicio a perros rescatados.",
            ubicacion = "Refugio Esperanza",
            familiasBeneficiadas = 20,
            mision = "Una actividad ideal para que los niños aprendan empatía animal cuidando a perros rescatados.",
            fotoSeed = "refugio-esperanza"
        ),
        Asociacion(
            id = "crecer-juntos",
            nombre = "Fundación Crecer Juntos",
            categoria = Categoria.NINOS,
            descripcion = "Desarrollo Infantil: espacio seguro para actividades educativas y apoyo psicosocial.",
            ubicacion = "Centro Comunitario Norte, Ciudad",
            verificada = true,
            familiasBeneficiadas = 50,
            mision = "Brindamos un espacio seguro y enriquecedor donde niños y familias pueden participar en actividades educativas, talleres creativos y apoyo psicosocial. Creemos que el fortalecimiento del vínculo familiar es la base para una comunidad próspera y resiliente.",
            fotoSeed = "crecer-juntos"
        )
    )

    val actividades = listOf(
        ActividadItem(
            id = "reforestacion-urbana",
            titulo = "Jornada de Reforestación Urbana",
            asociacion = "Raíces Vivas",
            dia = "24",
            mes = "Oct",
            fechaHoraCompleta = "Sáb, 24 Oct • 10:00 - 13:00",
            ubicacion = "Parque Los Andes, CABA",
            etiquetaAudiencia = "Apta para niños",
            descripcionCorta = "Plantación de especies nativas en familia.",
            descripcionLarga = "Únete a nosotros en esta jornada especial para reverdecer nuestra ciudad. Plantaremos especies nativas para fomentar la biodiversidad local. Es una excelente oportunidad para enseñar a los más pequeños sobre el cuidado del medio ambiente en un entorno seguro y divertido.",
            recomendaciones = "Proporcionaremos todas las herramientas necesarias, guantes y refrigerios. Solo necesitas traer ropa cómoda que se pueda ensuciar, protector solar y muchas ganas de colaborar.",
            cuposDisponibles = 5,
            cuposTotales = 15
        ),
        ActividadItem(
            id = "limpieza-playa",
            titulo = "Limpieza de Playa",
            asociacion = "Colores de Barrio",
            dia = "12",
            mes = "Oct",
            fechaHoraCompleta = "Dom, 12 Oct • 09:00 - 12:00",
            ubicacion = "Playa del Carmen Sur",
            etiquetaAudiencia = "Apta niños",
            descripcionCorta = "Recolección de residuos en la costa.",
            descripcionLarga = "Actividad familiar de limpieza costera para cuidar los ecosistemas marinos y crear conciencia ambiental en los más pequeños.",
            recomendaciones = "Trae protector solar, gorra y agua. Nosotros ponemos guantes y bolsas.",
            cuposDisponibles = 8,
            cuposTotales = 20
        ),
        ActividadItem(
            id = "clasificacion-alimentos",
            titulo = "Clasificación de Alimentos",
            asociacion = "Generaciones Unidas",
            dia = "15",
            mes = "Oct",
            fechaHoraCompleta = "Mié, 15 Oct • 16:00 - 18:00",
            ubicacion = "Banco de Alimentos Central",
            etiquetaAudiencia = "+12 años",
            descripcionCorta = "Apoyo en la organización de donativos.",
            descripcionLarga = "Ayuda a clasificar y empacar alimentos no perecederos que serán entregados a familias en situación vulnerable.",
            recomendaciones = "Actividad recomendada para mayores de 12 años. Usa ropa cómoda.",
            cuposDisponibles = 10,
            cuposTotales = 25
        )
    )

    val campanas = listOf(
        Campana(
            id = "mochilas-suenos",
            titulo = "Mochilas Llenas de Sueños",
            organizacion = "Colores de Barrio",
            descripcion = "Equipando a 500 niños para el regreso a clases en zonas rurales.",
            recaudadoTexto = "Recaudado: $3,200",
            metaTexto = "Meta: $5,000",
            progreso = 0.64f
        ),
        Campana(
            id = "abrigos-invierno",
            titulo = "Abrigos para el Invierno",
            organizacion = "Asilo de Ancianos San Pedro",
            descripcion = "Recolecta de abrigos, suéteres y bufandas en buen estado.",
            recaudadoTexto = "75 de 100 prendas",
            metaTexto = "Meta: 100 prendas",
            progreso = 0.75f,
            urgente = true,
            diasRestantes = 5
        ),
        Campana(
            id = "kits-escolares",
            titulo = "Kits Escolares 2024",
            organizacion = "Escuela Rural Los Pinos",
            descripcion = "Útiles escolares para niños de comunidades rurales.",
            recaudadoTexto = "40% completado",
            metaTexto = "Meta: 200 kits",
            progreso = 0.40f,
            diasRestantes = 12
        )
    )

    val campanaInvierno = listOf(
        ArticuloDonacion(
            nombre = "Ropa de Invierno",
            descripcion = "Abrigos, suéteres y bufandas en buen estado.",
            prioridad = "Alta Prioridad",
            progresoTexto = "75/100 prendas",
            progreso = 0.75f
        ),
        ArticuloDonacion(
            nombre = "Alimentos No Perecederos",
            descripcion = "Arroz, legumbres, conservas, aceite.",
            prioridad = "Urgente",
            progresoTexto = "30/50 kg",
            progreso = 0.60f
        ),
        ArticuloDonacion(
            nombre = "Mantas y Frazadas",
            descripcion = "Limpias y sin roturas para camas de 1 o 2 plazas.",
            prioridad = "Media",
            progresoTexto = "50/100 mantas",
            progreso = 0.50f
        )
    )

    val miembrosComunidad = listOf(
        MiembroComunidad("ana", "Ana", "Hija", "A"),
        MiembroComunidad("pedro", "Pedro", "Abuelo", "P"),
        MiembroComunidad("sofia", "Sofía", "Sobrina", "S")
    )

    val propuestas = listOf(
        Propuesta(
            id = "limpieza-parque",
            titulo = "Limpieza del Parque Central",
            descripcion = "Iniciativa vecinal para restaurar las áreas verdes y recoger residuos.",
            fecha = "12 Oct 2023",
            estado = EstadoPropuesta.EN_REVISION
        ),
        Propuesta(
            id = "taller-reciclaje",
            titulo = "Taller de Reciclaje Barrio Sur",
            descripcion = "Educación ambiental para niños de 8 a 12 años los fines de semana.",
            fecha = "05 Sep 2023",
            estado = EstadoPropuesta.APROBADA
        ),
        Propuesta(
            id = "iluminacion-calle",
            titulo = "Iluminación Calle Las Flores",
            descripcion = "Solicitud para instalar focos LED en la vía principal por seguridad.",
            fecha = "22 Ago 2023",
            estado = EstadoPropuesta.NECESITA_CAMBIOS
        )
    )

    val experiencias = listOf(
        Experiencia("Reforestación con Raíces Vivas", "15 Sep, 2023"),
        Experiencia("Apoyo en Comedor Sol", "02 Ago, 2023")
    )

    val temasInteres = listOf(
        "Medio Ambiente", "Educación", "Salud", "Bienestar Animal", "Tercera Edad", "Deportes"
    )
}
