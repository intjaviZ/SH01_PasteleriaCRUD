package com.javiviz.sh01_pasteleriacrud.core.network.dto

import kotlinx.serialization.Serializable

/** Formato único de error de la API: { "error": { codigo, mensaje, detalles } } */
@Serializable
data class ErrorResponseDto(val error: ErrorBodyDto)

@Serializable
data class ErrorBodyDto(
    val codigo: String,
    val mensaje: String,
    val detalles: List<ErrorDetalleDto> = emptyList()
)

@Serializable
data class ErrorDetalleDto(val campo: String? = null)
