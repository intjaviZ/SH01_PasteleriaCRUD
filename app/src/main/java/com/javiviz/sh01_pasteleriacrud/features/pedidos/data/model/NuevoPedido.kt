package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import java.time.Instant

/** Lo que el usuario captura para crear un pedido (cliente_id y metodo_pago los pone la API). */
data class NuevoPedido(
    val fechaEntregaEsperada: Instant,
    val notasEntrega: String?,
    val items: List<NuevoItemPedido>
)

data class NuevoItemPedido(
    val pastelId: Int,
    val rellenoId: Int?,
    val porcionesTotales: Int,
    val colorPrimario: String,
    val colorSecundario: String,
    val dedicatoria: String,
    val distribucion: List<DistribucionTorta>? = null
)
