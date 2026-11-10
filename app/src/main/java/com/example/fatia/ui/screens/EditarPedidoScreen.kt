package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

@Composable
fun EditarPedidoScreen(
    modifier: Modifier = Modifier,
    pedidoId: Long,
    viewModel: PedidoViewModel,
    onSalvo: () -> Unit
) {
    LaunchedEffect(pedidoId) {
        viewModel.carregarPedido(pedidoId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(
            titulo = "Editar pedido #$pedidoId",
            subtitulo = "${viewModel.pizzaNome} · preço base R$ ${viewModel.precoBase}"
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Tamanho", fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("P", "M", "G").forEach { tamanho ->
                        ChipTamanho(
                            texto = tamanho,
                            selecionado = tamanho == viewModel.tamanho,
                            onClick = { viewModel.tamanho = tamanho },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Borda recheada (+ R$ 8)")
                    Checkbox(
                        checked = viewModel.borda,
                        onCheckedChange = { viewModel.borda = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Quantidade")

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = { viewModel.diminuirQuantidade() }) {
                            Text(text = "–", color = AzulFatia, style = MaterialTheme.typography.titleLarge)
                        }
                        Text(text = "${viewModel.quantidade}", fontWeight = FontWeight.Bold)
                        TextButton(onClick = { viewModel.aumentarQuantidade() }) {
                            Text(text = "+", color = AzulFatia, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }

        if (viewModel.mensagem.isNotEmpty()) {
            Text(
                text = viewModel.mensagem,
                color = MaterialTheme.colorScheme.error
            )
        }

        BotaoPrincipal(
            texto = "Salvar alterações · R$ ${viewModel.calcularTotal()}",
            onClick = {
                if (viewModel.salvarAlteracao(pedidoId)) {
                    onSalvo()
                }
            }
        )
    }
}
