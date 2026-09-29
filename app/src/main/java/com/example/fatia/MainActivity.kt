package com.example.fatia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fatia.navigation.AppNavigation
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
                // Na Parte 1 a troca de telas era feita aqui, com uma
                // variavel telaAtual e um when. Na Parte 2 quem faz esse
                // papel e o NavHost, dentro do AppNavigation.
                //
                // rememberNavController cria o controlador da navegacao e
                // faz o Compose lembrar dele entre as recomposicoes.
                val navController = rememberNavController()

                AppNavigation(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
