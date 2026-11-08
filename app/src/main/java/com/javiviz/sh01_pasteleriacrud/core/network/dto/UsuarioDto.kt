package com.javiviz.sh01_pasteleriacrud.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    val id: Int,
    val nombre: String,
    val telefono: String? = null,
    val email: String,
    val rol: String
)
