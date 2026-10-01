package mx.familiasquesuman.app.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import mx.familiasquesuman.app.data.SampleData
import mx.familiasquesuman.app.ui.components.AppBottomNavBar
import mx.familiasquesuman.app.ui.components.AppDrawerContent
import mx.familiasquesuman.app.ui.components.FormasFlotantesFondo
import mx.familiasquesuman.app.ui.screens.ActividadDetalleScreen
import mx.familiasquesuman.app.ui.screens.AsociacionDetalleScreen
import mx.familiasquesuman.app.ui.screens.BienvenidaScreen
import mx.familiasquesuman.app.ui.screens.CampanaDonacionScreen
import mx.familiasquesuman.app.ui.screens.EstadoPropuestaScreen
import mx.familiasquesuman.app.ui.screens.ExplorarScreen
import mx.familiasquesuman.app.ui.screens.InicioScreen
import mx.familiasquesuman.app.ui.screens.LoginScreen
import mx.familiasquesuman.app.ui.screens.MiComunidadScreen
import mx.familiasquesuman.app.ui.screens.MisPropuestasScreen
import mx.familiasquesuman.app.ui.screens.NotificacionesScreen
import mx.familiasquesuman.app.ui.screens.OnboardingPreferenciasScreen
import mx.familiasquesuman.app.ui.screens.ParticiparScreen
import mx.familiasquesuman.app.ui.screens.PerfilScreen
import mx.familiasquesuman.app.ui.screens.ProponerIniciativaScreen
import mx.familiasquesuman.app.ui.screens.PropuestaEnviadaScreen
import mx.familiasquesuman.app.ui.screens.RegistroScreen
import mx.familiasquesuman.app.ui.screens.ReportarExperienciaScreen
import mx.familiasquesuman.app.ui.screens.TodasCampanasScreen
import mx.familiasquesuman.app.ui.screens.TodosVoluntariadosScreen
import mx.familiasquesuman.app.viewmodel.AppViewModel

/** Rutas que muestran la barra de navegación inferior (las 4 pestañas principales). */
private val rutasConBottomNav = TabDestino.entries.map { it.ruta }.toSet()

/** Rutas donde tiene sentido abrir el menú lateral (las mismas 4 pantallas principales). */
private val rutasConDrawer = rutasConBottomNav

@Composable
fun AppNavHost(esPantallaAncha: Boolean) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route
    val appViewModel: AppViewModel = viewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = rutaActual in rutasConDrawer,
        drawerContent = {
            ModalDrawerSheet {
                AppDrawerContent(
                    rutaActual = rutaActual,
                    onNavegar = { ruta ->
                        scope.launch { drawerState.close() }
                        navController.navigate(ruta) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onCerrarSesion = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Routes.BIENVENIDA) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            // Cada pantalla ya maneja su propio inset de status bar a través de su TopAppBar
            // (o de windowInsetsPadding cuando no tiene una). Si el Scaffold TAMBIÉN reserva
            // ese espacio aquí arriba, queda un espacio en blanco duplicado sobre cada pantalla.
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (rutaActual in rutasConBottomNav) {
                    val tabActual = TabDestino.entries.firstOrNull { it.ruta == rutaActual } ?: TabDestino.INICIO
                    AppBottomNavBar(
                        pestanaActual = tabActual,
                        onSeleccionar = { tab ->
                            navController.navigate(tab.ruta) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                NavHost(navController = navController, startDestination = Routes.BIENVENIDA) {

                    composable(Routes.BIENVENIDA) {
                        BienvenidaScreen(
                            onIniciarSesion = { navController.navigate(Routes.LOGIN) },
                            onCrearCuenta = { navController.navigate(Routes.REGISTRO) },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }
                    composable(Routes.LOGIN) {
                        LoginScreen(
                            onIniciarSesion = { navController.navigate(Routes.INICIO) { popUpTo(Routes.BIENVENIDA) { inclusive = true } } },
                            onCrearCuenta = { navController.navigate(Routes.REGISTRO) },
                            onBack = { navController.popBackStack() },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }
                    composable(Routes.REGISTRO) {
                        RegistroScreen(
                            onRegistrarse = { navController.navigate(Routes.ONBOARDING_PREFERENCIAS) },
                            onBack = { navController.popBackStack() },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }
                    composable(Routes.ONBOARDING_PREFERENCIAS) {
                        OnboardingPreferenciasScreen(
                            onEmpezar = { navController.navigate(Routes.INICIO) { popUpTo(Routes.BIENVENIDA) { inclusive = true } } },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }
                    composable(Routes.PREFERENCIAS) {
                        OnboardingPreferenciasScreen(
                            esEdicion = true,
                            onBack = { navController.popBackStack() },
                            onEmpezar = { navController.popBackStack() },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }

                    // Inicio, Explorar, Participar y Perfil no tienen su propio Surface con
                    // color de fondo (su Column raíz es transparente), así que el fondo de
                    // formas flotantes se puede poner aquí atrás sin tocar cada pantalla: se
                    // ve en los espacios en blanco y queda detrás de las tarjetas y del
                    // BrandTopBar (que sí son opacos).
                    composable(Routes.INICIO) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            InicioScreen(
                                esPantallaAncha = esPantallaAncha,
                                onVerAsociacion = { id -> navController.navigate(Routes.asociacionDetalle(id)) },
                                onVerActividad = { id -> navController.navigate(Routes.actividadDetalle(id)) },
                                onVerTodasAsociaciones = { navController.navigate(Routes.EXPLORAR) { launchSingleTop = true } },
                                onVerCampana = { navController.navigate(Routes.CAMPANA_DONACION) },
                                onMenu = { scope.launch { drawerState.open() } },
                                onNotificaciones = { navController.navigate(Routes.NOTIFICACIONES) },
                                hayNotificacionesSinLeer = appViewModel.hayNotificacionesSinLeer
                            )
                        }
                    }
                    composable(Routes.EXPLORAR) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            ExplorarScreen(
                                onVerAsociacion = { id -> navController.navigate(Routes.asociacionDetalle(id)) },
                                onMenu = { scope.launch { drawerState.open() } },
                                onNotificaciones = { navController.navigate(Routes.NOTIFICACIONES) },
                                hayNotificacionesSinLeer = appViewModel.hayNotificacionesSinLeer
                            )
                        }
                    }
                    composable(Routes.PARTICIPAR) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            ParticiparScreen(
                                onProponerIniciativa = { navController.navigate(Routes.PROPONER_INICIATIVA) },
                                onVerActividad = { id -> navController.navigate(Routes.actividadDetalle(id)) },
                                onVerCampana = { navController.navigate(Routes.CAMPANA_DONACION) },
                                onVerTodosVoluntariados = { navController.navigate(Routes.TODOS_VOLUNTARIADOS) },
                                onVerTodasCampanas = { navController.navigate(Routes.TODAS_CAMPANAS) },
                                onMenu = { scope.launch { drawerState.open() } },
                                onNotificaciones = { navController.navigate(Routes.NOTIFICACIONES) },
                                hayNotificacionesSinLeer = appViewModel.hayNotificacionesSinLeer
                            )
                        }
                    }
                    composable(Routes.PERFIL) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            PerfilScreen(
                                comunidad = appViewModel.comunidad,
                                fondoAnimadoActivo = appViewModel.fondoAnimadoActivo,
                                onCambiarFondoAnimado = appViewModel::cambiarFondoAnimado,
                                onProponerIniciativa = { navController.navigate(Routes.PROPONER_INICIATIVA) },
                                onMiComunidad = { navController.navigate(Routes.MI_COMUNIDAD) },
                                onMisPropuestas = { navController.navigate(Routes.MIS_PROPUESTAS) },
                                onPreferencias = { navController.navigate(Routes.PREFERENCIAS) },
                                onVerActividad = { id -> navController.navigate(Routes.actividadDetalle(id)) },
                                onMenu = { scope.launch { drawerState.open() } },
                                onNotificaciones = { navController.navigate(Routes.NOTIFICACIONES) },
                                hayNotificacionesSinLeer = appViewModel.hayNotificacionesSinLeer
                            )
                        }
                    }

                    // De aquí para abajo son pantallas de detalle/formulario: también tienen
                    // Column raíz transparente, así que se envuelven igual que las 4 de arriba.
                    composable(Routes.ASOCIACION_DETALLE) { entry ->
                        val id = entry.arguments?.getString("asociacionId")
                        val asociacion = SampleData.asociaciones.firstOrNull { it.id == id } ?: SampleData.asociaciones.first()
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            AsociacionDetalleScreen(
                                asociacion = asociacion,
                                onBack = { navController.popBackStack() },
                                onVerActividad = { actId -> navController.navigate(Routes.actividadDetalle(actId)) },
                                onReportar = { navController.navigate(Routes.REPORTAR_EXPERIENCIA) }
                            )
                        }
                    }
                    composable(Routes.ACTIVIDAD_DETALLE) { entry ->
                        val id = entry.arguments?.getString("actividadId")
                        val actividad = SampleData.actividades.firstOrNull { it.id == id } ?: SampleData.actividades.first()
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            ActividadDetalleScreen(
                                actividad = actividad,
                                miembrosComunidad = appViewModel.comunidad,
                                onBack = { navController.popBackStack() },
                                onIrAMiComunidad = { navController.navigate(Routes.MI_COMUNIDAD) },
                                onInscripcionExitosa = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(Routes.MI_COMUNIDAD) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            MiComunidadScreen(
                                miembros = appViewModel.comunidad,
                                onAgregar = appViewModel::agregarMiembro,
                                onEliminar = appViewModel::eliminarMiembro,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(Routes.PROPONER_INICIATIVA) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            ProponerIniciativaScreen(
                                onBack = { navController.popBackStack() },
                                onEnviar = { titulo, descripcion ->
                                    appViewModel.agregarPropuesta(titulo, descripcion)
                                    navController.navigate(Routes.PROPUESTA_ENVIADA) {
                                        popUpTo(Routes.PROPONER_INICIATIVA) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                    composable(Routes.PROPUESTA_ENVIADA) {
                        PropuestaEnviadaScreen(
                            onVolverInicio = { navController.navigate(Routes.INICIO) { popUpTo(Routes.INICIO) { inclusive = true } } },
                            onVerMisPropuestas = { navController.navigate(Routes.MIS_PROPUESTAS) },
                            animacionesActivas = appViewModel.fondoAnimadoActivo
                        )
                    }
                    composable(Routes.MIS_PROPUESTAS) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            MisPropuestasScreen(
                                propuestas = appViewModel.propuestas,
                                onBack = { navController.popBackStack() },
                                onVerPropuesta = { id -> navController.navigate(Routes.estadoPropuesta(id)) }
                            )
                        }
                    }
                    composable(Routes.ESTADO_PROPUESTA) { entry ->
                        val id = entry.arguments?.getString("propuestaId")
                        val propuesta = appViewModel.propuestaPorId(id)
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            EstadoPropuestaScreen(propuesta = propuesta, onBack = { navController.popBackStack() })
                        }
                    }
                    composable(Routes.CAMPANA_DONACION) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            CampanaDonacionScreen(
                                onRegistrarEntrega = { },
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(Routes.REPORTAR_EXPERIENCIA) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            ReportarExperienciaScreen(
                                onBack = { navController.popBackStack() },
                                onEnviar = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(Routes.NOTIFICACIONES) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            NotificacionesScreen(
                                notificaciones = appViewModel.notificaciones,
                                onBack = { navController.popBackStack() },
                                onMarcarLeida = appViewModel::marcarLeida,
                                onEliminar = appViewModel::eliminarNotificacion
                            )
                        }
                    }
                    composable(Routes.TODOS_VOLUNTARIADOS) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            TodosVoluntariadosScreen(
                                onBack = { navController.popBackStack() },
                                onVerActividad = { id -> navController.navigate(Routes.actividadDetalle(id)) }
                            )
                        }
                    }
                    composable(Routes.TODAS_CAMPANAS) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            FormasFlotantesFondo(activo = appViewModel.fondoAnimadoActivo)
                            TodasCampanasScreen(



                                
                                onBack = { navController.popBackStack() },
                                onVerCampana = { navController.navigate(Routes.CAMPANA_DONACION) }
                            )
                        }
                    }
                }
            }
        }
    }
}