package com.javiviz.sh01_pasteleriacrud.core.ui.validation

/** Cada función devuelve el mensaje de error, o null si el valor es válido. */
object Validadores {

    private val EMAIL = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private val TELEFONO = Regex("^\\d{10}$")

    fun email(valor: String): String? = when {
        valor.isBlank() -> "El email es obligatorio."
        !EMAIL.matches(valor.trim()) -> "Ingresa un email válido."
        else -> null
    }

    fun passwordObligatoria(valor: String): String? =
        if (valor.isEmpty()) "La contraseña es obligatoria." else null

    fun passwordNueva(valor: String): String? = when {
        valor.isEmpty() -> "La contraseña es obligatoria."
        valor.length < 8 -> "Debe tener al menos 8 caracteres."
        else -> null
    }

    fun confirmacion(password: String, confirmacion: String): String? =
        if (password != confirmacion) "Las contraseñas no coinciden." else null

    fun nombre(valor: String): String? = when {
        valor.isBlank() -> "El nombre es obligatorio."
        valor.trim().length < 3 -> "Escribe tu nombre completo."
        else -> null
    }

    fun telefono(valor: String): String? = when {
        valor.isBlank() -> "El teléfono es obligatorio."
        !TELEFONO.matches(valor.trim()) -> "Ingresa un teléfono de 10 dígitos."
        else -> null
    }
}
