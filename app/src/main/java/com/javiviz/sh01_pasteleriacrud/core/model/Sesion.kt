package com.javiviz.sh01_pasteleriacrud.core.model

import java.time.Instant

data class Sesion(
    val accessToken: String,
    val expiraEn: Instant,
    val usuario: Usuario
)
