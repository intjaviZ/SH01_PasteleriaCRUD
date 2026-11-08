package com.javiviz.sh01_pasteleriacrud.features.login.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginRequestDto
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}
