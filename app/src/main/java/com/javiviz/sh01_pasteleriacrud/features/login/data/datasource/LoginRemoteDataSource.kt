package com.javiviz.sh01_pasteleriacrud.features.login.data.datasource

import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginRequestDto
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginResponseDto

interface LoginRemoteDataSource {
    suspend fun login(request: LoginRequestDto): Result<LoginResponseDto>
}
