package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AmareloFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

@Composable
fun PersonalizarPizzaScreen(
    modifier: Modifier = Modifier,
    pizzaId: Long,
    viewModel: PedidoViewModel,
    onPedidoCriado: (Long) -> Unit,
    onVoltar: () -> Unit
) {
    LaunchedEffect(pizzaId) {
        viewModel.carregarPizza(pizzaId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(
            titulo = viewModel.pizzaNome,
            subtitulo = "Preço base: R$ ${viewModel.precoBase}"
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(AmareloFatia, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍕",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

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
            texto = "Adicionar · R$ ${viewModel.calcularTotal()}",
            onClick = {
                val id = viewModel.criarPedido()
                if (id > 0) {
                    onPedidoCriado(id)
                }
            }
        )

        TextButton(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancelar", color = AzulFatia)
        }
    }
}
