package com.javiviz.sh01_pasteleriacrud.core.ui

import androidx.compose.ui.graphics.Color
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val LOCALE_MX: Locale = Locale.forLanguageTag("es-MX")
private val FORMATO_FECHA: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", LOCALE_MX)
private val FORMATO_FECHA_HORA: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", LOCALE_MX)

fun BigDecimal.comoMoneda(): String = NumberFormat.getCurrencyInstance(LOCALE_MX).format(this)

fun Instant.comoFecha(zona: ZoneId = ZoneId.systemDefault()): String =
    FORMATO_FECHA.format(atZone(zona))

fun Instant.comoFechaHora(zona: ZoneId = ZoneId.systemDefault()): String =
    FORMATO_FECHA_HORA.format(atZone(zona))

fun Instant.esHoy(zona: ZoneId = ZoneId.systemDefault()): Boolean =
    atZone(zona).toLocalDate() == LocalDate.now(zona)

fun LocalDate.comoFecha(): String = FORMATO_FECHA.format(this)

/** "#F7D774" -> Color, o null si el texto no es un color válido. */
fun String.aColorOrNull(): Color? =
    try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: IllegalArgumentException) {
        null
    }
