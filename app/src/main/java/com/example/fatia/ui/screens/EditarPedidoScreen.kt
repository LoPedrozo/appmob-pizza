package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

@Composable
fun EditarPedidoScreen(
    modifier: Modifier = Modifier,
    pedidoId: Long,
    viewModel: PedidoViewModel,
    onSalvo: () -> Unit,
    onVoltar: () -> Unit
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
            Column(modifier = Modifier.padding(16.dp)) {
                OpcoesDoPedido(viewModel = viewModel)
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

        TextButton(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancelar", color = AzulFatia)
        }
    }
}
