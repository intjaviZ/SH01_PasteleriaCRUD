package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.AgrupacionVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.GrupoVentas
import java.math.BigDecimal

/**
 * Barras horizontales hechas con Box (sin librería de gráficas): la etiqueta cabe completa
 * aunque sea larga ("Semana 45 · 2026" o el nombre de un pastel) y no hay dependencias nuevas.
 */
@Composable
fun GraficaBarrasVentas(
    grupos: List<GrupoVentas>,
    agruparPor: AgrupacionVentas,
    modifier: Modifier = Modifier
) {
    val mayor = grupos.maxByOrNull { it.totalVentas }
    val maximo = mayor?.totalVentas ?: BigDecimal.ZERO

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Ventas por ${agruparPor.etiqueta().lowercase()}",
                style = MaterialTheme.typography.titleMedium
            )

            if (mayor != null && maximo > BigDecimal.ZERO) {
                Text(
                    text = "Mayor venta: ${etiquetaLegible(mayor.etiqueta, agruparPor)} (${mayor.totalVentas.comoMoneda()})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            grupos.forEach { grupo ->
                FilaBarra(grupo = grupo, agruparPor = agruparPor, maximo = maximo)
            }
        }
    }
}

@Composable
private fun FilaBarra(
    grupo: GrupoVentas,
    agruparPor: AgrupacionVentas,
    maximo: BigDecimal
) {
    // Proporción respecto a la barra más larga: la mayor ocupa todo el ancho.
    val proporcion = if (maximo.signum() > 0) {
        (grupo.totalVentas.toFloat() / maximo.toFloat()).coerceIn(0f, 1f)
    } else 0f

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = etiquetaLegible(grupo.etiqueta, agruparPor),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = grupo.totalVentas.comoMoneda(),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.outlineVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(proporcion)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        Text(
            text = "${grupo.totalPedidos} pedidos · ${grupo.totalPorciones} porciones",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
