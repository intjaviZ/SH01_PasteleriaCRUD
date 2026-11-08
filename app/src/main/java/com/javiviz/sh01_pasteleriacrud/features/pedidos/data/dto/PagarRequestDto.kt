package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagarRequestDto(
    @SerialName("monto_recibido") val montoRecibido: Double? = null
)
