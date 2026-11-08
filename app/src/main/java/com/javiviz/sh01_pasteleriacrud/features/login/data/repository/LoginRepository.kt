package com.javiviz.sh01_pasteleriacrud.features.login.data.repository

import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import kotlinx.coroutines.flow.Flow

/** Lo único que conocerá el ViewModel: sin DTOs, sin Retrofit, sin DataStore. */
interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Sesion>
    fun observarSesion(): Flow<Sesion?>
    suspend fun cerrarSesion()
}
