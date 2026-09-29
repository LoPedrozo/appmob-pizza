package com.example.fatia.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.fatia.ui.screens.CardapioScreen
import com.example.fatia.ui.screens.ConfirmacaoScreen
import com.example.fatia.ui.screens.LoginScreen
import com.example.fatia.ui.screens.PersonalizarPizzaScreen

// A NAVEGACAO DO APP
//
// O NavHost e o "porta-telas": ele guarda a lista de rotas e mostra
// a tela da rota atual. startDestination diz por qual rota o app comeca.
//
// Repare que as telas NAO recebem o navController. Elas so recebem
// funcoes (callbacks). Quem navega de verdade e este arquivo aqui.
//
// O Scaffold com o Menu fica DENTRO de cada composable que tem menu.
// E por isso que o menu nao aparece no Login: aquela rota chama a tela
// direto, sem Scaffold.
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {

        // ----- LOGIN: sem menu -----
        composable<Login> {
            LoginScreen(
                onEntrar = { navController.navigate(Cardapio) }
            )
        }

        // ----- CARDAPIO: com menu -----
        composable<Cardapio> {
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                CardapioScreen(
                    modifier = Modifier.padding(innerPadding),
                    // O cardapio devolve o nome da pizza clicada e nos
                    // passamos esse nome como argumento da proxima rota.
                    onPizzaEscolhida = { nome ->
                        navController.navigate(Personalizar(nome))
                    }
                )
            }
        }

        // ----- PERSONALIZAR: com menu e com argumento -----
        // back e a entrada da pilha de navegacao. toRoute<Personalizar>()
        // reconstroi o objeto da rota, ja com o argumento dentro dele.
        composable<Personalizar> { back ->
            val rota = back.toRoute<Personalizar>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                PersonalizarPizzaScreen(
                    modifier = Modifier.padding(innerPadding),
                    nomePizza = rota.nomePizza,
                    onAdicionar = { nome ->
                        navController.navigate(Confirmacao(nome))
                    }
                )
            }
        }

        // ----- CONFIRMACAO: com menu e com argumento -----
        composable<Confirmacao> { back ->
            val rota = back.toRoute<Confirmacao>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                ConfirmacaoScreen(
                    modifier = Modifier.padding(innerPadding),
                    nomePizza = rota.nomePizza,
                    // popBackStack tira a tela atual da pilha, ou seja,
                    // volta para a tela anterior.
                    onVoltarClick = { navController.popBackStack() }
                )
            }
        }
    }
}
