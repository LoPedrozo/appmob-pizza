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
import com.example.fatia.ui.screens.EditarPedidoScreen
import com.example.fatia.ui.screens.LoginScreen
import com.example.fatia.ui.screens.MeusPedidosScreen
import com.example.fatia.ui.screens.PersonalizarPizzaScreen
import com.example.fatia.ui.screens.PizzaFormScreen
import com.example.fatia.ui.viewmodel.AuthViewModel
import com.example.fatia.ui.viewmodel.PedidoViewModel
import com.example.fatia.ui.viewmodel.PizzaViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val authViewModel: AuthViewModel = viewModel()
    val pizzaViewModel: PizzaViewModel = viewModel()
    val pedidoViewModel: PedidoViewModel = viewModel()

    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination

    val estaNoLogin = rotaAtual?.hasRoute<Login>() == true
    val estaNoCadastro = rotaAtual?.hasRoute<Cadastro>() == true

    LaunchedEffect(rotaAtual, SessaoUsuario.usuarioId) {
        if (rotaAtual != null &&
            !estaNoLogin &&
            !estaNoCadastro &&
            SessaoUsuario.usuarioId == null
        ) {
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

        composable<Login> {
            LoginScreen(
                viewModel = authViewModel,
                onEntrar = {
                    navController.navigate(Cardapio) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onIrParaCadastro = { navController.navigate(Cadastro) }
            )
        }

        composable<Cadastro> {
            CadastroScreen(
                viewModel = authViewModel,
                onCadastrado = { navController.popBackStack() },
                onVoltarParaLogin = { navController.popBackStack() }
            )
        }

        composable<Cardapio> {
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                CardapioScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = pizzaViewModel,
                    onPizzaEscolhida = { id ->
                        navController.navigate(Personalizar(id))
                    },
                    onNovaPizza = {
                        navController.navigate(PizzaForm(0))
                    },
                    onEditarPizza = { id ->
                        navController.navigate(PizzaForm(id))
                    }
                )
            }
        }

        composable<PizzaForm> { back ->
            val rota = back.toRoute<PizzaForm>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                PizzaFormScreen(
                    modifier = Modifier.padding(innerPadding),
                    pizzaId = rota.pizzaId,
                    viewModel = pizzaViewModel,
                    onSalvo = { navController.popBackStack() },
                    onVoltar = { navController.popBackStack() }
                )
            }
        }

        composable<Personalizar> { back ->
            val rota = back.toRoute<Personalizar>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                PersonalizarPizzaScreen(
                    modifier = Modifier.padding(innerPadding),
                    pizzaId = rota.pizzaId,
                    viewModel = pedidoViewModel,
                    onPedidoCriado = { id ->
                        navController.navigate(Confirmacao(id)) {
                            popUpTo(Cardapio)
                        }
                    }
                )
            }
        }

        composable<Confirmacao> { back ->
            val rota = back.toRoute<Confirmacao>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                ConfirmacaoScreen(
                    modifier = Modifier.padding(innerPadding),
                    pedidoId = rota.pedidoId,
                    viewModel = pedidoViewModel,
                    onVerPedidos = { navController.navigate(MeusPedidos) },
                    onVoltarCardapio = {
                        navController.popBackStack(route = Cardapio, inclusive = false)
                    }
                )
            }
        }

        composable<MeusPedidos> {
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                MeusPedidosScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = pedidoViewModel,
                    onAlterar = { id ->
                        navController.navigate(EditarPedido(id))
                    }
                )
            }
        }

        composable<EditarPedido> { back ->
            val rota = back.toRoute<EditarPedido>()
            Scaffold(topBar = { Menu(navController) }) { innerPadding ->
                EditarPedidoScreen(
                    modifier = Modifier.padding(innerPadding),
                    pedidoId = rota.pedidoId,
                    viewModel = pedidoViewModel,
                    onSalvo = { navController.popBackStack() }
                )
            }
        }
    }
}
