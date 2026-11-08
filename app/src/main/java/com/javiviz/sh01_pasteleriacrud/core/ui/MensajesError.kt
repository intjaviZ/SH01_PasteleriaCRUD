package com.javiviz.sh01_pasteleriacrud.core.ui

import com.javiviz.sh01_pasteleriacrud.core.model.DomainError

/** Texto listo para mostrar al usuario a partir de cualquier error que llegue del repositorio. */
fun Throwable.mensajeParaUsuario(): String =
    (this as? DomainError)?.message ?: "Ocurrió un error inesperado. Inténtalo de nuevo."
