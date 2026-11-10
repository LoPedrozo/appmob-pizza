package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.data.model.Pizza
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.PizzaViewModel

@Composable
fun CardapioScreen(
    modifier: Modifier = Modifier,
    viewModel: PizzaViewModel,
    onPizzaEscolhida: (Long) -> Unit,
    onNovaPizza: () -> Unit,
    onEditarPizza: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    var pizzaParaExcluir by remember { mutableStateOf<Pizza?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(
            titulo = "Olá, ${viewModel.nomeUsuario}!",
            subtitulo = "Escolha a sua pizza"
        )

        BotaoPrincipal(texto = "Nova pizza", onClick = { onNovaPizza() })

        if (viewModel.pizzas.isEmpty()) {
            ListaVazia(icone = Icons.Filled.LocalPizza, texto = "Nenhuma pizza cadastrada")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.pizzas) { pizza ->
                    CardDaPizza(
                        pizza = pizza,
                        onEscolher = onPizzaEscolhida,
                        onEditar = onEditarPizza,
                        onExcluir = { pizzaParaExcluir = pizza }
                    )
                }
            }
        }
    }

    val pizzaDoDialogo = pizzaParaExcluir
    if (pizzaDoDialogo != null) {
        AlertDialog(
            onDismissRequest = { pizzaParaExcluir = null },
            title = { Text("Excluir pizza") },
            text = { Text("Quer excluir a pizza ${pizzaDoDialogo.nome} do cardápio?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.excluir(pizzaDoDialogo.id)
                    pizzaParaExcluir = null
                }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            },
            dismissButton = {
                TextButton(onClick = { pizzaParaExcluir = null }) {
                    Text(text = "Cancelar", color = AzulFatia)
                }
            }
        )
    }
}

@Composable
fun CardDaPizza(
    pizza: Pizza,
    onEscolher: (Long) -> Unit,
    onEditar: (Long) -> Unit,
    onExcluir: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = pizza.nome, fontWeight = FontWeight.Bold)
                Text(text = "R$ ${pizza.preco}", color = CoralFatia, fontWeight = FontWeight.Bold)
            }
            Text(text = pizza.descricao, color = CinzaFatia)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { onEscolher(pizza.id) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Escolher")
                }
                TextButton(onClick = { onEditar(pizza.id) }) {
                    Text(text = "Editar", color = AzulFatia)
                }
                TextButton(onClick = { onExcluir() }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            }
        }
    }
}
