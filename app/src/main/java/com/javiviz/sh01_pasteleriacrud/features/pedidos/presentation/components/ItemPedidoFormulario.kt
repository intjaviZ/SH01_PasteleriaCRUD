package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoSelector
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoTexto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.ItemFormUi
import java.math.BigDecimal

/** Todo lo que un item del formulario puede reportar; la pantalla lo conecta al ViewModel con el uid del item. */
data class ItemFormAcciones(
    val onQuitar: () -> Unit,
    val onPastelChange: (Int) -> Unit,
    val onRellenoChange: (Int) -> Unit,
    val onPorcionesChange: (Int) -> Unit,
    val onColorPrimarioChange: (String) -> Unit,
    val onColorSecundarioChange: (String) -> Unit,
    val onDedicatoriaChange: (String) -> Unit,
    val onDividirChange: (Boolean) -> Unit,
    val onAgregarFila: () -> Unit,
    val onQuitarFila: (Int) -> Unit,
    val onFilaPorcionesChange: (Int, Int) -> Unit,
    val onFilaCantidadChange: (Int, String) -> Unit
)

@Composable
fun ItemPedidoFormulario(
    indice: Int,
    item: ItemFormUi,
    pasteles: List<PastelCatalogo>,
    rellenos: List<Relleno>,
    config: ConfiguracionTamanos,
    puedeQuitar: Boolean,
    enabled: Boolean,
    acciones: ItemFormAcciones,
    modifier: Modifier = Modifier
) {
    val pastel = pasteles.firstOrNull { it.id == item.pastelId }
    val porciones = item.porciones

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pastel ${indice + 1}", style = MaterialTheme.typography.titleMedium)
                if (puedeQuitar) {
                    TextButton(onClick = acciones.onQuitar, enabled = enabled) { Text("Quitar") }
                }
            }

            CampoSelector(
                etiqueta = "Pastel",
                opciones = pasteles,
                seleccionado = pastel,
                etiquetaDe = { "${it.nombre} · ${it.precioBasePorcion.comoMoneda()}/porción" },
                onSeleccion = { acciones.onPastelChange(it.id) },
                error = item.errores.pastel,
                enabled = enabled
            )

            // Regla del contrato: el relleno solo aplica si el pastel lo permite.
            if (pastel != null) {
                if (pastel.permiteRelleno) {
                    CampoSelector(
                        etiqueta = "Relleno",
                        opciones = rellenos,
                        seleccionado = rellenos.firstOrNull { it.id == item.rellenoId },
                        etiquetaDe = { it.nombre },
                        onSeleccion = { acciones.onRellenoChange(it.id) },
                        error = item.errores.relleno,
                        enabled = enabled
                    )
                } else {
                    Text(
                        text = "Este pastel no lleva relleno.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            CampoSelector(
                etiqueta = "Porciones",
                opciones = config.opcionesPorciones,
                seleccionado = porciones,
                etiquetaDe = { "$it porciones" },
                onSeleccion = acciones.onPorcionesChange,
                error = item.errores.porciones,
                enabled = enabled
            )

            CampoSelector(
                etiqueta = "Color principal",
                opciones = PaletaColores.colores,
                seleccionado = PaletaColores.deHex(item.colorPrimario),
                etiquetaDe = { it.nombre },
                onSeleccion = { acciones.onColorPrimarioChange(it.hex) },
                error = item.errores.colorPrimario,
                enabled = enabled
            )

            CampoSelector(
                etiqueta = "Color secundario",
                opciones = PaletaColores.colores,
                seleccionado = PaletaColores.deHex(item.colorSecundario),
                etiquetaDe = { it.nombre },
                onSeleccion = { acciones.onColorSecundarioChange(it.hex) },
                error = item.errores.colorSecundario,
                enabled = enabled
            )

            CampoTexto(
                valor = item.dedicatoria,
                onValorChange = acciones.onDedicatoriaChange,
                etiqueta = "Dedicatoria (${item.dedicatoria.length}/${config.dedicatoriaMaxCaracteres})",
                error = item.errores.dedicatoria,
                enabled = enabled
            )

            if (porciones != null && porciones >= config.umbralDistribucion) {
                DistribucionEditor(
                    item = item,
                    porciones = porciones,
                    config = config,
                    enabled = enabled,
                    acciones = acciones
                )
            }

            if (pastel != null && porciones != null) {
                Text(
                    text = "Subtotal: ${pastel.precioBasePorcion.multiply(BigDecimal(porciones)).comoMoneda()}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
