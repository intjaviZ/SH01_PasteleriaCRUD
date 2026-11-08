package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfiguracionTamanosDto(
    @SerialName("porciones_minimas") val porcionesMinimas: Int,
    @SerialName("porciones_maximas") val porcionesMaximas: Int,
    val multiplo: Int,
    @SerialName("opciones_porciones") val opcionesPorciones: List<Int>,
    @SerialName("umbral_distribucion") val umbralDistribucion: Int,
    @SerialName("dedicatoria_max_caracteres") val dedicatoriaMaxCaracteres: Int,
    @SerialName("margen_minimo_entrega_horas") val margenMinimoEntregaHoras: Int
)
