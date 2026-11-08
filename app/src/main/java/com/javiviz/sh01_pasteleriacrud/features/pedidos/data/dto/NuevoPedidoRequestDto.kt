package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Los null no se envían (explicitNulls = false en el Json del AppModule). */
@Serializable
data class NuevoPedidoRequestDto(
    @SerialName("fecha_entrega_esperada") val fechaEntregaEsperada: String,
    @SerialName("notas_entrega") val notasEntrega: String? = null,
    val items: List<NuevoItemRequestDto>
)

@Serializable
data class NuevoItemRequestDto(
    @SerialName("pastel_catalogo_id") val pastelCatalogoId: Int,
    @SerialName("relleno_id") val rellenoId: Int? = null,
    @SerialName("porciones_totales") val porcionesTotales: Int,
    @SerialName("color_primario") val colorPrimario: String,
    @SerialName("color_secundario") val colorSecundario: String,
    val dedicatoria: String,
    val distribucion: List<DistribucionDto>? = null
)
