package com.javiviz.sh01_pasteleriacrud.features.signup.data.repository

import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.core.network.mapper.toUsuario
import com.javiviz.sh01_pasteleriacrud.features.signup.data.datasource.SignupRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.signup.data.dto.RegisterRequestDto

class SignupRepositoryImpl(
    private val remoteDataSource: SignupRemoteDataSource
) : SignupRepository {

    override suspend fun registrar(
        nombre: String,
        telefono: String,
        email: String,
        password: String
    ): Result<Usuario> =
        remoteDataSource
            .register(RegisterRequestDto(nombre.trim(), telefono.trim(), email.trim(), password))
            .mapCatching { it.toUsuario() }
}
