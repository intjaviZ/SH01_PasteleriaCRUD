package com.javiviz.sh01_pasteleriacrud.core.model

/**
 * Errores que entiende la capa de presentación, sin depender de HTTP ni de Retrofit.
 * Se devuelven dentro de Result.failure(...).
 */
sealed class DomainError(override val message: String) : Exception(message) {
    data object SinConexion : DomainError("No hay conexión con el servidor.")
    data object CredencialesInvalidas : DomainError("Email o contraseña incorrectos.")
    data object NoAutenticado : DomainError("Tu sesión no es válida o expiró. Inicia sesión de nuevo.")
    data object SinPermiso : DomainError("No tienes permiso para realizar esta acción.")
    data object NoEncontrado : DomainError("No se encontró el recurso solicitado.")
    data object EmailDuplicado : DomainError("Ya existe una cuenta con ese email.")
    data object PasswordDebil : DomainError("La contraseña debe tener al menos 8 caracteres.")

    /** 400 / 422 que no tienen un tipo propio todavía. */
    data class SolicitudInvalida(
        val codigo: String?,
        val detalle: String,
        val campos: List<String>
    ) : DomainError(detalle)

    data class Desconocido(val detalle: String) : DomainError(detalle)
}
