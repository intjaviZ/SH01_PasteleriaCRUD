package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import java.math.BigDecimal
import java.time.Instant

/** Respuesta resumida de /pagar, /entregar y /cancelar. Los campos opcionales dependen de la operación. */
data class CambioEstadoPedido(
    val id: Int,
    val estado: EstadoPedido,
    /** fecha_pago, fecha_entrega_real o fecha_cancelacion según la operación. */
    val fechaCambio: Instant?,
    val montoRecibido: BigDecimal?,
    val motivoCancelacion: String?,
    val canceladoPor: Rol?
)
