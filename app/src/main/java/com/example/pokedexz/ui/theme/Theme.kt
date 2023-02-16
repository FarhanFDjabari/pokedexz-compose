package com.example.pokedexz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color(0xFFF4AC1C),
    background = Color(0xFF2C0C3C),
    onBackground = Color(0xFFF0F2F6),
    surface = Color(0xFF641C7C),
    onSurface = Color(0xFFF0F2F6),
    secondary = Color.LightGray,
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFF42A28),
    onPrimary = Color(0xFF2C0C3C),
    background = Color(0xFFF0F2F6),
    onBackground = Color.Black,
    surface = Color(0xFF37A5C6),
    onSurface = Color(0xFFF0F2F6),
    secondary = Color.LightGray,
)

@Composable
fun PokedexZTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(
        color = Color.Transparent
    )
    systemUiController.setStatusBarColor(
        color = Color.Transparent
    )
    val colors = if (darkTheme) {
        systemUiController.setStatusBarColor(
            color = DarkColorPalette.background
        )
        DarkColorPalette
    } else {
        systemUiController.setStatusBarColor(
            color = LightColorPalette.background
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}