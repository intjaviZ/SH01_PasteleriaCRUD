package com.javiviz.sh01_pasteleriacrud.core.network.dto

import kotlinx.serialization.Serializable

/** Envoltorio { "data": [ ... ] } que usan los listados de la API. */
@Serializable
data class DataListResponseDto<T>(val data: List<T>)
