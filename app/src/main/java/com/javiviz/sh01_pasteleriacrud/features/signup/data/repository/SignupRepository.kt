package com.javiviz.sh01_pasteleriacrud.features.signup.data.repository

import com.javiviz.sh01_pasteleriacrud.core.model.Usuario

interface SignupRepository {
    suspend fun registrar(
        nombre: String,
        telefono: String,
        email: String,
        password: String
    ): Result<Usuario>
}
