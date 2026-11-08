package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import java.math.BigDecimal

data class ItemPedido(
    val id: Int,
    val pastelId: Int,
    val pastelNombre: String?,
    val rellenoId: Int?,
    val rellenoNombre: String?,
    val porcionesTotales: Int,
    val colorPrimario: String,
    val colorSecundario: String,
    val dedicatoria: String,
    val precioUnitario: BigDecimal,
    val distribucion: List<DistribucionTorta>
) {
    fun subtotal(): BigDecimal = precioUnitario.multiply(BigDecimal(porcionesTotales))
}
