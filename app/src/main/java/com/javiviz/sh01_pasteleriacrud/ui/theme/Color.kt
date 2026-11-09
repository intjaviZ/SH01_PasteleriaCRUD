package com.javiviz.sh01_pasteleriacrud.ui.theme

import androidx.compose.ui.graphics.Color

/*
 * Paleta de pastelería. Cada tono de contraste tiene un rol fijo en los dos modos:
 *
 *   Morado      -> primary    (acciones principales, barra superior, selección)
 *   Verde menta -> secondary  (confirmaciones, estados "entregado")
 *   Rosa        -> tertiary   (acentos, estados "ordenado")
 *   Celeste     -> superficies (tarjetas, campos, divisores)
 *
 * Los textos siempre usan el color "on..." de su fondo: oscuro sobre fondos claros y
 * claro sobre fondos oscuros. Todas las parejas texto/fondo cumplen contraste AA (4.5:1).
 */

// ───────────────────────── MODO CLARO ─────────────────────────

// Morado
val LightPrimary = Color(0xFF6B46A1)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFEBDDFF)
val LightOnPrimaryContainer = Color(0xFF2A0A52)

// Verde menta
val LightSecondary = Color(0xFF1F7A5C)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFBFF0DD)
val LightOnSecondaryContainer = Color(0xFF00201A)

// Rosa
val LightTertiary = Color(0xFFA83A67)
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFFFD8E6)
val LightOnTertiaryContainer = Color(0xFF3B0A20)

// Error
val LightError = Color(0xFFBA1A1A)
val LightOnError = Color(0xFFFFFFFF)
val LightErrorContainer = Color(0xFFFFDAD6)
val LightOnErrorContainer = Color(0xFF410002)

// Fondos y superficies (celeste en las capas altas)
val LightBackground = Color(0xFFFFF9FC)
val LightOnBackground = Color(0xFF1E1A22)
val LightSurface = Color(0xFFFFF9FC)
val LightOnSurface = Color(0xFF1E1A22)
val LightSurfaceVariant = Color(0xFFDCEBF8)
val LightOnSurfaceVariant = Color(0xFF3F4A55)
val LightSurfaceDim = Color(0xFFE6DEE8)
val LightSurfaceBright = Color(0xFFFFF9FC)
val LightSurfaceContainerLowest = Color(0xFFFFFFFF)
val LightSurfaceContainerLow = Color(0xFFFBF2F8)
val LightSurfaceContainer = Color(0xFFF5EDF6)
val LightSurfaceContainerHigh = Color(0xFFE8F1FA)
val LightSurfaceContainerHighest = Color(0xFFDCEBF8)

val LightOutline = Color(0xFF6F7A86)
val LightOutlineVariant = Color(0xFFC3D3E2)
val LightInverseSurface = Color(0xFF332F37)
val LightInverseOnSurface = Color(0xFFF6EEF8)
val LightInversePrimary = Color(0xFFD5BBFF)

// ───────────────────────── MODO OSCURO ─────────────────────────

// Morado (lavanda, para que destaque sobre fondo oscuro)
val DarkPrimary = Color(0xFFD5BBFF)
val DarkOnPrimary = Color(0xFF3B1470)
val DarkPrimaryContainer = Color(0xFF54307F)
val DarkOnPrimaryContainer = Color(0xFFEBDDFF)

// Verde menta
val DarkSecondary = Color(0xFF8EDBBE)
val DarkOnSecondary = Color(0xFF00382A)
val DarkSecondaryContainer = Color(0xFF00513F)
val DarkOnSecondaryContainer = Color(0xFFBFF0DD)

// Rosa
val DarkTertiary = Color(0xFFFFB0CE)
val DarkOnTertiary = Color(0xFF5E1135)
val DarkTertiaryContainer = Color(0xFF7E2A4C)
val DarkOnTertiaryContainer = Color(0xFFFFD8E6)

// Error
val DarkError = Color(0xFFFFB4AB)
val DarkOnError = Color(0xFF690005)
val DarkErrorContainer = Color(0xFF93000A)
val DarkOnErrorContainer = Color(0xFFFFDAD6)

// Fondos y superficies (celeste oscuro en las capas altas)
val DarkBackground = Color(0xFF151218)
val DarkOnBackground = Color(0xFFE8E0EB)
val DarkSurface = Color(0xFF151218)
val DarkOnSurface = Color(0xFFE8E0EB)
val DarkSurfaceVariant = Color(0xFF2A3744)
val DarkOnSurfaceVariant = Color(0xFFC5D3DF)
val DarkSurfaceDim = Color(0xFF151218)
val DarkSurfaceBright = Color(0xFF3B383E)
val DarkSurfaceContainerLowest = Color(0xFF100D13)
val DarkSurfaceContainerLow = Color(0xFF1D1A20)
val DarkSurfaceContainer = Color(0xFF221E25)
val DarkSurfaceContainerHigh = Color(0xFF232C36)
val DarkSurfaceContainerHighest = Color(0xFF2A3744)

val DarkOutline = Color(0xFF8E9AA6)
val DarkOutlineVariant = Color(0xFF3F4A57)
val DarkInverseSurface = Color(0xFFE8E0EB)
val DarkInverseOnSurface = Color(0xFF332F37)
val DarkInversePrimary = Color(0xFF6B46A1)
