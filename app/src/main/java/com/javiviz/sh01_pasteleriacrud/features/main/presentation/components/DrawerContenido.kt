package com.javiviz.sh01_pasteleriacrud.features.main.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.core.model.Rol
import com.javiviz.sh01_pasteleriacrud.core.model.Usuario
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation.Destino

@Composable
fun DrawerContenido(
    usuario: Usuario,
    destinos: List<Destino>,
    rutaActual: String?,
    onDestinoClick: (Destino) -> Unit,
    onCerrarSesion: () -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Text(text = usuario.nombre, style = MaterialTheme.typography.titleMedium)
            Text(
                text = usuario.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = etiquetaRol(usuario.rol),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        HorizontalDivider()
        Spacer(Modifier.height(8.dp))

        destinos.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                icon = if (destino.icono != null) {
                    { Icon(destino.icono, contentDescription = null) }
                } else null,
                selected = rutaActual == destino.ruta,
                onClick = { onDestinoClick(destino) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }

        Spacer(Modifier.weight(1f))
        HorizontalDivider()

        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
            selected = false,
            onClick = onCerrarSesion,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Spacer(Modifier.height(8.dp))
    }
}

private fun etiquetaRol(rol: Rol): String = when (rol) {
    Rol.CLIENTE -> "Cliente"
    Rol.REPOSTERA -> "Repostera"
}
