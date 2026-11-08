package com.javiviz.sh01_pasteleriacrud.features.catalogo.data.mapper

import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.ConfiguracionTamanosDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.PastelDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.dto.RellenoDto
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.PastelCatalogo
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.Relleno

fun PastelDto.toPastelCatalogo(): PastelCatalogo = PastelCatalogo(
    id = id,
    nombre = nombre,
    permiteRelleno = permiteRelleno,
    precioBasePorcion = precioBasePorcion.toBigDecimal(),
    activo = activo
)

fun RellenoDto.toRelleno(): Relleno = Relleno(id = id, nombre = nombre, activo = activo)

fun ConfiguracionTamanosDto.toConfiguracionTamanos(): ConfiguracionTamanos = ConfiguracionTamanos(
    porcionesMinimas = porcionesMinimas,
    porcionesMaximas = porcionesMaximas,
    multiplo = multiplo,
    opcionesPorciones = opcionesPorciones,
    umbralDistribucion = umbralDistribucion,
    dedicatoriaMaxCaracteres = dedicatoriaMaxCaracteres,
    margenMinimoEntregaHoras = margenMinimoEntregaHoras
)
