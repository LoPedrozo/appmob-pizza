package com.example.fatia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Conjunto de cores do app.
// primary = cor principal, secondary e tertiary = cores de apoio.
private val CoresDoApp = lightColorScheme(
    primary = LaranjaFatia,
    secondary = VermelhoFatia,
    tertiary = CremeFatia
)

// Tema do app.
// Tudo que for colocado dentro de FatiaTheme { ... } usa essas cores.
@Composable
fun FatiaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresDoApp,
        content = content
    )
}
