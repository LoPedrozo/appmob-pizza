package com.example.fatia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Conjunto de cores do app, seguindo os mockups.
// primary = coral, usado nos botoes e nos precos.
// secondary = azul, usado nos titulos.
// tertiary = amarelo, cor de apoio.
private val CoresDoApp = lightColorScheme(
    primary = CoralFatia,
    secondary = AzulFatia,
    tertiary = AmareloFatia
)

// Tema do app.
// Tudo que for colocado dentro de FatiaTheme { ... } usa essas cores e textos.
@Composable
fun FatiaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresDoApp,
        typography = Typography,
        content = content
    )
}
