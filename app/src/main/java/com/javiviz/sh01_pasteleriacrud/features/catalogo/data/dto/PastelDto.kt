package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PastelDto(
    val id: Int,
    val nombre: String,
    @SerialName("permite_relleno") val permiteRelleno: Boolean,
    @SerialName("precio_base_porcion") val precioBasePorcion: Double,
    val activo: Boolean
)
