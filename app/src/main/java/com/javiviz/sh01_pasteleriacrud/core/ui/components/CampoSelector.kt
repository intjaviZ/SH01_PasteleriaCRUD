package com.javiviz.sh01_pasteleriacrud.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Selector desplegable genérico. Se arma con TextField de solo lectura + DropdownMenu
 * para no depender de las APIs de ExposedDropdownMenuBox, que cambian entre versiones de Material3.
 */
@Composable
fun <T> CampoSelector(
    etiqueta: String,
    opciones: List<T>,
    seleccionado: T?,
    etiquetaDe: (T) -> String,
    onSeleccion: (T) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    enabled: Boolean = true,
    placeholder: String = "Selecciona una opción"
) {
    var expandido by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = if (seleccionado != null) etiquetaDe(seleccionado) else "",
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(etiqueta) },
            placeholder = { Text(placeholder) },
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
            isError = error != null,
            supportingText = if (error != null) {
                { Text(error) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        // Capa transparente que recibe el toque: un TextField de solo lectura no abre menús por sí solo.
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(enabled = enabled) { expandido = true }
        )

        DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(etiquetaDe(opcion)) },
                    onClick = {
                        onSeleccion(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}
