package com.javiviz.sh01_pasteleriacrud.core.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.model.Sesion
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.Instant

/** Única instancia de DataStore por proceso (el delegate garantiza el singleton). */
val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class DataStoreSessionStorage(
    private val dataStore: DataStore<Preferences>
) : SessionStorage {

    private object Keys {
        val TOKEN = stringPreferencesKey("access_token")
        val EXPIRA_EN = longPreferencesKey("expira_en_epoch_seconds")
        val USUARIO_ID = intPreferencesKey("usuario_id")
        val NOMBRE = stringPreferencesKey("usuario_nombre")
        val TELEFONO = stringPreferencesKey("usuario_telefono")
        val EMAIL = stringPreferencesKey("usuario_email")
        val ROL = stringPreferencesKey("usuario_rol")
    }

    private val datos: Flow<Preferences> = dataStore.data.catch { error ->
        if (error is IOException) emit(emptyPreferences()) else throw error
    }

    override fun observarSesion(): Flow<Sesion?> = datos.map { it.aSesion() }

    override suspend fun obtenerSesion(): Sesion? = observarSesion().first()

    override suspend fun obtenerToken(): String? = datos.first()[Keys.TOKEN]

    override suspend fun guardar(sesion: Sesion) {
        dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = sesion.accessToken
            prefs[Keys.EXPIRA_EN] = sesion.expiraEn.epochSecond
            prefs[Keys.USUARIO_ID] = sesion.usuario.id
            prefs[Keys.NOMBRE] = sesion.usuario.nombre
            prefs[Keys.EMAIL] = sesion.usuario.email
            prefs[Keys.ROL] = sesion.usuario.rol.name
            val telefono = sesion.usuario.telefono
            if (telefono != null) prefs[Keys.TELEFONO] = telefono else prefs.remove(Keys.TELEFONO)
        }
    }

    override suspend fun limpiar() {
        dataStore.edit { it.clear() }
    }

    private fun Preferences.aSesion(): Sesion? {
        val token = this[Keys.TOKEN] ?: return null
        val expiraEn = this[Keys.EXPIRA_EN] ?: return null
        val id = this[Keys.USUARIO_ID] ?: return null
        val nombre = this[Keys.NOMBRE] ?: return null
        val email = this[Keys.EMAIL] ?: return null
        val rol = Rol.entries.firstOrNull { it.name == this[Keys.ROL] } ?: return null

        return Sesion(
            accessToken = token,
            expiraEn = Instant.ofEpochSecond(expiraEn),
            usuario = Usuario(id, nombre, this[Keys.TELEFONO], email, rol)
        )
    }
}
