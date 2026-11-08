package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model

import java.math.BigDecimal

data class PastelCatalogo(
    val id: Int,
    val nombre: String,
    val permiteRelleno: Boolean,
    val precioBasePorcion: BigDecimal,
    val activo: Boolean
)
