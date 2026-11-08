package com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.ApiCaller
import com.javiviz.sh01_pasteleriacrud.core.network.dto.UsuarioDto
import com.javiviz.sh01_pasteleriacrud.features.signup.data.dto.RegisterRequestDto

class SignupRemoteDataSourceImpl(
    private val api: SignupApiService,
    private val apiCaller: ApiCaller
) : SignupRemoteDataSource {

    override suspend fun register(request: RegisterRequestDto): Result<UsuarioDto> =
        apiCaller.execute { api.register(request) }
}
