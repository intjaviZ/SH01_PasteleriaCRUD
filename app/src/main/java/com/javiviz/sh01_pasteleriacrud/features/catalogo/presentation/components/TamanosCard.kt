package com.javiviz.sh01_pasteleriacrud.features.catalogo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javiviz.sh01_pasteleriacrud.features.catalogo.data.model.ConfiguracionTamanos

/** Resume las reglas de tamaño que valida la API, para que el usuario las conozca antes de pedir. */
@Composable
fun TamanosCard(config: ConfiguracionTamanos, modifier: Modifier = Modifier) {

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Text(
                text = "Porciones: de ${config.porcionesMinimas} a ${config.porcionesMaximas}, de ${config.multiplo} en ${config.multiplo}.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Desde ${config.umbralDistribucion} porciones puedes dividir el pedido en varias tortas.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Dedicatoria de hasta ${config.dedicatoriaMaxCaracteres} caracteres.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Pide con al menos ${config.margenMinimoEntregaHoras} horas de anticipación.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

}
