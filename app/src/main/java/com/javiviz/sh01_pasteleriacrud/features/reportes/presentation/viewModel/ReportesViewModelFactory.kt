package com.javiviz.sh01_pasteleriacrud.features.reportes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository.ReportesRepository

class ReportesViewModelFactory(
    private val repository: ReportesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportesViewModel::class.java)) {
            return ReportesViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
