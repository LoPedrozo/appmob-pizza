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

@Composable
fun MeusPedidosScreen(
    modifier: Modifier = Modifier,
    viewModel: PedidoViewModel,
    onEditarPedido: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    var pedidoParaExcluir by remember { mutableStateOf<Pedido?>(null) }

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
            color = MaterialTheme.colorScheme.secondary
        )

        if (viewModel.pedidos.isEmpty()) {
            Text(
                text = "Você ainda não fez nenhum pedido",
                color = CinzaFatia
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.pedidos) { pedido ->
                    CardDoPedido(
                        pedido = pedido,
                        onEditar = onEditarPedido,
                        onExcluir = { pedidoParaExcluir = pedido }
                    )
                }
            }
        }
    }

    val pedidoDoDialogo = pedidoParaExcluir
    if (pedidoDoDialogo != null) {
        AlertDialog(
            onDismissRequest = { pedidoParaExcluir = null },
            title = { Text("Cancelar pedido") },
            text = { Text("Quer cancelar o pedido #${pedidoDoDialogo.id}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.excluir(pedidoDoDialogo.id)
                    pedidoParaExcluir = null
                }) {
                    Text(text = "Cancelar pedido", color = CoralFatia)
                }
            },
            dismissButton = {
                TextButton(onClick = { pedidoParaExcluir = null }) {
                    Text("Voltar")
                }
            }
        )
    }
}

@Composable
fun CardDoPedido(
    pedido: Pedido,
    onEditar: (Long) -> Unit,
    onExcluir: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Pedido #${pedido.id}",
                fontWeight = FontWeight.Bold
            )

            LinhaDoResumo(titulo = "Pizza", valor = pedido.pizzaNome)
            LinhaDoResumo(titulo = "Tamanho", valor = pedido.tamanho)
            LinhaDoResumo(
                titulo = "Borda recheada",
                valor = if (pedido.borda) "Sim" else "Não"
            )
            LinhaDoResumo(titulo = "Quantidade", valor = "${pedido.quantidade}")
            LinhaDoResumo(titulo = "Total", valor = "R$ ${pedido.total}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { onEditar(pedido.id) }) {
                    Text("Alterar")
                }
                TextButton(onClick = { onExcluir() }) {
                    Text(text = "Cancelar pedido", color = CoralFatia)
                }
            }
        }
    }
}
