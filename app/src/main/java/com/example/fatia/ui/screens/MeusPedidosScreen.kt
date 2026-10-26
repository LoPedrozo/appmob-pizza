package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.data.model.Pedido
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

// TELA - MEUS PEDIDOS (o "Listar" e o "Deletar" do CRUD de pedidos)
//
// Mostra so os pedidos de quem esta logado: quem filtra pelo usuario da
// sessao e o viewModel, no listar().
//
// onAlterar = chamada com o id do pedido, para abrir a tela de editar.
@Composable
fun MeusPedidosScreen(
    modifier: Modifier = Modifier,
    viewModel: PedidoViewModel,
    onAlterar: (Long) -> Unit
) {
    // LaunchedEffect(Unit) roda quando a tela aparece. Como esta tela sai
    // da tela enquanto a de editar esta aberta, ao voltar ele roda de novo
    // e a lista ja vem atualizada do banco.
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    // Guarda o pedido que a pessoa pediu para cancelar.
    // null = nenhum, ou seja, o AlertDialog fica escondido.
    var pedidoParaCancelar by remember { mutableStateOf<Pedido?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Meus pedidos",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            // secondary = azul do mockup, usado nos titulos
            color = MaterialTheme.colorScheme.secondary
        )

        if (viewModel.pedidos.isEmpty()) {
            // Sem este aviso a tela ficaria so com um espaco branco.
            Text(
                text = "Você ainda não fez nenhum pedido",
                color = CinzaFatia
            )
        } else {
            // LazyColumn e uma lista que rola na vertical e so desenha
            // os itens que estao aparecendo na tela.
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.pedidos) { pedido ->
                    CardDoPedido(
                        pedido = pedido,
                        onAlterar = onAlterar,
                        // Ainda nao cancela: so marca qual pedido vai
                        // aparecer na pergunta de confirmacao.
                        onCancelar = { pedidoParaCancelar = pedido }
                    )
                }
            }
        }
    }

    // ----- CONFIRMACAO DO CANCELAMENTO (o "Deletar" do CRUD) -----
    // Copiamos para uma variavel local porque assim o Kotlin entende que
    // dentro do if ela nao e mais nula e deixa usar o .id.
    val pedidoDoDialogo = pedidoParaCancelar
    if (pedidoDoDialogo != null) {
        AlertDialog(
            // Chamado quando a pessoa toca fora do dialogo: nao apaga nada.
            onDismissRequest = { pedidoParaCancelar = null },
            title = { Text("Cancelar pedido") },
            text = { Text("Quer cancelar o pedido #${pedidoDoDialogo.id}?") },
            confirmButton = {
                TextButton(onClick = {
                    // Agora sim: o viewModel apaga no banco e pede a lista
                    // de novo. Depois fechamos o dialogo.
                    viewModel.excluir(pedidoDoDialogo.id)
                    pedidoParaCancelar = null
                }) {
                    Text(text = "Cancelar pedido", color = CoralFatia)
                }
            },
            dismissButton = {
                TextButton(onClick = { pedidoParaCancelar = null }) {
                    Text("Voltar")
                }
            }
        )
    }
}

// Card de um pedido: o numero, o resumo e os dois botoes.
@Composable
fun CardDoPedido(
    pedido: Pedido,
    onAlterar: (Long) -> Unit,
    onCancelar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // O numero do pedido e o id que o banco gerou.
            Text(
                text = "Pedido #${pedido.id}",
                fontWeight = FontWeight.Bold
            )

            // LinhaDoResumo e a mesma funcao usada na tela de confirmacao
            // (ela esta no arquivo ConfirmacaoScreen.kt, mesmo pacote).
            LinhaDoResumo(titulo = "Pizza", valor = pedido.pizzaNome)
            LinhaDoResumo(titulo = "Tamanho", valor = pedido.tamanho)
            LinhaDoResumo(
                titulo = "Borda recheada",
                // No banco borda e 0 ou 1; aqui viramos texto.
                valor = if (pedido.borda) "Sim" else "Não"
            )
            LinhaDoResumo(titulo = "Quantidade", valor = "${pedido.quantidade}")
            LinhaDoResumo(titulo = "Total", valor = "R$ ${pedido.total}")

            // Linha dos botoes do card.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Manda o id para a tela de editar saber qual pedido abrir.
                Button(onClick = { onAlterar(pedido.id) }) {
                    Text("Alterar")
                }
                TextButton(onClick = { onCancelar() }) {
                    Text(text = "Cancelar pedido", color = CoralFatia)
                }
            }
        }
    }
}

// Sem @Preview: esta tela precisa de um PedidoViewModel, que so existe
// quando o app esta rodando (ele e criado no AppNavigation).
