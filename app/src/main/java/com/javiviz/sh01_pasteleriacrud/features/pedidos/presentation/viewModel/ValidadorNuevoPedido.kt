package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.DistribucionTorta
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.NuevoItemPedido
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Reglas de negocio del contrato validadas en el cliente antes de llamar a la API
 * (relleno, porciones, distribución, fechas). Los límites salen de ConfiguracionTamanos,
 * nada está fijo en el código. La API vuelve a validar todo: esto solo ahorra viajes de red.
 */
object ValidadorNuevoPedido {

    fun fechaEntrega(
        fecha: LocalDate?,
        hora: Int?,
        margenHoras: Int,
        ahora: Instant,
        zona: ZoneId
    ): ErroresFechaEntrega {
        val errorFecha = if (fecha == null) "Selecciona la fecha de entrega." else null
        val errorHora = if (hora == null) "Selecciona la hora de entrega." else null
        if (fecha == null || hora == null) return ErroresFechaEntrega(errorFecha, errorHora)

        val entrega = fecha.atTime(hora, 0).atZone(zona).toInstant()
        val minimo = ahora.plus(Duration.ofHours(margenHoras.toLong()))
        return if (entrega.isBefore(minimo)) {
            ErroresFechaEntrega(fecha = "La entrega debe ser al menos $margenHoras horas después de ahora.")
        } else {
            ErroresFechaEntrega()
        }
    }

    fun item(item: ItemFormUi, pastel: PastelCatalogo?, config: ConfiguracionTamanos): ErroresItem {
        val porciones = item.porciones
        val multiploValido = config.multiplo > 0

        return ErroresItem(
            pastel = if (pastel == null) "Selecciona un pastel." else null,
            relleno = when {
                pastel == null -> null
                pastel.permiteRelleno && item.rellenoId == null -> "Selecciona un relleno."
                !pastel.permiteRelleno && item.rellenoId != null -> "Este pastel no admite relleno."
                else -> null
            },
            porciones = when {
                porciones == null -> "Selecciona las porciones."
                porciones < config.porcionesMinimas ||
                    porciones > config.porcionesMaximas ||
                    (multiploValido && porciones % config.multiplo != 0) ->
                    "Debe ser múltiplo de ${config.multiplo} entre ${config.porcionesMinimas} y ${config.porcionesMaximas}."
                else -> null
            },
            colorPrimario = if (item.colorPrimario.isBlank()) "Elige un color." else null,
            colorSecundario = if (item.colorSecundario.isBlank()) "Elige un color." else null,
            dedicatoria = if (item.dedicatoria.length > config.dedicatoriaMaxCaracteres) {
                "Máximo ${config.dedicatoriaMaxCaracteres} caracteres."
            } else null,
            distribucion = distribucion(item, porciones, config)
        )
    }

    private fun distribucion(item: ItemFormUi, porciones: Int?, config: ConfiguracionTamanos): String? {
        if (!item.dividirEnTortas || porciones == null || porciones < config.umbralDistribucion) return null
        if (item.distribucion.isEmpty()) return "Agrega al menos un tamaño de torta."

        var suma = 0
        for (fila in item.distribucion) {
            val porcionesTorta = fila.porcionesTorta ?: return "Elige las porciones de cada torta."
            val cantidad = fila.cantidad.toIntOrNull()
            if (cantidad == null || cantidad < 1) return "La cantidad de cada torta debe ser 1 o más."
            if (config.multiplo > 0 && porcionesTorta % config.multiplo != 0) {
                return "Las porciones de cada torta deben ser múltiplo de ${config.multiplo}."
            }
            suma += porcionesTorta * cantidad
        }
        return if (suma != porciones) "La suma de las tortas es $suma y debe ser $porciones." else null
    }

    /** Convierte el item del formulario al modelo que se envía. Solo se llama con items ya validados. */
    fun aNuevoItem(item: ItemFormUi, config: ConfiguracionTamanos): NuevoItemPedido? {
        val pastelId = item.pastelId ?: return null
        val porciones = item.porciones ?: return null
        if (item.colorPrimario.isBlank() || item.colorSecundario.isBlank()) return null

        val distribucion = if (item.dividirEnTortas && porciones >= config.umbralDistribucion) {
            item.distribucion.mapNotNull { fila ->
                val porcionesTorta = fila.porcionesTorta
                val cantidad = fila.cantidad.toIntOrNull()
                if (porcionesTorta != null && cantidad != null) DistribucionTorta(porcionesTorta, cantidad) else null
            }
        } else null

        return NuevoItemPedido(
            pastelId = pastelId,
            rellenoId = item.rellenoId,
            porcionesTotales = porciones,
            colorPrimario = item.colorPrimario,
            colorSecundario = item.colorSecundario,
            dedicatoria = item.dedicatoria.trim(),
            distribucion = distribucion
        )
    }
}
