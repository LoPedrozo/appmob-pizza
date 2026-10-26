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
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

// TELA - EDITAR PEDIDO (o "Alterar" do CRUD de pedidos)
//
// Mesmos controles da tela de personalizar (chips de tamanho, borda e
// quantidade), mas em cima de um pedido que JA esta no banco. A pizza nao
// muda: para trocar de pizza a pessoa faz outro pedido.
//
// pedidoId = id que veio como argumento da rota EditarPedido.
// onSalvo = chamada depois de gravar, para voltar para a lista.
@Composable
fun EditarPedidoScreen(
    modifier: Modifier = Modifier,
    pedidoId: Long,
    viewModel: PedidoViewModel,
    onSalvo: () -> Unit
) {
    // Busca o pedido no banco e preenche os campos do viewModel com o que
    // foi pedido antes. A chave e o pedidoId: se abrirmos outro pedido, o
    // bloco roda de novo.
    LaunchedEffect(pedidoId) {
        viewModel.carregarPedido(pedidoId)
    }

    // Box de fora: pinta a tela inteira de azul, igual as outras.
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFatia)
    ) {
        // Cartao branco arredondado por cima do azul.
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

                Text(
                    text = "Alterar pedido #$pedidoId",
                    color = AzulFatia,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                // Nome e preco base vem do pedido que esta no banco.
                Text(
                    text = viewModel.pizzaNome,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Preço base: R$ ${viewModel.precoBase}",
                    color = CinzaFatia,
                    style = MaterialTheme.typography.bodyMedium
                )

                // ----- SELETOR DE TAMANHO (P / M / G) -----
                // ChipTamanho e a mesma funcao usada na tela de personalizar
                // (ela esta no arquivo PersonalizarPizzaScreen.kt, mesmo
                // pacote), para nao escrever o chip duas vezes.
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

                // ----- BORDA RECHEADA -----
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    // SpaceBetween joga um item para cada ponta da linha.
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Borda recheada (+ R$ 8)")
                    Checkbox(
                        checked = viewModel.borda,
                        onCheckedChange = { viewModel.borda = it }
                    )
                }

                // ----- QUANTIDADE -----
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

                // A mensagem de erro so aparece quando existe erro.
                if (viewModel.mensagem.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagem,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Spacer com weight(1f) empurra o botao para o fim do cartao.
                Spacer(modifier = Modifier.weight(1f))

                // ----- BOTAO SALVAR -----
                Button(
                    onClick = {
                        // O viewModel monta o pedido alterado (com o total
                        // recalculado) e manda o DAO fazer o update.
                        if (viewModel.salvarAlteracao(pedidoId)) {
                            onSalvo()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    // O total e recalculado na hora, a cada toque nos chips,
                    // na borda ou na quantidade.
                    Text(
                        text = "Salvar alterações · R$ ${viewModel.calcularTotal()}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Sem @Preview: esta tela precisa de um PedidoViewModel, que so existe
// quando o app esta rodando (ele e criado no AppNavigation).
