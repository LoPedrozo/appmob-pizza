package com.example.fatia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fatia.ui.theme.FatiaTheme

// Tela inicial do app. E a primeira coisa que abre quando o app inicia.
class MainActivity : ComponentActivity() {

    // onCreate e chamado pelo Android quando a tela e criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent diz qual conteudo Compose vai aparecer na tela.
        setContent {
            // Aplica o tema do app (cores do Material 3).
            FatiaTheme {
                // Surface e a "folha de fundo" da tela.
                Surface(
                    // fillMaxSize faz ocupar a tela toda.
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaTemporaria()
                }
            }
        }
    }
}

// Funcao @Composable = um pedaco de tela feito com Jetpack Compose.
// Por enquanto e so um texto provisorio, ate criarmos as telas de verdade.
@Composable
fun TelaTemporaria() {
    // Box empilha o conteudo; contentAlignment centraliza no meio da tela.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Fatia - em construção")
    }
}

// @Preview mostra essa tela no painel de visualizacao do Android Studio,
// sem precisar rodar o app no celular/emulador.
@Preview(showBackground = true)
@Composable
fun TelaTemporariaPreview() {
    FatiaTheme {
        TelaTemporaria()
    }
}
