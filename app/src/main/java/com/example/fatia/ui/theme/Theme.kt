package com.example.fatia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CoresDoApp = lightColorScheme(
    primary = CoralFatia,
    secondary = AzulFatia,
    tertiary = AmareloFatia
)

@Composable
fun FatiaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresDoApp,
        typography = Typography,
        content = content
    )
}
