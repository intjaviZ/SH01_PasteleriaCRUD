package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                    item { TamanosCard(config) }
                }

                item { Text(text = "Pasteles", style = MaterialTheme.typography.titleLarge) }
                items(uiState.pasteles, key = { it.id }) { pastel -> PastelCard(pastel) }

                item { RellenosSeccion(uiState.rellenos) }

                if (rol == Rol.REPOSTERA) {
                    item {
                        Text(
                            text = "Vista de consulta: los clientes eligen de este catálogo al hacer su pedido.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
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
