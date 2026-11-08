package com.javiviz.sh01_pasteleriacrud.core.network

import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import com.javiviz.sh01_pasteleriacrud.core.network.dto.ErrorResponseDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

/**
 * Ejecuta una llamada de Retrofit y traduce cualquier fallo a DomainError.
 * Una sola responsabilidad: convertir excepciones de red/HTTP en errores propios.
 * No captura CancellationException, así las corrutinas se cancelan con normalidad.
 */
class ApiCaller(private val json: Json) {

    suspend fun <T> execute(block: suspend () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: HttpException) {
            Result.failure(mapearHttp(e))
        } catch (e: IOException) {
            Result.failure(DomainError.SinConexion)
        } catch (e: SerializationException) {
            Result.failure(DomainError.Desconocido("La respuesta del servidor tiene un formato inesperado."))
        }

    private fun mapearHttp(e: HttpException): DomainError {
        val cuerpo = e.response()?.errorBody()?.string()
        val error = cuerpo?.let {
            runCatching { json.decodeFromString(ErrorResponseDto.serializer(), it).error }.getOrNull()
        }
        val codigo = error?.codigo
        val mensaje = error?.mensaje ?: e.message()
        val campos = error?.detalles?.mapNotNull { it.campo }.orEmpty()

        return when {
            codigo == "CREDENCIALES_INVALIDAS" -> DomainError.CredencialesInvalidas
            codigo == "EMAIL_DUPLICADO" -> DomainError.EmailDuplicado
            codigo == "PASSWORD_DEBIL" -> DomainError.PasswordDebil
            codigo == "TRANSICION_INVALIDA" -> DomainError.TransicionInvalida(mensaje)
            e.code() == 401 -> DomainError.NoAutenticado
            e.code() == 403 -> DomainError.SinPermiso
            e.code() == 404 -> DomainError.NoEncontrado
            e.code() == 400 || e.code() == 422 -> DomainError.SolicitudInvalida(codigo, mensaje, campos)
            else -> DomainError.Desconocido(mensaje)
        }
    }
}
