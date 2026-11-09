package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javiviz.sh01_pasteleriacrud.PasteleriaApplication
import com.javiviz.sh01_pasteleriacrud.core.ui.comoFecha
import com.javiviz.sh01_pasteleriacrud.core.ui.components.BannerMensaje
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components.FiltrosVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components.GraficaBarrasVentas
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.components.ResumenVentasSeccion
import com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.viewModel.ReportesViewModel

/**
 * Estadísticas de ventas. Solo REPOSTERA: la ruta pasa por RutaProtegida en MainNavHost
 * y la API además responde 403 a cualquier otro rol.
 */
@Composable
fun ReportesScreen(
    viewModel: ReportesViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as PasteleriaApplication)
            .appModule.reportesModule.viewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val reporte = uiState.reporte
    val error = uiState.error

    // Todo va en un solo LazyColumn: los filtros se desplazan junto con los resultados.
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FiltrosVentas(
                fechaInicio = uiState.fechaInicio,
                fechaFin = uiState.fechaFin,
                atajo = uiState.atajo,
                agruparPor = uiState.agruparPor,
                errorRango = uiState.errorRango,
                onAtajoSeleccionado = viewModel::onAtajoSeleccionado,
                onFechaInicioChange = viewModel::onFechaInicioChange,
                onFechaFinChange = viewModel::onFechaFinChange,
                onAgrupacionChange = viewModel::onAgrupacionChange
            )
        }

        if (uiState.cargando) {
            item {
                if (reporte == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }

        if (error != null) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BannerMensaje(texto = error, esError = true)
                    TextButton(onClick = viewModel::cargar) { Text("Reintentar") }
                }
            }
        }

        if (reporte != null) {
            item {
                Text(
                    text = "Del ${reporte.fechaInicio.comoFecha()} al ${reporte.fechaFin.comoFecha()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item { ResumenVentasSeccion(reporte.resumen) }

            item {
                if (reporte.grupos.isEmpty()) {
                    BannerMensaje(
                        texto = "No hay ventas registradas en este periodo.",
                        esError = false
                    )
                } else {
                    GraficaBarrasVentas(grupos = reporte.grupos, agruparPor = reporte.agruparPor)
                }
            }

            item {
                // Los datos no se actualizan solos: sirve para ver pagos recién registrados.
                TextButton(onClick = viewModel::cargar) { Text("Actualizar") }
            }
        }
    }
}
