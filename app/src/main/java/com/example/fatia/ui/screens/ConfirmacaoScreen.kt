package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.R
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.theme.VerdeFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

@Composable
fun ConfirmacaoScreen(
    modifier: Modifier = Modifier,
    pedidoId: Long,
    viewModel: PedidoViewModel,
    onVerPedidos: () -> Unit,
    onVoltarCardapio: () -> Unit
) {
    LaunchedEffect(pedidoId) {
        viewModel.carregarPedido(pedidoId)
    }

    val pedido = viewModel.pedidoAtual

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_check),
            contentDescription = "Pedido confirmado",
            tint = VerdeFatia,
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "Pedido confirmado!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        if (pedido == null) {
            Text(text = "Pedido não encontrado.")
        } else {
            Text(text = "Pedido #${pedido.id}")

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Resumo do pedido",
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
                }
            }

            Text(text = "Estamos preparando o seu pedido.")
        }

        Button(
            onClick = { onVerPedidos() },
            colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "Ver meus pedidos",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        TextButton(
            onClick = { onVoltarCardapio() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Voltar ao cardápio",
                color = AzulFatia,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LinhaDoResumo(titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = titulo)
        Text(
            text = valor,
            fontWeight = FontWeight.Bold
        )
    }
}
