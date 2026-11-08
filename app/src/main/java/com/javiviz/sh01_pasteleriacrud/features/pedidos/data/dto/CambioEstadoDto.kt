package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Une las respuestas de /pagar, /entregar y /cancelar: cada una trae solo sus campos. */
@Serializable
data class CambioEstadoDto(
    val id: Int,
    val estado: String,
    @SerialName("fecha_pago") val fechaPago: String? = null,
    @SerialName("monto_recibido") val montoRecibido: Double? = null,
    @SerialName("fecha_entrega_real") val fechaEntregaReal: String? = null,
    @SerialName("fecha_cancelacion") val fechaCancelacion: String? = null,
    @SerialName("motivo_cancelacion") val motivoCancelacion: String? = null,
    @SerialName("cancelado_por") val canceladoPor: String? = null
)
