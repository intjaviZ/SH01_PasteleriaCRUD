package com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model

import com.javiviz.sh01_pasteleriacrud.core.model.Rol

/** Máquina de estados: ORDENADO -> PAGADO -> ENTREGADO; CANCELADO desde ORDENADO o PAGADO. */
enum class EstadoPedido {
    ORDENADO,
    PAGADO,
    ENTREGADO,
    CANCELADO;

    fun puedePagar(): Boolean = this == ORDENADO

    fun puedeEntregar(): Boolean = this == PAGADO

    fun puedeCancelarPor(rol: Rol): Boolean = when (rol) {
        Rol.REPOSTERA -> this == ORDENADO || this == PAGADO
        Rol.CLIENTE -> this == ORDENADO
    }
}
