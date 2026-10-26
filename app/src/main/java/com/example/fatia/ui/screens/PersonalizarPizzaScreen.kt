package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AmareloFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

val CinzaChipFatia = Color(0xFFF0F0F0)

@Composable
fun PersonalizarPizzaScreen(
    modifier: Modifier = Modifier,
    pizzaId: Long,
    viewModel: PedidoViewModel,
    onPedidoCriado: (Long) -> Unit
) {
    LaunchedEffect(pizzaId) {
        viewModel.carregarPizza(pizzaId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFatia)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(AmareloFatia, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍕",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

                Text(
                    text = viewModel.pizzaNome,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = AzulFatia
                )
                Text(
                    text = "Preço base: R$ ${viewModel.precoBase}",
                    color = CinzaFatia,
                    style = MaterialTheme.typography.bodyMedium
                )

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

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "–",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.clickable { viewModel.diminuirQuantidade() }
                        )
                        Text(text = "${viewModel.quantidade}")
                        Text(
                            text = "+",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.clickable { viewModel.aumentarQuantidade() }
                        )
                    }
                }

                if (viewModel.mensagem.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagem,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        val id = viewModel.criarPedido()
                        if (id > 0) {
                            onPedidoCriado(id)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Adicionar · R$ ${viewModel.calcularTotal()}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ChipTamanho(
    texto: String,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                if (selecionado) AmareloFatia else CinzaChipFatia,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold,
            color = AzulFatia
        )
    }
}
