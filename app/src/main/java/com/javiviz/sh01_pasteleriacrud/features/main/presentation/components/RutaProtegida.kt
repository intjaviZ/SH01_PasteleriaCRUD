package com.javiviz.sh01_pasteleriacrud.features.main.presentation.components

import androidx.compose.runtime.Composable
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation.Destino
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.screens.AccesoDenegadoScreen

/**
 * Guardia de ruta: si el rol del usuario no está permitido para el destino,
 * muestra "acceso denegado" aunque alguien llegue a la ruta por otro camino.
 * El drawer solo oculta opciones; esto es lo que realmente protege la vista.
 */
@Composable
fun RutaProtegida(
    destino: Destino,
    rol: Rol,
    onVolver: () -> Unit,
    contenido: @Composable () -> Unit
) {
    if (destino.permiteA(rol)) {
        contenido()
    } else {
        AccesoDenegadoScreen(onVolver = onVolver)
    }
}
