package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.comoFecha
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoSelector
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.ErroresFechaEntrega
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

private val HORAS_DE_ENTREGA = (9..19).toList()

/**
 * Fecha (calendario) + hora (lista). Se separa así para no depender del TimePicker,
 * y porque una pastelería entrega en horario de atención.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorFechaEntrega(
    fecha: LocalDate?,
    hora: Int?,
    errores: ErroresFechaEntrega,
    margenHoras: Int,
    enabled: Boolean,
    onFechaSeleccionada: (LocalDate) -> Unit,
    onHoraSeleccionada: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var mostrarCalendario by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = fecha?.comoFecha() ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                label = { Text("Fecha de entrega") },
                trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                isError = errores.fecha != null,
                supportingText = {
                    Text(errores.fecha ?: "Con al menos $margenHoras horas de anticipación")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(enabled = enabled) { mostrarCalendario = true }
            )
        }

        CampoSelector(
            etiqueta = "Hora de entrega",
            opciones = HORAS_DE_ENTREGA,
            seleccionado = hora,
            etiquetaDe = { "%02d:00".format(it) },
            onSeleccion = onHoraSeleccionada,
            error = errores.hora,
            enabled = enabled
        )
    }

    if (mostrarCalendario) {
        val estadoCalendario = rememberDatePickerState(
            initialSelectedDateMillis = fecha?.atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(onClick = {
                    estadoCalendario.selectedDateMillis?.let { milisegundos ->
                        // El calendario entrega medianoche UTC del día elegido.
                        onFechaSeleccionada(
                            Instant.ofEpochMilli(milisegundos).atZone(ZoneOffset.UTC).toLocalDate()
                        )
                    }
                    mostrarCalendario = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = estadoCalendario)
        }
    }
}
