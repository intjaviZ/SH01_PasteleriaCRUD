package com.javiviz.sh01_pasteleriacrud

import android.app.Application
import com.javiviz.sh01_pasteleriacrud.app.AppModule

class PasteleriaApplication : Application() {

    lateinit var appModule: AppModule
        private set

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)
    }
}