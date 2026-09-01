package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fatia.R
import com.example.fatia.ui.theme.FatiaTheme

// data class = classe feita para guardar dados.
// Cada pizza tem um nome, uma descricao e um preco.
data class Pizza(
    val nome: String,
    val descricao: String,
    val preco: Int
)

// Funcao que devolve a lista de pizzas do cardapio.
// A lista e fixa (escrita no codigo), nao vem de internet nem de banco.
fun listaDePizzas(): List<Pizza> {
    return listOf(
        Pizza("Calabresa", "Calabresa, cebola e azeitona", 39),
        Pizza("Mussarela", "Muito queijo mussarela e orégano", 35),
        Pizza("Portuguesa", "Presunto, ovo, ervilha e cebola", 42),
        Pizza("Frango com Catupiry", "Frango desfiado com catupiry", 45),
        Pizza("Quatro Queijos", "Mussarela, provolone, gorgonzola e parmesão", 47)
    )
}

// TELA 2 - CARDAPIO
// onPizzaEscolhida e uma funcao recebida de fora (lambda).
// Quando o usuario clica em "Escolher", chamamos essa funcao passando
// o nome da pizza. Quem usa a tela decide o que fazer com esse nome.
@Composable
fun CardapioScreen(
    modifier: Modifier = Modifier,
    onPizzaEscolhida: (String) -> Unit
) {
    // Guarda a lista de pizzas numa variavel para usar mais abaixo.
    val pizzas = listaDePizzas()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ----- TOPO: saudacao e icone do carrinho -----
        Row(
            modifier = Modifier.fillMaxWidth(),
            // SpaceBetween joga um item para cada ponta da linha.
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Olá, Ana!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            // Icone apenas decorativo (nao faz nada quando clicado).
            Icon(
                painter = painterResource(R.drawable.ic_carrinho),
                contentDescription = "Carrinho",
                modifier = Modifier.size(28.dp)
            )
        }

        // ----- CATEGORIAS (decorativas) -----
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

        // ----- LISTA DE PIZZAS -----
        // LazyColumn e uma lista que rola na vertical e so desenha
        // os itens que estao aparecendo na tela.
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Para cada pizza da lista, desenha um Card.
            items(pizzas) { pizza ->
                CardDaPizza(
                    pizza = pizza,
                    onPizzaEscolhida = onPizzaEscolhida
                )
            }
        }
    }
}

// Card de uma categoria. Separado numa funcao para nao repetir codigo.
@Composable
fun Categoria(nome: String) {
    Card {
        Text(
            text = nome,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

// Card de uma pizza: nome, descricao, preco e o botao "Escolher".
@Composable
fun CardDaPizza(
    pizza: Pizza,
    onPizzaEscolhida: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coluna da esquerda: textos da pizza.
            Column {
                Text(
                    text = pizza.nome,
                    fontWeight = FontWeight.Bold
                )
                Text(text = pizza.descricao)
                // O $ dentro do texto coloca o valor da variavel ali.
                Text(
                    text = "R$ ${pizza.preco}",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            // Ao clicar, avisa quem chamou a tela qual pizza foi escolhida.
            Button(onClick = { onPizzaEscolhida(pizza.nome) }) {
                Text("Escolher")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardapioScreenPreview() {
    FatiaTheme {
        CardapioScreen(onPizzaEscolhida = {})
    }
}
