package com.javiviz.sh01_pasteleriacrud.core.model

data class Usuario(
    val id: Int,
    val nombre: String,
    // La respuesta de /auth/login no incluye telefono (solo /auth/register), por eso es nullable.
    val telefono: String?,
    val email: String,
    val rol: Rol
)
