package com.javiviz.sh01_pasteleriacrud.core.network

import com.javiviz.sh01_pasteleriacrud.core.storage.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.encodedPath.contains("/auth/")) {
            return chain.proceed(request)
        }

        val token = runBlocking { tokenProvider.obtenerToken() }
            ?: return chain.proceed(request)

        val autenticada = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(autenticada)
    }
}
