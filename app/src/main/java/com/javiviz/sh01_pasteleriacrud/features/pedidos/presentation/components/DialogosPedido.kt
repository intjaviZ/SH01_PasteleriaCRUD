package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.ui.comoMoneda
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.core.ui.components.CampoTexto
import java.math.BigDecimal

/** El error de la operación (si lo hay) se muestra dentro del diálogo, sin cerrarlo. */

@Composable
fun PagarDialog(
    total: BigDecimal,
    error: String?,
    procesando: Boolean,
    onConfirmar: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var monto by rememberSaveable { mutableStateOf(total.toPlainString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar pago en efectivo") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Total del pedido: ${total.comoMoneda()}")
                CampoTexto(
                    valor = monto,
                    onValorChange = { monto = it },
                    etiqueta = "Monto recibido",
                    enabled = !procesando,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                if (error != null) BannerMensaje(texto = error, esError = true)
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmar(monto) }, enabled = !procesando) {
                Text(if (procesando) "Guardando..." else "Confirmar pago")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !procesando) { Text("Cancelar") }
        }
    )
}

@Composable
fun EntregarDialog(
    error: String?,
    procesando: Boolean,
    onConfirmar: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Marcar como entregado") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Confirma que el pedido ya fue entregado al cliente.")
                if (error != null) BannerMensaje(texto = error, esError = true)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmar, enabled = !procesando) {
                Text(if (procesando) "Guardando..." else "Confirmar entrega")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !procesando) { Text("Volver") }
        }
    )
}

@Composable
fun CancelarDialog(
    error: String?,
    procesando: Boolean,
    onConfirmar: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var motivo by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cancelar pedido") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Esta acción no se puede deshacer.")
                CampoTexto(
                    valor = motivo,
                    onValorChange = { motivo = it.take(200) },
                    etiqueta = "Motivo (opcional)",
                    enabled = !procesando
                )
                if (error != null) BannerMensaje(texto = error, esError = true)
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmar(motivo) }, enabled = !procesando) {
                Text(if (procesando) "Cancelando..." else "Cancelar pedido")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !procesando) { Text("Volver") }
        }
    )
}
