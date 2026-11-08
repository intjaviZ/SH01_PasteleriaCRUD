package com.javiviz.sh01_pasteleriacrud.core.network.mapper

import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeParseException

/** Conversiones seguras: si el servidor manda algo inesperado, falla con DomainError (no crashea). */
fun String.toInstantOrThrow(): Instant =
    try {
        Instant.parse(this)
    } catch (e: DateTimeParseException) {
        throw DomainError.Desconocido("Fecha inválida recibida del servidor: $this")
    }

fun String.toLocalDateOrThrow(): LocalDate =
    try {
        LocalDate.parse(this)
    } catch (e: DateTimeParseException) {
        throw DomainError.Desconocido("Fecha inválida recibida del servidor: $this")
    }

inline fun <reified E : Enum<E>> String.toEnumOrThrow(campo: String): E =
    enumValues<E>().firstOrNull { it.name == this }
        ?: throw DomainError.Desconocido("Valor no reconocido para $campo: $this")
