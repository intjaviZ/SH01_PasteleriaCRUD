package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno
import java.math.BigDecimal
import java.time.LocalDate

data class FilaDistribucionUi(
    val uid: Int,
    val porcionesTorta: Int? = null,
    /** Texto porque el usuario lo escribe; se valida como entero >= 1. */
    val cantidad: String = "1"
)

data class ErroresItem(
    val pastel: String? = null,
    val relleno: String? = null,
    val porciones: String? = null,
    val colorPrimario: String? = null,
    val colorSecundario: String? = null,
    val dedicatoria: String? = null,
    val distribucion: String? = null
) {
    fun hayErrores(): Boolean =
        listOf(pastel, relleno, porciones, colorPrimario, colorSecundario, dedicatoria, distribucion)
            .any { it != null }
}

data class ItemFormUi(
    val uid: Int,
    val pastelId: Int? = null,
    val rellenoId: Int? = null,
    val porciones: Int? = null,
    /** Colores como hex ("#F7D774"), que es lo que espera la API. */
    val colorPrimario: String = "",
    val colorSecundario: String = "",
    val dedicatoria: String = "",
    val dividirEnTortas: Boolean = false,
    val distribucion: List<FilaDistribucionUi> = emptyList(),
    val errores: ErroresItem = ErroresItem()
) {
    fun sumaDistribucion(): Int =
        distribucion.sumOf { (it.porcionesTorta ?: 0) * (it.cantidad.toIntOrNull() ?: 0) }
}

data class ErroresFechaEntrega(
    val fecha: String? = null,
    val hora: String? = null
) {
    fun hayErrores(): Boolean = fecha != null || hora != null
}

data class NuevoPedidoUiState(
    val cargandoCatalogo: Boolean = true,
    val errorCatalogo: String? = null,
    val pasteles: List<PastelCatalogo> = emptyList(),
    val rellenos: List<Relleno> = emptyList(),
    val config: ConfiguracionTamanos? = null,

    val fechaEntrega: LocalDate? = null,
    val horaEntrega: Int? = null,
    val erroresFecha: ErroresFechaEntrega = ErroresFechaEntrega(),
    val notas: String = "",
    val items: List<ItemFormUi> = listOf(ItemFormUi(uid = 0)),

    val enviando: Boolean = false,
    val errorGeneral: String? = null,
    /** La pantalla lo consume una vez para navegar al detalle del pedido creado. */
    val pedidoCreadoId: Int? = null
) {
    /** Vista previa: precio base por porción x porciones de cada item (el servidor calcula el total oficial). */
    val totalEstimado: BigDecimal
        get() = items.fold(BigDecimal.ZERO) { acumulado, item ->
            val pastel = pasteles.firstOrNull { it.id == item.pastelId }
            val porciones = item.porciones
            if (pastel != null && porciones != null) {
                acumulado + pastel.precioBasePorcion.multiply(BigDecimal(porciones))
            } else {
                acumulado
            }
        }
}
