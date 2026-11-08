package com.javiviz.sh01_pasteleriacrud.features.login.data.mapper

import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import com.javiviz.sh01_pasteleriacrud.core.network.mapper.toUsuario
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginResponseDto
import java.time.Instant

/** expires_in viene en segundos; la sesión guarda el instante exacto de expiración. */
fun LoginResponseDto.toSesion(ahora: Instant = Instant.now()): Sesion = Sesion(
    accessToken = accessToken,
    expiraEn = ahora.plusSeconds(expiresIn),
    usuario = usuario.toUsuario()
)
