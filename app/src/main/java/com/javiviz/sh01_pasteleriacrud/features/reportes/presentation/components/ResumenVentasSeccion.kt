package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.model.ResumenVentas

/** Cinco métricas; cada una toma su color del tema: morado, menta, rosa, celeste y rojo. */
@Composable
fun ResumenVentasSeccion(resumen: ResumenVentas, modifier: Modifier = Modifier) {
    val colores = MaterialTheme.colorScheme

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TarjetaMetrica(
            titulo = "Ventas totales",
            valor = resumen.totalVentas.comoMoneda(),
            fondo = colores.primaryContainer,
            contenido = colores.onPrimaryContainer,
            destacada = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaMetrica(
                titulo = "Pedidos",
                valor = resumen.totalPedidos.toString(),
                fondo = colores.secondaryContainer,
                contenido = colores.onSecondaryContainer,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            TarjetaMetrica(
                titulo = "Porciones",
                valor = resumen.totalPorciones.toString(),
                fondo = colores.tertiaryContainer,
                contenido = colores.onTertiaryContainer,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaMetrica(
                titulo = "Ticket promedio",
                valor = resumen.ticketPromedio.comoMoneda(),
                fondo = colores.surfaceVariant,
                contenido = colores.onSurfaceVariant,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            TarjetaMetrica(
                titulo = "Cancelados",
                valor = resumen.pedidosCancelados.toString(),
                fondo = colores.errorContainer,
                contenido = colores.onErrorContainer,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun TarjetaMetrica(
    titulo: String,
    valor: String,
    fondo: Color,
    contenido: Color,
    modifier: Modifier = Modifier,
    destacada: Boolean = false
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = fondo, contentColor = contenido)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = titulo, style = MaterialTheme.typography.labelLarge)
            Text(
                text = valor,
                style = if (destacada) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.titleLarge
            )
        }
    }
}
