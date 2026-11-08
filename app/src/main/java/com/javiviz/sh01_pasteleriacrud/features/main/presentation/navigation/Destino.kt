package com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.javiviz.sh01_pasteleriacrud.core.model.Rol

/**
 * Un destino de MainScreen y quién puede entrar.
 * Todos requieren sesión; rolesPermitidos restringe además por Rol.
 */
data class Destino(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector? = null,
    val rolesPermitidos: Set<Rol> = Rol.entries.toSet(),
    val enDrawer: Boolean = false
) {
    fun permiteA(rol: Rol): Boolean = rol in rolesPermitidos
}

object Destinos {

    val Catalogo = Destino(
        ruta = Rutas.CATALOGO,
        titulo = "Catálogo",
        icono = Icons.Default.Star,
        enDrawer = true
    )

    val Pedidos = Destino(
        ruta = Rutas.PEDIDOS,
        titulo = "Pedidos",
        icono = Icons.Default.ShoppingCart,
        enDrawer = true
    )

    val NuevoPedido = Destino(
        ruta = Rutas.NUEVO_PEDIDO,
        titulo = "Nuevo pedido",
        icono = Icons.Default.Add,
        rolesPermitidos = setOf(Rol.CLIENTE),
        enDrawer = true
    )

    /** Ambos roles pueden verlo, pero no aparece en el drawer: se abre desde la lista de pedidos. */
    val DetallePedido = Destino(
        ruta = Rutas.DETALLE_PEDIDO_PATRON,
        titulo = "Detalle del pedido"
    )

    val Reportes = Destino(
        ruta = Rutas.REPORTES,
        titulo = "Reportes de ventas",
        icono = Icons.Default.DateRange,
        rolesPermitidos = setOf(Rol.REPOSTERA),
        enDrawer = true
    )

    val todos = listOf(Catalogo, Pedidos, NuevoPedido, DetallePedido, Reportes)

    fun paraDrawer(rol: Rol): List<Destino> = todos.filter { it.enDrawer && it.permiteA(rol) }

    fun deRuta(ruta: String?): Destino? = todos.firstOrNull { it.ruta == ruta }
}
