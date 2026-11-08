package com.javiviz.sh01_pasteleriacrud.features.login.data.repository

import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import com.javiviz.sh01_pasteleriacrud.core.storage.SessionStorage
import com.javiviz.sh01_pasteleriacrud.features.login.data.datasource.LoginRemoteDataSource
import com.javiviz.sh01_pasteleriacrud.features.login.data.dto.LoginRequestDto
import com.javiviz.sh01_pasteleriacrud.features.login.data.mapper.toSesion
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource,
    private val sessionStorage: SessionStorage
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<Sesion> {
        val sesion = remoteDataSource.login(LoginRequestDto(email.trim(), password))
            .mapCatching { it.toSesion() }
            .getOrElse { return Result.failure(it) }

        sessionStorage.guardar(sesion)
        return Result.success(sesion)
    }

    override fun observarSesion(): Flow<Sesion?> = sessionStorage.observarSesion()

    override suspend fun cerrarSesion() = sessionStorage.limpiar()
}
