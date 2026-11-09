package com.javiviz.sh01_pasteleriacrud.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.javiviz.sh01_pasteleriacrud.core.ui.comoFecha
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

/** Campo de fecha que abre un calendario. Reutilizable en cualquier formulario o filtro. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoFecha(
    etiqueta: String,
    fecha: LocalDate?,
    onFechaSeleccionada: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    /** Pinta el campo en rojo sin mostrar texto (útil cuando el mensaje se muestra aparte). */
    marcarError: Boolean = false,
    enabled: Boolean = true
) {
    var mostrarCalendario by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = fecha?.comoFecha() ?: "",
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            label = { Text(etiqueta) },
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            isError = error != null || marcarError,
            supportingText = if (error != null) {
                { Text(error) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(enabled = enabled) { mostrarCalendario = true }
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
                        // El calendario entrega la medianoche UTC del día elegido.
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
