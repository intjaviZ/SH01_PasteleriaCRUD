package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components

import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

fun AgrupacionVentas.etiqueta(): String = when (this) {
    AgrupacionVentas.DIA -> "Día"
    AgrupacionVentas.SEMANA -> "Semana"
    AgrupacionVentas.MES -> "Mes"
    AgrupacionVentas.PASTEL -> "Pastel"
}

private val LOCALE_MX: Locale = Locale.forLanguageTag("es-MX")
private val FORMATO_DIA: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE d MMM", LOCALE_MX)
private val FORMATO_MES: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", LOCALE_MX)
private val PATRON_SEMANA = Regex("^(\\d{4})-W(\\d{1,2})$")

/**
 * La API manda el periodo en formato técnico ("2026-W45", "2026-11", "2026-11-03").
 * Si no se reconoce el formato, se muestra tal cual.
 */
fun etiquetaLegible(etiqueta: String, agruparPor: AgrupacionVentas): String =
    try {
        when (agruparPor) {
            AgrupacionVentas.DIA -> LocalDate.parse(etiqueta).format(FORMATO_DIA)
            AgrupacionVentas.MES -> YearMonth.parse(etiqueta).format(FORMATO_MES)
            AgrupacionVentas.SEMANA -> PATRON_SEMANA.matchEntire(etiqueta)
                ?.let { "Semana ${it.groupValues[2].toInt()} · ${it.groupValues[1]}" }
                ?: etiqueta
            AgrupacionVentas.PASTEL -> etiqueta
        }
    } catch (e: DateTimeParseException) {
        etiqueta
    }
