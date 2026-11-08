package com.javiviz.sh01_pasteleriacrud.app

import android.content.Context
import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.core.network.RetrofitFactory
import com.javiviz.sh01_pasteleriacrud.core.storage.DataStoreSessionStorage
import com.javiviz.sh01_pasteleriacrud.core.storage.SessionStorage
import com.javiviz.sh01_pasteleriacrud.core.storage.sessionDataStore
import com.javiviz.sh01_pasteleriacrud.features.catalogo.di.CatalogoModule
import com.javiviz.sh01_pasteleriacrud.features.login.di.LoginModule
import com.javiviz.sh01_pasteleriacrud.features.pedidos.di.PedidosModule
import com.javiviz.sh01_pasteleriacrud.features.reportes.di.ReportesModule
import com.javiviz.sh01_pasteleriacrud.features.signup.di.SignupModule
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

/** Raíz de composición: aquí se simula/configura la conexión a la API. */
class AppModule(context: Context) {

    private val appContext = context.applicationContext

    // 10.0.2.2 = localhost de tu PC visto desde el emulador. Debe terminar en "/".
    private val baseUrl = "http://10.0.2.2:3000/api/v1/"

    val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    val sessionStorage: SessionStorage by lazy {
        DataStoreSessionStorage(appContext.sessionDataStore)
    }

    val apiCaller: ApiCaller by lazy { ApiCaller(json) }

    val retrofit: Retrofit by lazy {
        RetrofitFactory.create(baseUrl, json, sessionStorage)
    }

    val loginModule: LoginModule by lazy { LoginModule(this) }
    val signupModule: SignupModule by lazy { SignupModule(this) }
    val catalogoModule: CatalogoModule by lazy { CatalogoModule(this) }
    val pedidosModule: PedidosModule by lazy { PedidosModule(this) }
    val reportesModule: ReportesModule by lazy { ReportesModule(this) }
}
