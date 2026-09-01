package com.example.fatia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.fatia.ui.screens.CardapioScreen
import com.example.fatia.ui.screens.ConfirmacaoScreen
import com.example.fatia.ui.screens.LoginScreen
import com.example.fatia.ui.theme.FatiaTheme

// Activity principal: e a primeira coisa que abre quando o app inicia.
class MainActivity : ComponentActivity() {

    // onCreate e chamado pelo Android quando a tela e criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Faz o app usar a tela inteira (atras da barra de status).
        enableEdgeToEdge()

        // setContent diz qual conteudo Compose vai aparecer na tela.
        setContent {
            // Aplica o tema do app (cores e textos do Material 3).
            FatiaTheme {
                // Scaffold e a estrutura basica da tela.
                // innerPadding e o espaco das barras do sistema.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppFatia(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Aqui decidimos QUAL tela aparece.
// Guardamos o nome da tela atual numa variavel de estado e usamos when
// para escolher. Quando a variavel muda, o Compose redesenha a tela.
@Composable
fun AppFatia(modifier: Modifier = Modifier) {

    // remember + mutableStateOf = variavel que a tela "lembra" e observa.
    // Comeca na tela de login.
    var telaAtual by remember { mutableStateOf("login") }

    // Guarda o nome da pizza escolhida no cardapio, para mostrar
    // depois na tela de confirmacao.
    var pizzaEscolhida by remember { mutableStateOf("") }

    when (telaAtual) {
        // Cada tela recebe uma funcao (lambda) que diz para onde ir depois.
        "login" -> LoginScreen(
            modifier = modifier,
            onEntrar = { telaAtual = "cardapio" }
        )
        // O cardapio devolve o nome da pizza clicada. Guardamos esse nome
        // e so depois trocamos de tela.
        "cardapio" -> CardapioScreen(
            modifier = modifier,
            onPizzaEscolhida = { nome ->
                pizzaEscolhida = nome
                telaAtual = "confirmacao"
            }
        )
        "confirmacao" -> ConfirmacaoScreen(
            modifier = modifier,
            nomePizza = pizzaEscolhida,
            onVoltarClick = { telaAtual = "cardapio" }
        )
    }
}
