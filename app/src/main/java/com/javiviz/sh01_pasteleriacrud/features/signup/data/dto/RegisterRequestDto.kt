package com.javiviz.sh01_pasteleriacrud.features.signup.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val nombre: String,
    val telefono: String,
    val email: String,
    val password: String
)
