package com.javiviz.sh01_pasteleriacrud.features.login.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)
