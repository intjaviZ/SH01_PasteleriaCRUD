package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.repository.CatalogoRepository

class CatalogoViewModelFactory(
    private val repository: CatalogoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogoViewModel::class.java)) {
            return CatalogoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
