package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoSelector
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoTexto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.ItemFormUi

/**
 * Solo aparece cuando el pedido llega al umbral de porciones (50 en el contrato).
 * Si el cliente no divide, la API asume una sola torta del tamaño total.
 */
@Composable
fun DistribucionEditor(
    item: ItemFormUi,
    porciones: Int,
    config: ConfiguracionTamanos,
    enabled: Boolean,
    acciones: ItemFormAcciones,
    modifier: Modifier = Modifier
) {
    val opcionesTorta = config.opcionesPorciones.filter { it <= porciones }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = item.dividirEnTortas,
                onCheckedChange = acciones.onDividirChange,
                enabled = enabled
            )
            Spacer(Modifier.width(8.dp))
            Text(text = "Dividir en varias tortas", style = MaterialTheme.typography.bodyLarge)
        }

        if (item.dividirEnTortas) {
            item.distribucion.forEach { fila ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CampoSelector(
                            etiqueta = "Porciones por torta",
                            opciones = opcionesTorta,
                            seleccionado = fila.porcionesTorta,
                            etiquetaDe = { "$it" },
                            onSeleccion = { acciones.onFilaPorcionesChange(fila.uid, it) },
                            enabled = enabled
                        )
                    }
                    Box(modifier = Modifier.weight(0.7f)) {
                        CampoTexto(
                            valor = fila.cantidad,
                            onValorChange = { acciones.onFilaCantidadChange(fila.uid, it) },
                            etiqueta = "Cantidad",
                            enabled = enabled,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    IconButton(
                        onClick = { acciones.onQuitarFila(fila.uid) },
                        enabled = enabled
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Quitar torta")
                    }
                }
            }

            TextButton(onClick = acciones.onAgregarFila, enabled = enabled) {
                Text("Agregar tamaño de torta")
            }

            val suma = item.sumaDistribucion()
            Text(
                text = "Suma: $suma de $porciones porciones",
                style = MaterialTheme.typography.bodyMedium,
                color = if (suma == porciones) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )

            item.errores.distribucion?.let { mensaje ->
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
