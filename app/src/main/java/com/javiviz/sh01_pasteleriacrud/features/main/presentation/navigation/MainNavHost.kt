package com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.screens.CatalogoScreen
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.components.RutaProtegida
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens.NuevoPedidoScreen
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens.PedidoDetalleScreen
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens.PedidosScreen
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.screens.ReportesScreen

/**
 * NavHost interno de MainScreen. Cada ruta pasa por RutaProtegida, que revisa el Rol.
 */
@Composable
fun MainNavHost(
    navController: NavHostController,
    usuario: Usuario,
    modifier: Modifier = Modifier
) {
    val rol = usuario.rol
    val volverAInicio: () -> Unit = {
        navController.popBackStack(Rutas.PEDIDOS, inclusive = false)
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.PEDIDOS,
        modifier = modifier
    ) {
        composable(Rutas.CATALOGO) {
            RutaProtegida(Destinos.Catalogo, rol, volverAInicio) {
                CatalogoScreen(
                    rol = rol,
                    onHacerPedido = { navController.navigate(Rutas.NUEVO_PEDIDO) }
                )
            }
        }

        composable(Rutas.PEDIDOS) { entry ->
            // Otras pantallas dejan aquí una marca cuando modifican un pedido; la lista la lee y se recarga.
            val refrescar by entry.savedStateHandle
                .getStateFlow(Rutas.CLAVE_REFRESCAR_PEDIDOS, false)
                .collectAsState()

            RutaProtegida(Destinos.Pedidos, rol, volverAInicio) {
                PedidosScreen(
                    rol = rol,
                    refrescar = refrescar,
                    onRefrescado = { entry.savedStateHandle[Rutas.CLAVE_REFRESCAR_PEDIDOS] = false },
                    onPedidoClick = { id -> navController.navigate(Rutas.detallePedido(id)) },
                    onNuevoPedido = { navController.navigate(Rutas.NUEVO_PEDIDO) }
                )
            }
        }

        composable(Rutas.NUEVO_PEDIDO) {
            RutaProtegida(Destinos.NuevoPedido, rol, volverAInicio) {
                NuevoPedidoScreen(
                    onPedidoCreado = { id ->
                        navController.marcarPedidosParaRefrescar()
                        // El formulario sale del historial: al volver desde el detalle no reaparece.
                        navController.navigate(Rutas.detallePedido(id)) {
                            popUpTo(Rutas.NUEVO_PEDIDO) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            route = Rutas.DETALLE_PEDIDO_PATRON,
            arguments = listOf(navArgument(Rutas.ARG_PEDIDO_ID) { type = NavType.IntType })
        ) { entry ->
            val pedidoId = entry.arguments?.getInt(Rutas.ARG_PEDIDO_ID) ?: -1
            RutaProtegida(Destinos.DetallePedido, rol, volverAInicio) {
                PedidoDetalleScreen(
                    pedidoId = pedidoId,
                    rol = rol,
                    onPedidoModificado = { navController.marcarPedidosParaRefrescar() }
                )
            }
        }

        composable(Rutas.REPORTES) {
            RutaProtegida(Destinos.Reportes, rol, volverAInicio) {
                ReportesScreen()
            }
        }
    }
}

/** Si la lista de pedidos no está en el historial, no hay nada que refrescar. */
private fun NavHostController.marcarPedidosParaRefrescar() {
    runCatching {
        getBackStackEntry(Rutas.PEDIDOS).savedStateHandle[Rutas.CLAVE_REFRESCAR_PEDIDOS] = true
    }
}
