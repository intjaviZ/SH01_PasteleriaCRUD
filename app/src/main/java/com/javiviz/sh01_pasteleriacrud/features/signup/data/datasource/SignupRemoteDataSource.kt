package com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource

import com.javiviz.sh01_pasteleriacrud.core.network.dto.UsuarioDto
import com.javiviz.sh01_pasteleriacrud.features.signup.data.dto.RegisterRequestDto

interface SignupRemoteDataSource {
    suspend fun register(request: RegisterRequestDto): Result<UsuarioDto>
}
