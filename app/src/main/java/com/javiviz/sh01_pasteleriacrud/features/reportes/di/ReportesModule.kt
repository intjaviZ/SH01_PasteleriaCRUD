package com.javiviz.sh01_pasteleriacrud.features.reportes.di

import com.javiviz.sh01_pasteleriacrud.app.AppModule
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource.ReportesApiService
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource.ReportesRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.datasource.ReportesRemoteDataSourceImpl
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository.ReportesRepository
import com.javiviz.sh01_pasteleriacrud.features.reportes.data.repository.ReportesRepositoryImpl

class ReportesModule(private val appModule: AppModule) {

    private fun createApiService(): ReportesApiService =
        appModule.retrofit.create(ReportesApiService::class.java)

    private fun createRemoteDataSource(): ReportesRemoteDataSource =
        ReportesRemoteDataSourceImpl(createApiService(), appModule.apiCaller)

    fun createRepository(): ReportesRepository =
        ReportesRepositoryImpl(createRemoteDataSource())

    val repository: ReportesRepository by lazy { createRepository() }
}
