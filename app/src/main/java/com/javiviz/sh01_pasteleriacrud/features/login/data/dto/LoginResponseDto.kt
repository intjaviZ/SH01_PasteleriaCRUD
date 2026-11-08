package com.javiviz.sh01_pasteleriacrud.features.login.data.dto

import com.javiviz.sh01_pasteleriacrud.core.network.dto.UsuarioDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Long,
    val usuario: UsuarioDto
)
