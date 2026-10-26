package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.R
import com.example.fatia.data.model.Pizza
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Olá, ${viewModel.nomeUsuario}!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Icon(
                painter = painterResource(R.drawable.ic_carrinho),
                contentDescription = "Carrinho",
                modifier = Modifier.size(28.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Categoria(nome = "Salgadas")
            Categoria(nome = "Doces")
            Categoria(nome = "Bebidas")
        }

        Text(
            text = "Escolha a sua pizza",
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { onNovaPizza() },
            colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Nova pizza",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        if (viewModel.pizzas.isEmpty()) {
            Text(
                text = "Nenhuma pizza cadastrada",
                color = CinzaFatia
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.pizzas) { pizza ->
                    CardDaPizza(
                        pizza = pizza,
                        onPizzaEscolhida = onPizzaEscolhida,
                        onEditarPizza = onEditarPizza,
                        onExcluirPizza = { pizzaParaExcluir = pizza }
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
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun Categoria(nome: String) {
    Card {
        Text(
            text = nome,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CardDaPizza(
    pizza: Pizza,
    onPizzaEscolhida: (Long) -> Unit,
    onEditarPizza: (Long) -> Unit,
    onExcluirPizza: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = pizza.nome,
                fontWeight = FontWeight.Bold
            )
            Text(text = pizza.descricao)
            Text(
                text = "R$ ${pizza.preco}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { onPizzaEscolhida(pizza.id) }) {
                    Text("Escolher")
                }
                TextButton(onClick = { onEditarPizza(pizza.id) }) {
                    Text("Editar")
                }
                TextButton(onClick = { onExcluirPizza() }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            }
        }
    }
}
