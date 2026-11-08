package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Se usa en request y response (las claves extra de la respuesta se ignoran). */
@Serializable
data class DistribucionDto(
    @SerialName("porciones_torta") val porcionesTorta: Int,
    val cantidad: Int
)
