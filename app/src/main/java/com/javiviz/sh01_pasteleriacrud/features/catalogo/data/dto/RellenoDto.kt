package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RellenoDto(
    val id: Int,
    val nombre: String,
    val activo: Boolean
)
