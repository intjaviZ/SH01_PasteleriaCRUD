package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido

@Composable
fun FiltroEstadosRow(
    seleccionado: EstadoPedido?,
    onSeleccionar: (EstadoPedido?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionado == null,
                onClick = { onSeleccionar(null) },
                label = { Text("Todos") }
            )
        }
        items(EstadoPedido.entries) { estado ->
            FilterChip(
                selected = seleccionado == estado,
                onClick = { onSeleccionar(estado) },
                label = { Text(estado.etiqueta()) }
            )
        }
    }
}
