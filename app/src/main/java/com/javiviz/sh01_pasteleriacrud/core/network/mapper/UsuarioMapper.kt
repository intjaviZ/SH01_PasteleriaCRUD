package com.javiviz.sh01_pasteleriacrud.core.network.mapper

import com.javiviz.sh01_pasteleriacrud.core.model.DomainError
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.core.network.dto.UsuarioDto

fun UsuarioDto.toUsuario(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    telefono = telefono,
    email = email,
    rol = Rol.entries.firstOrNull { it.name == rol }
        ?: throw DomainError.Desconocido("Rol no reconocido: $rol")
)
