package com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation

/** Todas las rutas de la app en un solo lugar. */
object Rutas {

    // ----- Navegación raíz (sin drawer) -----
    const val LOGIN_PATRON = "login?registrado={registrado}"
    const val ARG_REGISTRADO = "registrado"
    fun login(registrado: Boolean = false) = "login?registrado=$registrado"

    const val SIGNUP = "signup"

    /** Contenedor autenticado: MainScreen con drawer y su propio NavHost. */
    const val MAIN = "main"

    // ----- Destinos dentro de MainScreen -----
    const val CATALOGO = "catalogo"
    const val PEDIDOS = "pedidos"
    const val NUEVO_PEDIDO = "pedido_nuevo"
    const val REPORTES = "reportes"

    const val ARG_PEDIDO_ID = "pedidoId"
    const val DETALLE_PEDIDO_PATRON = "pedido_detalle/{pedidoId}"
    fun detallePedido(id: Int) = "pedido_detalle/$id"
}
