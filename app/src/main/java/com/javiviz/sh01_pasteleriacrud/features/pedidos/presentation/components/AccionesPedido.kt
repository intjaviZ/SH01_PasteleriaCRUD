package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BotonPrincipal
import com.javiviz.sh01_pasteleriacrud.features.pedidos.data.model.EstadoPedido

/**
 * Botones según rol y estado, usando las reglas de EstadoPedido:
 *  - REPOSTERA: pagar (ORDENADO), entregar (PAGADO) y cancelar (ORDENADO o PAGADO).
 *  - CLIENTE: solo cancelar, y solo mientras esté ORDENADO.
 * Si no hay ninguna acción disponible, no dibuja nada.
 */
@Composable
fun AccionesPedido(
    estado: EstadoPedido,
    rol: Rol,
    procesando: Boolean,
    onPagar: () -> Unit,
    onEntregar: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val puedePagar = rol == Rol.REPOSTERA && estado.puedePagar()
    val puedeEntregar = rol == Rol.REPOSTERA && estado.puedeEntregar()
    val puedeCancelar = estado.puedeCancelarPor(rol)

    if (!puedePagar && !puedeEntregar && !puedeCancelar) return

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (puedePagar) {
            BotonPrincipal(texto = "Registrar pago", onClick = onPagar, enabled = !procesando)
        }
        if (puedeEntregar) {
            BotonPrincipal(texto = "Marcar como entregado", onClick = onEntregar, enabled = !procesando)
        }
        if (puedeCancelar) {
            OutlinedButton(
                onClick = onCancelar,
                enabled = !procesando,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar pedido")
            }
        }
    }
}
