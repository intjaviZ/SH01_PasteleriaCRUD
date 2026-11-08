package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PedidoDto(
    val id: Int,
    @SerialName("cliente_id") val clienteId: Int,
    val cliente: ClienteResumenDto? = null,
    @SerialName("fecha_creacion") val fechaCreacion: String,
    @SerialName("fecha_entrega_esperada") val fechaEntregaEsperada: String,
    val estado: String,
    @SerialName("total_estimado") val totalEstimado: Double,
    @SerialName("metodo_pago") val metodoPago: String,
    @SerialName("notas_entrega") val notasEntrega: String? = null,
    val items: List<ItemPedidoDto> = emptyList()
)

@Serializable
data class ClienteResumenDto(
    val id: Int,
    val nombre: String,
    val telefono: String? = null
)

@Serializable
data class ItemPedidoDto(
    val id: Int,
    @SerialName("pastel_catalogo_id") val pastelCatalogoId: Int,
    @SerialName("pastel_nombre") val pastelNombre: String? = null,
    @SerialName("relleno_id") val rellenoId: Int? = null,
    @SerialName("relleno_nombre") val rellenoNombre: String? = null,
    @SerialName("porciones_totales") val porcionesTotales: Int,
    @SerialName("color_primario") val colorPrimario: String,
    @SerialName("color_secundario") val colorSecundario: String,
    val dedicatoria: String,
    @SerialName("precio_unitario") val precioUnitario: Double,
    val distribucion: List<DistribucionDto> = emptyList()
)
