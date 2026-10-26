package com.example.fatia.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.ui.screens.CadastroScreen
import com.example.fatia.ui.screens.CardapioScreen
import com.example.fatia.ui.screens.ConfirmacaoScreen
import com.example.fatia.ui.screens.LoginScreen
import com.example.fatia.ui.screens.PersonalizarPizzaScreen
import com.example.fatia.ui.viewmodel.AuthViewModel

// A NAVEGACAO DO APP
//
// O NavHost e o "porta-telas": ele guarda a lista de rotas e mostra
// a tela da rota atual. startDestination diz por qual rota o app comeca.
//
// Repare que as telas NAO recebem o navController. Elas so recebem
// funcoes (callbacks) e, quando precisam de dados, o viewModel.
// Quem navega de verdade e este arquivo aqui.
//
// O Scaffold com o Menu fica DENTRO de cada composable que tem menu.
// E por isso que o menu nao aparece no Login: aquela rota chama a tela
// direto, sem Scaffold.
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // O viewModel e criado AQUI, uma vez, e depois passado como parametro
    // para as telas que precisam dele. Assim o login e o cadastro usam o
    // mesmo AuthViewModel.
    val authViewModel: AuthViewModel = viewModel()

    // ----- CONTROLE DE SESSAO -----
    //
    // currentBackStackEntryAsState avisa este arquivo sempre que a rota
    // atual muda. Com ela descobrimos em qual tela o app esta agora.
    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination

    // hasRoute<Login>() pergunta "a rota atual e a do Login?".
    // O ? antes do ponto evita erro quando rotaAtual ainda e nula.
    val estaNoLogin = rotaAtual?.hasRoute<Login>() == true
    val estaNoCadastro = rotaAtual?.hasRoute<Cadastro>() == true

    // Este LaunchedEffect e o guarda do app: se alguem cair numa tela de
    // dentro sem estar logado (por exemplo depois de clicar em "Sair"),
    // ele manda de volta para o login.
    //
    // As chaves entre parenteses dizem quando o bloco roda de novo:
    // quando a rota muda ou quando o usuario logado muda.
    LaunchedEffect(rotaAtual, SessaoUsuario.usuarioId) {
        if (rotaAtual != null &&
            !estaNoLogin &&
            !estaNoCadastro &&
            SessaoUsuario.usuarioId == null
        ) {
            // popUpTo(0) { inclusive = true } limpa a pilha inteira, para o
            // botao voltar do celular nao devolver ninguem para dentro do app.
            navController.navigate(Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {

        // ----- LOGIN: sem menu -----
        composable<Login> {
            LoginScreen(
                viewModel = authViewModel,
                onEntrar = {
                    // Depois de entrar, o login sai da pilha (inclusive = true).
                    // Assim o botao voltar do celular nao volta para ele.
                    navController.navigate(Cardapio) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onIrParaCadastro = { navController.navigate(Cadastro) }
            )
        }

        // ----- CADASTRO: sem menu -----
        composable<Cadastro> {
            CadastroScreen(
                viewModel = authViewModel,
                // Nos dois casos voltamos para a tela anterior, que e o
                // login, tirando o cadastro da pilha de navegacao.
                onCadastrado = { navController.popBackStack() },
                onVoltarParaLogin = { navController.popBackStack() }
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
