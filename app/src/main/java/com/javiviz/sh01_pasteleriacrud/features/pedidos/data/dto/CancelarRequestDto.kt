package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CancelarRequestDto(
    val motivo: String? = null
)
