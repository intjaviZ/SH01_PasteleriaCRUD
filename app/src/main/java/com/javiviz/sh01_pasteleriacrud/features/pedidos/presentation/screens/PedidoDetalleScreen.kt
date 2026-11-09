package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoCargando
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoError
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.AccionesPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.CancelarDialog
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.EntregarDialog
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.PagarDialog
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components.PedidoDetalleContenido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.DialogoPedido
import com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.viewModel.PedidoDetalleViewModel

/**
 * Detalle de un pedido para ambos roles. El contenido es el mismo;
 * las acciones disponibles dependen del rol y del estado (ver AccionesPedido).
 */
@Composable
fun PedidoDetalleScreen(
    pedidoId: Int,
    rol: Rol,
    onPedidoModificado: () -> Unit,
    viewModel: PedidoDetalleViewModel = viewModel(
        key = "pedido_$pedidoId",
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.pedidosModule.createDetalleViewModelFactory(pedidoId)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val pedido = uiState.pedido
    val error = uiState.error

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.consumirMensaje()
        }
    }

    LaunchedEffect(uiState.pedidoModificado) {
        if (uiState.pedidoModificado) {
            onPedidoModificado()
            viewModel.consumirModificado()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            pedido == null && uiState.cargando -> EstadoCargando()

            pedido == null -> EstadoError(
                mensaje = error ?: "No se pudo cargar el pedido.",
                onReintentar = viewModel::cargar
            )

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PedidoDetalleContenido(pedido = pedido, rol = rol)

                // El cliente solo puede cancelar mientras el pedido siga ORDENADO.
                if (rol == Rol.CLIENTE && pedido.estado == EstadoPedido.PAGADO) {
                    BannerMensaje(
                        texto = "Tu pedido ya está pagado. Si necesitas cancelarlo, comunícate con la repostera.",
                        esError = false
                    )
                }

                AccionesPedido(
                    estado = pedido.estado,
                    rol = rol,
                    procesando = uiState.procesando,
                    onPagar = { viewModel.abrirDialogo(DialogoPedido.PAGAR) },
                    onEntregar = { viewModel.abrirDialogo(DialogoPedido.ENTREGAR) },
                    onCancelar = { viewModel.abrirDialogo(DialogoPedido.CANCELAR) }
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

    if (pedido != null) {
        when (uiState.dialogo) {
            DialogoPedido.PAGAR -> PagarDialog(
                total = pedido.totalEstimado,
                error = uiState.errorAccion,
                procesando = uiState.procesando,
                onConfirmar = viewModel::confirmarPago,
                onDismiss = viewModel::cerrarDialogo
            )

            DialogoPedido.ENTREGAR -> EntregarDialog(
                error = uiState.errorAccion,
                procesando = uiState.procesando,
                onConfirmar = viewModel::confirmarEntrega,
                onDismiss = viewModel::cerrarDialogo
            )

            DialogoPedido.CANCELAR -> CancelarDialog(
                error = uiState.errorAccion,
                procesando = uiState.procesando,
                onConfirmar = viewModel::confirmarCancelacion,
                onDismiss = viewModel::cerrarDialogo
            )

            null -> Unit
        }
    }
}
