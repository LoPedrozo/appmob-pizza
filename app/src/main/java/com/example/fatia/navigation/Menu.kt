package com.example.fatia.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.ui.theme.AzulFatia

// MENU DO APP
//
// E a barra de cima com os atalhos entre as telas.
// Ele recebe o navController porque e ele quem navega quando
// clicamos num dos botoes.
//
// @OptIn e necessario porque a TopAppBar ainda e marcada como
// experimental no Material 3.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavHostController) {
    TopAppBar(
        title = { Text("Fatia") },
        // Pinta a barra com o azul do app e o titulo de branco.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AzulFatia,
            titleContentColor = Color.White
        ),
        // actions = os botoes que ficam do lado direito da barra.
        actions = {
            TextButton(onClick = { navController.navigate(Cardapio) }) {
                Text(text = "Cardápio", color = Color.White)
            }
            // O botao "Personalizar" saiu do menu: aquela tela precisa de
            // uma pizza escolhida, e so o cardapio sabe qual e. Agora o
            // caminho para ela e sempre o botao "Escolher" de um card.
            TextButton(onClick = { navController.navigate(MeusPedidos) }) {
                Text(text = "Pedidos", color = Color.White)
            }
            TextButton(onClick = {
                // Sair = fechar a sessao e voltar para o login.
                // encerrar() apaga o usuario logado da sessao.
                SessaoUsuario.encerrar()
                // popUpTo(0) { inclusive = true } limpa a pilha inteira, para
                // o botao voltar do celular nao devolver ninguem para dentro
                // do app depois de sair.
                navController.navigate(Login) {
                    popUpTo(0) { inclusive = true }
                }
            }) {
                Text(text = "Sair", color = Color.White)
            }
        }
    )
}
