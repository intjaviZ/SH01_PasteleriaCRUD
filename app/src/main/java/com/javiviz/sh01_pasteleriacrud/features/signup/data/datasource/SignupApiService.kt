package com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.dto.UsuarioDto
import com.javiviz.sh01_pasteleriacrud.features.signup.data.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto): UsuarioDto
}
