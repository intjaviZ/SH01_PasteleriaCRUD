package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoCargando
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoError
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoVacio
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.FiltroEstadosRow
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.PedidoResumenCard
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.PedidosViewModel

/**
 * Lista de pedidos para los dos roles:
 *  - CLIENTE: "Mis pedidos", botón para crear uno nuevo.
 *  - REPOSTERA: todos los pedidos, con el cliente y la marca de entregas de hoy.
 */
@Composable
fun PedidosScreen(
    rol: Rol,
    refrescar: Boolean,
    onRefrescado: () -> Unit,
    onPedidoClick: (Int) -> Unit,
    onNuevoPedido: () -> Unit,
    viewModel: PedidosViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.pedidosModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val esCliente = rol == Rol.CLIENTE
    val error = uiState.error

    // Cuando el detalle o el formulario modifican un pedido, avisan por aquí para recargar la lista.
    LaunchedEffect(refrescar) {
        if (refrescar) {
            viewModel.recargar()
            onRefrescado()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)) {
                Text(
                    text = if (esCliente) "Mis pedidos" else "Pedidos de clientes",
                    style = MaterialTheme.typography.titleLarge
                )
                if (!uiState.cargando && error == null) {
                    Text(
                        text = if (uiState.totalRegistros == 1) "1 pedido" else "${uiState.totalRegistros} pedidos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            FiltroEstadosRow(
                seleccionado = uiState.estadoFiltro,
                onSeleccionar = viewModel::onFiltroEstadoChange
            )
            Spacer(Modifier.height(8.dp))

            if (uiState.cargando && uiState.pedidos.isNotEmpty()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            when {
                uiState.cargando && uiState.pedidos.isEmpty() -> EstadoCargando()

                error != null && uiState.pedidos.isEmpty() ->
                    EstadoError(mensaje = error, onReintentar = viewModel::recargar)

                uiState.pedidos.isEmpty() -> {
                    if (esCliente && uiState.estadoFiltro == null) {
                        EstadoVacio(
                            titulo = "Aún no tienes pedidos",
                            mensaje = "Elige un pastel, sus porciones y la fecha de entrega.",
                            accion = { Button(onClick = onNuevoPedido) { Text("Hacer mi primer pedido") } }
                        )
                    } else {
                        EstadoVacio(
                            titulo = "Sin resultados",
                            mensaje = "No hay pedidos con este filtro."
                        )
                    }
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 4.dp,
                        bottom = if (esCliente) 88.dp else 16.dp // espacio para el botón flotante
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.pedidos, key = { it.id }) { pedido ->
                        PedidoResumenCard(
                            pedido = pedido,
                            rol = rol,
                            onClick = { onPedidoClick(pedido.id) }
                        )
                    }

                    if (uiState.hayMas) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                if (uiState.cargandoMas) {
                                    CircularProgressIndicator()
                                } else {
                                    OutlinedButton(onClick = viewModel::cargarMas) { Text("Cargar más") }
                                }
                            }
                        }
                    }

                    if (error != null) {
                        item {
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        if (esCliente) {
            ExtendedFloatingActionButton(
                onClick = onNuevoPedido,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nuevo pedido") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}
