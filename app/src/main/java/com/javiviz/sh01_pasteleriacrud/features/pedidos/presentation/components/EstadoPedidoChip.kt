package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido

fun EstadoPedido.etiqueta(): String = when (this) {
    EstadoPedido.ORDENADO -> "Ordenado"
    EstadoPedido.PAGADO -> "Pagado"
    EstadoPedido.ENTREGADO -> "Entregado"
    EstadoPedido.CANCELADO -> "Cancelado"
}

@Composable
fun EstadoPedidoChip(estado: EstadoPedido, modifier: Modifier = Modifier) {
    val colores = MaterialTheme.colorScheme
    val fondo = when (estado) {
        EstadoPedido.ORDENADO -> colores.tertiaryContainer
        EstadoPedido.PAGADO -> colores.primaryContainer
        EstadoPedido.ENTREGADO -> colores.secondaryContainer
        EstadoPedido.CANCELADO -> colores.errorContainer
    }
    val contenido = when (estado) {
        EstadoPedido.ORDENADO -> colores.onTertiaryContainer
        EstadoPedido.PAGADO -> colores.onPrimaryContainer
        EstadoPedido.ENTREGADO -> colores.onSecondaryContainer
        EstadoPedido.CANCELADO -> colores.onErrorContainer
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = fondo,
        contentColor = contenido
    ) {
        Text(
            text = estado.etiqueta(),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
