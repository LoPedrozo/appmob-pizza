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

// A data class Pizza fica em data/model/Pizza.kt, porque a pizza e uma
// linha da tabela "pizzas" do banco (o Model do MVVM).
//
// A funcao listaDePizzas() foi apagada: as pizzas nao sao mais escritas no
// codigo, elas vem do banco pelo PizzaViewModel.

// TELA 2 - CARDAPIO (o "Listar" e o "Deletar" do CRUD de pizzas)
//
// viewModel = quem fala com o banco. A tela so mostra viewModel.pizzas.
// onPizzaEscolhida = botao "Escolher". Passa o ID da pizza, porque a tela
//   de personalizar busca o nome e o preco dela no banco.
// onNovaPizza = botao "Nova pizza" (abre o formulario vazio).
// onEditarPizza = botao "Editar" de um card (abre o formulario preenchido).
@Composable
fun CardapioScreen(
    modifier: Modifier = Modifier,
    viewModel: PizzaViewModel,
    onPizzaEscolhida: (Long) -> Unit,
    onNovaPizza: () -> Unit,
    onEditarPizza: (Long) -> Unit
) {
    // LaunchedEffect(Unit) roda quando a tela aparece. Como o cardapio sai
    // da tela enquanto o formulario esta aberto, ao voltar ele roda de novo
    // e a lista ja vem atualizada do banco.
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    // Guarda a pizza que a pessoa pediu para excluir.
    // null = nenhuma, ou seja, o AlertDialog de confirmacao fica escondido.
    var pizzaParaExcluir by remember { mutableStateOf<Pizza?>(null) }

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
            // O nome vem do viewModel, que pega na sessao: e o nome de
            // quem fez o login. Antes era "Ola, Ana!" escrito na mao.
            Text(
                text = "Olá, ${viewModel.nomeUsuario}!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                // secondary = azul do mockup, usado nos titulos
                color = MaterialTheme.colorScheme.secondary
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

        // ----- BOTAO DE CRIAR (o "Criar" do CRUD) -----
        // Abre o formulario vazio. Quem manda o id 0 e o AppNavigation.
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

        // ----- LISTA DE PIZZAS -----
        if (viewModel.pizzas.isEmpty()) {
            // Sem este aviso a tela ficaria so com um espaco branco quando
            // todas as pizzas fossem excluidas.
            Text(
                text = "Nenhuma pizza cadastrada",
                color = CinzaFatia
            )
        } else {
            // LazyColumn e uma lista que rola na vertical e so desenha
            // os itens que estao aparecendo na tela.
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Para cada pizza do viewModel, desenha um Card.
                items(viewModel.pizzas) { pizza ->
                    CardDaPizza(
                        pizza = pizza,
                        onPizzaEscolhida = onPizzaEscolhida,
                        onEditarPizza = onEditarPizza,
                        // Ainda nao exclui: so marca qual pizza vai aparecer
                        // na pergunta de confirmacao.
                        onExcluirPizza = { pizzaParaExcluir = pizza }
                    )
                }
            }
        }
    }

    // ----- CONFIRMACAO DE EXCLUSAO (o "Deletar" do CRUD) -----
    // Copiamos para uma variavel local porque assim o Kotlin entende que
    // dentro do if ela nao e mais nula e deixa usar o .nome.
    val pizzaDoDialogo = pizzaParaExcluir
    if (pizzaDoDialogo != null) {
        AlertDialog(
            // Chamado quando a pessoa toca fora do dialogo: nao exclui nada.
            onDismissRequest = { pizzaParaExcluir = null },
            title = { Text("Excluir pizza") },
            text = { Text("Quer excluir a pizza ${pizzaDoDialogo.nome} do cardápio?") },
            confirmButton = {
                TextButton(onClick = {
                    // Agora sim: o viewModel apaga no banco e pede a lista
                    // de novo. Depois fechamos o dialogo.
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

// Card de uma pizza: nome, descricao, preco e os tres botoes.
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
            // Textos da pizza.
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

            // Linha dos botoes do card.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avisa quem chamou a tela qual pizza foi escolhida,
                // mandando o id dela.
                Button(onClick = { onPizzaEscolhida(pizza.id) }) {
                    Text("Escolher")
                }
                // Manda o id para o formulario saber qual pizza carregar.
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

// Sem @Preview: esta tela agora precisa de um PizzaViewModel, que so existe
// quando o app esta rodando (ele e criado no AppNavigation).
