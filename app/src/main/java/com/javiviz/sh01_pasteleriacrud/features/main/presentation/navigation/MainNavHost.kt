package com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.components.RutaProtegida
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.screens.PantallaPendienteScreen

/**
 * NavHost interno de MainScreen. Cada ruta pasa por RutaProtegida, que revisa el Rol.
 * Las pantallas reales aún no existen: cada una usa PantallaPendienteScreen.
 * Cuando las crees, reemplaza solo la línea marcada con TODO.
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
                PantallaPendienteScreen("Catálogo") // TODO: CatalogoScreen()
            }
        }

        composable(Rutas.PEDIDOS) {
            RutaProtegida(Destinos.Pedidos, rol, volverAInicio) {
                // TODO: PedidosScreen(onPedidoClick = { id -> navController.navigate(Rutas.detallePedido(id)) })
                PantallaPendienteScreen("Lista de pedidos")
            }
        }

        composable(Rutas.NUEVO_PEDIDO) {
            RutaProtegida(Destinos.NuevoPedido, rol, volverAInicio) {
                PantallaPendienteScreen("Crear pedido") // TODO: NuevoPedidoScreen()
            }
        }

        composable(
            route = Rutas.DETALLE_PEDIDO_PATRON,
            arguments = listOf(navArgument(Rutas.ARG_PEDIDO_ID) { type = NavType.IntType })
        ) { entry ->
            val pedidoId = entry.arguments?.getInt(Rutas.ARG_PEDIDO_ID) ?: -1
            RutaProtegida(Destinos.DetallePedido, rol, volverAInicio) {
                PantallaPendienteScreen("Detalle del pedido #$pedidoId") // TODO: DetallePedidoScreen(pedidoId)
            }
        }

        composable(Rutas.REPORTES) {
            RutaProtegida(Destinos.Reportes, rol, volverAInicio) {
                PantallaPendienteScreen("Reportes de ventas") // TODO: ReportesScreen()
            }
        }
    }
}
