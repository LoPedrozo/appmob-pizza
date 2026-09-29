package com.example.fatia.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
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
            // A rota Personalizar exige um argumento, entao passamos
            // uma pizza de exemplo quando o atalho vem do menu.
            TextButton(onClick = { navController.navigate(Personalizar("Pizza calabresa")) }) {
                Text(text = "Personalizar", color = Color.White)
            }
            TextButton(onClick = { navController.navigate(Login) }) {
                Text(text = "Sair", color = Color.White)
            }
        }
    )
}
