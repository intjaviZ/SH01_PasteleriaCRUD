package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.aColorOrNull
import com.javiviz.sh01_pasteleriacrud.core.ui.comoFechaHora
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.ItemPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.Pedido

/** Datos del pedido. La repostera además ve nombre y teléfono del cliente. */
@Composable
fun PedidoDetalleContenido(
    pedido: Pedido,
    rol: Rol,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pedido #${pedido.id}", style = MaterialTheme.typography.headlineSmall)
            EstadoPedidoChip(pedido.estado)
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (rol == Rol.REPOSTERA) {
                    FilaDato("Cliente", pedido.cliente?.nombre ?: "Cliente #${pedido.clienteId}")
                    pedido.cliente?.telefono?.let { FilaDato("Teléfono", it) }
                }
                FilaDato("Fecha de entrega", pedido.fechaEntregaEsperada.comoFechaHora())
                FilaDato("Creado", pedido.fechaCreacion.comoFechaHora())
                FilaDato("Pago", "Efectivo en el local")
                pedido.notasEntrega?.takeIf { it.isNotBlank() }?.let { FilaDato("Notas", it) }
                FilaDato("Porciones totales", pedido.totalPorciones().toString())
                HorizontalDivider()
                FilaDato("Total estimado", pedido.totalEstimado.comoMoneda(), destacado = true)
            }
        }

        Text(text = "Pasteles", style = MaterialTheme.typography.titleMedium)
        pedido.items.forEach { item -> ItemPedidoCard(item) }
    }
}

@Composable
private fun ItemPedidoCard(item: ItemPedido, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.pastelNombre ?: "Pastel #${item.pastelId}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(text = item.subtotal().comoMoneda(), style = MaterialTheme.typography.titleSmall)
            }

            Text(
                text = "${item.porcionesTotales} porciones · ${item.precioUnitario.comoMoneda()} c/u",
                style = MaterialTheme.typography.bodyMedium
            )

            val relleno = item.rellenoNombre ?: item.rellenoId?.let { "Relleno #$it" }
            if (relleno != null) {
                Text(text = "Relleno: $relleno", style = MaterialTheme.typography.bodyMedium)
            }

            if (item.distribucion.isNotEmpty()) {
                val detalle = item.distribucion.joinToString(" · ") { "${it.cantidad} × ${it.porcionesTorta} porc." }
                Text(text = "Tortas: $detalle", style = MaterialTheme.typography.bodyMedium)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Colores: ", style = MaterialTheme.typography.bodyMedium)
                MuestraColor(item.colorPrimario)
                MuestraColor(item.colorSecundario, modifier = Modifier.padding(start = 6.dp))
            }

            if (item.dedicatoria.isNotBlank()) {
                Text(
                    text = "Dedicatoria: “${item.dedicatoria}”",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun MuestraColor(hex: String, modifier: Modifier = Modifier) {
    val color = hex.aColorOrNull() ?: Color.Transparent
    Box(
        modifier = modifier
            .size(20.dp)
            .background(color, CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
    )
}

@Composable
private fun FilaDato(etiqueta: String, valor: String, destacado: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = if (destacado) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal
        )
    }
}
