package com.javiviz.sh01_pasteleriacrud.features.signup.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource.SignupApiService
import com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource.SignupRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource.SignupRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.signup.data.repository.SignupRepository
import com.javiviz.sh01_pasteleriacrud.features.signup.data.repository.SignupRepositoryImpl

class SignupModule(private val appModule: AppModule) {

    private fun createApiService(): SignupApiService =
        appModule.retrofit.create(SignupApiService::class.java)

    private fun createRemoteDataSource(): SignupRemoteDataSource =
        SignupRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): SignupRepository =
        SignupRepositoryImpl(createRemoteDataSource())

    val repository: SignupRepository by lazy { createRepository() }

    // Cuando exista el ViewModel:
    // fun createViewModelFactory(): SignupViewModelFactory = SignupViewModelFactory(repository)
}
