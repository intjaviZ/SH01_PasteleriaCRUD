package com.javiviz.sh01_pasteleriacrud.core.network

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.javiviz.sh01_pasteleriacrud.core.storage.TokenProvider
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/** Factory que arma OkHttp (con interceptor) + Retrofit + kotlinx.serialization. */
object RetrofitFactory {

    fun create(baseUrl: String, json: Json, tokenProvider: TokenProvider): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenProvider))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
