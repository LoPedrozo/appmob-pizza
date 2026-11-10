package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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

    val subtitulo = if (pedido == null) {
        "Pedido não encontrado"
    } else {
        "Estamos preparando o seu pedido"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(titulo = "Pedido confirmado!", subtitulo = subtitulo)

        if (pedido != null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = "Pedido confirmado",
                            tint = VerdeFatia,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(text = "Pedido #${pedido.id}", fontWeight = FontWeight.Bold)
                    }
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
        }

        BotaoPrincipal(texto = "Ver meus pedidos", onClick = { onVerPedidos() })

        TextButton(onClick = { onVoltarCardapio() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar ao cardápio", color = AzulFatia)
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
