package com.javiviz.sh01_pasteleriacrud.features.main.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.components.DrawerContenido
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation.Destinos
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation.MainNavHost
import kotlinx.coroutines.launch

/** Contenedor autenticado: drawer lateral + Scaffold donde se montan las vistas según la ruta. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    usuario: Usuario,
    onCerrarSesion: () -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route
    val destinoActual = Destinos.deRuta(rutaActual)
    val esSubpantalla = destinoActual != null && !destinoActual.enDrawer

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContenido(
                usuario = usuario,
                destinos = Destinos.paraDrawer(usuario.rol),
                rutaActual = rutaActual,
                onDestinoClick = { destino ->
                    scope.launch { drawerState.close() }
                    navController.navegarADestino(destino.ruta)
                },
                onCerrarSesion = {
                    scope.launch { drawerState.close() }
                    onCerrarSesion()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(destinoActual?.titulo ?: "Pastelería") },
                    navigationIcon = {
                        if (esSubpantalla) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        } else {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            MainNavHost(
                navController = navController,
                usuario = usuario,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

/** Navegación entre secciones del drawer: conserva el estado de cada una y evita duplicados. */
private fun NavHostController.navegarADestino(ruta: String) {
    navigate(ruta) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
