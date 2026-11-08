package com.javiviz.sh01_pasteleriacrud.features.login.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.login.data.datasource.LoginApiService
import com.javiviz.sh01_pasteleriacrud.features.login.data.datasource.LoginRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.login.data.datasource.LoginRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.login.data.repository.LoginRepository
import com.javiviz.sh01_pasteleriacrud.features.login.data.repository.LoginRepositoryImpl

/** DI manual con Factory Method: cada create...() sabe armar una pieza. */
class LoginModule(private val appModule: AppModule) {

    private fun createApiService(): LoginApiService =
        appModule.retrofit.create(LoginApiService::class.java)

    private fun createRemoteDataSource(): LoginRemoteDataSource =
        LoginRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): LoginRepository =
        LoginRepositoryImpl(createRemoteDataSource(), appModule.sessionStorage)

    val repository: LoginRepository by lazy { createRepository() }

    // Cuando exista el ViewModel:
    // fun createViewModelFactory(): LoginViewModelFactory = LoginViewModelFactory(repository)
}
