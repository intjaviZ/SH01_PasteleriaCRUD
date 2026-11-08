package com.javiviz.sh01_pasteleriacrud.features.main.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.viewModel.MainViewModelFactory

/** Main no tiene capa data propia: usa el repositorio de login para observar la sesión. */
class MainModule(private val appModule: AppModule) {

    fun createViewModelFactory(): MainViewModelFactory =
        MainViewModelFactory(appModule.loginModule.repository)

    val viewModelFactory: MainViewModelFactory by lazy { createViewModelFactory() }
}
