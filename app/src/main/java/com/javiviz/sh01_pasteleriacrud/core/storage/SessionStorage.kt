package com.javiviz.sh01_pasteleriacrud.core.storage

import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import kotlinx.coroutines.flow.Flow

/** Contrato mínimo que necesita el interceptor (Interface Segregation). */
interface TokenProvider {
    suspend fun obtenerToken(): String?
}

/** Abstracción de la persistencia de sesión; DataStore es solo un detalle de implementación. */
interface SessionStorage : TokenProvider {
    fun observarSesion(): Flow<Sesion?>
    suspend fun obtenerSesion(): Sesion?
    suspend fun guardar(sesion: Sesion)
    suspend fun limpiar()
}
