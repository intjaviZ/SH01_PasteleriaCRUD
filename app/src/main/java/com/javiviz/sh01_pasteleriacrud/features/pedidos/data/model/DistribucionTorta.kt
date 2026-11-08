package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

/** Sirve para leer pedidos y también para armar el request de un pedido nuevo. */
data class DistribucionTorta(
    val porcionesTorta: Int,
    val cantidad: Int
)
