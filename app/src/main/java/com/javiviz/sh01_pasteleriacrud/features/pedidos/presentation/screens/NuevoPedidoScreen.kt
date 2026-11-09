package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BotonPrincipal
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoTexto
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoCargando
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoError
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.ItemFormAcciones
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.ItemPedidoFormulario
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.SelectorFechaEntrega
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.NuevoPedidoViewModel

/** Formulario de pedido (solo CLIENTE; la ruta está protegida en MainNavHost). */
@Composable
fun NuevoPedidoScreen(
    onPedidoCreado: (Int) -> Unit,
    viewModel: NuevoPedidoViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.pedidosModule.nuevoPedidoViewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val config = uiState.config
    val errorCatalogo = uiState.errorCatalogo

    LaunchedEffect(uiState.pedidoCreadoId) {
        uiState.pedidoCreadoId?.let { id ->
            onPedidoCreado(id)
            viewModel.consumirPedidoCreado()
        }
    }

    when {
        uiState.cargandoCatalogo -> EstadoCargando()

        errorCatalogo != null -> EstadoError(
            mensaje = errorCatalogo,
            onReintentar = viewModel::cargarCatalogo
        )

        config == null -> EstadoError(
            mensaje = "No se pudo cargar la configuración de tamaños.",
            onReintentar = viewModel::cargarCatalogo
        )

        else -> Column(modifier = Modifier
            .fillMaxSize()
            .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SelectorFechaEntrega(
                        fecha = uiState.fechaEntrega,
                        hora = uiState.horaEntrega,
                        errores = uiState.erroresFecha,
                        margenHoras = config.margenMinimoEntregaHoras,
                        enabled = !uiState.enviando,
                        onFechaSeleccionada = viewModel::onFechaSeleccionada,
                        onHoraSeleccionada = viewModel::onHoraSeleccionada
                    )
                }

                item {
                    CampoTexto(
                        valor = uiState.notas,
                        onValorChange = viewModel::onNotasChange,
                        etiqueta = "Notas de entrega (opcional)",
                        enabled = !uiState.enviando
                    )
                }

                itemsIndexed(uiState.items, key = { _, item -> item.uid }) { indice, item ->
                    val uid = item.uid
                    ItemPedidoFormulario(
                        indice = indice,
                        item = item,
                        pasteles = uiState.pasteles,
                        rellenos = uiState.rellenos,
                        config = config,
                        puedeQuitar = uiState.items.size > 1,
                        enabled = !uiState.enviando,
                        acciones = ItemFormAcciones(
                            onQuitar = { viewModel.quitarItem(uid) },
                            onPastelChange = { viewModel.onPastelChange(uid, it) },
                            onRellenoChange = { viewModel.onRellenoChange(uid, it) },
                            onPorcionesChange = { viewModel.onPorcionesChange(uid, it) },
                            onColorPrimarioChange = { viewModel.onColorPrimarioChange(uid, it) },
                            onColorSecundarioChange = { viewModel.onColorSecundarioChange(uid, it) },
                            onDedicatoriaChange = { viewModel.onDedicatoriaChange(uid, it) },
                            onDividirChange = { viewModel.onDividirChange(uid, it) },
                            onAgregarFila = { viewModel.agregarFila(uid) },
                            onQuitarFila = { viewModel.quitarFila(uid, it) },
                            onFilaPorcionesChange = { filaUid, porciones ->
                                viewModel.onFilaPorcionesChange(uid, filaUid, porciones)
                            },
                            onFilaCantidadChange = { filaUid, texto ->
                                viewModel.onFilaCantidadChange(uid, filaUid, texto)
                            }
                        )
                    )
                }

                item {
                    OutlinedButton(
                        onClick = viewModel::agregarItem,
                        enabled = !uiState.enviando,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar otro pastel")
                    }
                }

                item {
                    Text(
                        text = "El pago se realiza en efectivo en el local.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(shadowElevation = 4.dp) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.errorGeneral?.let { BannerMensaje(texto = it, esError = true) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total estimado", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = uiState.totalEstimado.comoMoneda(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    BotonPrincipal(
                        texto = "Confirmar pedido",
                        onClick = viewModel::onEnviarClick,
                        cargando = uiState.enviando
                    )
                }
            }
        }
    }
}
