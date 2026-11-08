package com.javiviz.sh01_pasteleriacrud.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Mensaje en recuadro: rojo si es error, tono secundario si es informativo. */
@Composable
fun BannerMensaje(
    texto: String,
    esError: Boolean,
    modifier: Modifier = Modifier
) {
    val fondo = if (esError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer
    val contenido = if (esError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = fondo,
        contentColor = contenido,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(12.dp)
        )
    }
}
