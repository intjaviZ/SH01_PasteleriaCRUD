package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoFecha
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.viewModel.AtajoRango
import java.time.LocalDate

/** Periodo (atajos + fechas manuales) y forma de agrupar. Sin estado propio. */
@Composable
fun FiltrosVentas(
    fechaInicio: LocalDate,
    fechaFin: LocalDate,
    atajo: AtajoRango?,
    agruparPor: AgrupacionVentas,
    errorRango: String?,
    onAtajoSeleccionado: (AtajoRango) -> Unit,
    onFechaInicioChange: (LocalDate) -> Unit,
    onFechaFinChange: (LocalDate) -> Unit,
    onAgrupacionChange: (AgrupacionVentas) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = "Periodo", style = MaterialTheme.typography.titleMedium)

        LazyRow(
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(AtajoRango.entries) { opcion ->
                FilterChip(
                    selected = atajo == opcion,
                    onClick = { onAtajoSeleccionado(opcion) },
                    label = { Text(opcion.etiqueta) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                CampoFecha(
                    etiqueta = "Desde",
                    fecha = fechaInicio,
                    onFechaSeleccionada = onFechaInicioChange,
                    marcarError = errorRango != null
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                CampoFecha(
                    etiqueta = "Hasta",
                    fecha = fechaFin,
                    onFechaSeleccionada = onFechaFinChange,
                    marcarError = errorRango != null
                )
            }
        }

        if (errorRango != null) {
            Text(
                text = errorRango,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Text(text = "Agrupar por", style = MaterialTheme.typography.titleMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AgrupacionVentas.entries.forEach { opcion ->
                FilterChip(
                    selected = agruparPor == opcion,
                    onClick = { onAgrupacionChange(opcion) },
                    label = { Text(opcion.etiqueta()) }
                )
            }
        }
    }
}
