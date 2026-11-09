package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.viewModel

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno

data class CatalogoUiState(
    val cargando: Boolean = true,
    val pasteles: List<PastelCatalogo> = emptyList(),
    val rellenos: List<Relleno> = emptyList(),
    val config: ConfiguracionTamanos? = null,
    val error: String? = null
)
