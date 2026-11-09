package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BotonPrincipal
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoCargando
import com.javiviz.sh01_pasteleriacrud.core.ui.components.EstadoError
import com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.components.PastelCard
import com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.components.RellenosSeccion
import com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.components.TamanosCard
import com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.viewModel.CatalogoViewModel

/**
 * Misma información para ambos roles, distinta intención:
 *  - CLIENTE: explora y tiene el botón "Hacer un pedido".
 *  - REPOSTERA: vista de consulta, sin botón de pedido.
 */
@Composable
fun CatalogoScreen(
    rol: Rol,
    onHacerPedido: () -> Unit,
    viewModel: CatalogoViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.catalogoModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(true) }
    val error = uiState.error

    when {
        uiState.cargando -> EstadoCargando()
        error != null -> EstadoError(mensaje = error, onReintentar = viewModel::cargar)
        else -> Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.config?.let { config ->
                    item {
                        if (mostrarDialogo) {
                            AlertDialog(
                                onDismissRequest = { mostrarDialogo = false },
                                title = { Text(text = "Cómo pedir", style = MaterialTheme.typography.titleMedium) },
                                text = { TamanosCard(config = uiState.config!!) },
                                confirmButton = {
                                    TextButton ( onClick = { mostrarDialogo = false } ) {
                                        Text("Entendido")
                                    }
                                }
                            )
                        }
                    }
                }

                item { Text(text = "Pasteles", style = MaterialTheme.typography.titleLarge) }
                items(uiState.pasteles, key = { it.id }) { pastel -> PastelCard(pastel) }

                item { RellenosSeccion(uiState.rellenos) }

            }

            if (rol == Rol.CLIENTE) {
                Surface(shadowElevation = 4.dp) {
                    BotonPrincipal(
                        texto = "Hacer un pedido",
                        onClick = onHacerPedido,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
