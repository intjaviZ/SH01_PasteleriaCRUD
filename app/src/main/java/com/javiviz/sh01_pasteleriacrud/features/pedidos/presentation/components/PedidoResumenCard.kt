package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.comoFechaHora
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.core.ui.esHoy
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.PedidoResumen

/**
 * Tarjeta de la lista. Misma base para los dos roles; la repostera además ve
 * de qué cliente es el pedido y qué entregas son para hoy.
 */
@Composable
fun PedidoResumenCard(
    pedido: PedidoResumen,
    rol: Rol,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val esRepostera = rol == Rol.REPOSTERA
    val pendiente = pedido.estado == EstadoPedido.ORDENADO || pedido.estado == EstadoPedido.PAGADO
    val entregaHoy = esRepostera && pendiente && pedido.fechaEntregaEsperada.esHoy()

    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pedido #${pedido.id}", style = MaterialTheme.typography.titleMedium)
                EstadoPedidoChip(pedido.estado)
            }

            if (esRepostera) {
                Text(text = "Cliente #${pedido.clienteId}", style = MaterialTheme.typography.bodyMedium)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Entrega: ${pedido.fechaEntregaEsperada.comoFechaHora()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (entregaHoy) {
                    Spacer(Modifier.width(8.dp))
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text(
                            text = "HOY",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (pedido.totalItems == 1) "1 pastel" else "${pedido.totalItems} pasteles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = pedido.totalEstimado.comoMoneda(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
