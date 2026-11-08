package com.javiviz.sh01_pasteleriacrud.features.login.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginRequestDto
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginResponseDto

class LoginRemoteDataSourceImpl(
    private val api: LoginApiService,
    private val apiCaller: ApiCaller
) : LoginRemoteDataSource {

    override suspend fun login(request: LoginRequestDto): Result<LoginResponseDto> =
        apiCaller.execute { api.login(request) }
}
