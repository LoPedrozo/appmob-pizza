package com.example.fatia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { navController.navigate(Cardapio) }) {
                Icon(
                    imageVector = Icons.Filled.LocalPizza,
                    contentDescription = "Cardápio"
                )
            }
            IconButton(onClick = { navController.navigate(MeusPedidos) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                    contentDescription = "Pedidos"
                )
            }
            IconButton(onClick = {
                SessaoUsuario.encerrar()
                navController.navigate(Login) {
                    popUpTo(0) { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Sair"
                )
            }
        }
    )
}
