package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model

/** Límites que usarán las validaciones del pedido (porciones, dedicatoria, margen de entrega). */
data class ConfiguracionTamanos(
    val porcionesMinimas: Int,
    val porcionesMaximas: Int,
    val multiplo: Int,
    val opcionesPorciones: List<Int>,
    val umbralDistribucion: Int,
    val dedicatoriaMaxCaracteres: Int,
    val margenMinimoEntregaHoras: Int
)
