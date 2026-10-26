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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavHostController) {
    TopAppBar(
        title = { Text("Fatia") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AzulFatia,
            titleContentColor = Color.White
        ),
        actions = {
            TextButton(onClick = { navController.navigate(Cardapio) }) {
                Text(text = "Cardápio", color = Color.White)
            }
            TextButton(onClick = { navController.navigate(MeusPedidos) }) {
                Text(text = "Pedidos", color = Color.White)
            }
            TextButton(onClick = {
                SessaoUsuario.encerrar()
                navController.navigate(Login) {
                    popUpTo(0) { inclusive = true }
                }
            }) {
                Text(text = "Sair", color = Color.White)
            }
        }
    )
}
