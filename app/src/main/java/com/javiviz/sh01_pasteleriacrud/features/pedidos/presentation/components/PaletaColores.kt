package com.javiviz.sh01_pasteleriacrud.features.pedidos.presentation.components

data class ColorPastel(val nombre: String, val hex: String)

/** Colores ofrecidos para decorar. La API recibe el hex ("#F7D774"). */
object PaletaColores {

    val colores: List<ColorPastel> = listOf(
        ColorPastel("Blanco", "#FFFFFF"),
        ColorPastel("Rosa", "#F8BBD0"),
        ColorPastel("Amarillo", "#F7D774"),
        ColorPastel("Naranja", "#FFB74D"),
        ColorPastel("Rojo", "#C0392B"),
        ColorPastel("Lila", "#CE93D8"),
        ColorPastel("Azul cielo", "#90CAF9"),
        ColorPastel("Verde menta", "#A5D6A7"),
        ColorPastel("Chocolate", "#5D4037")
    )

    fun deHex(hex: String): ColorPastel? = colores.firstOrNull { it.hex == hex }
}
