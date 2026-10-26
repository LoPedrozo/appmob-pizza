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

// TELA 4 - CONFIRMACAO DO PEDIDO
//
// Agora a tela recebe o ID do pedido e pede os dados dele para o banco.
// Antes o numero do pedido era fixo ("#1042") e so o nome da pizza viajava
// pela rota.
//
// pedidoId = id que veio como argumento da rota Confirmacao.
// onVerPedidos = botao "Ver meus pedidos".
// onVoltarCardapio = botao "Voltar ao cardapio".
@Composable
fun ConfirmacaoScreen(
    modifier: Modifier = Modifier,
    pedidoId: Long,
    viewModel: PedidoViewModel,
    onVerPedidos: () -> Unit,
    onVoltarCardapio: () -> Unit
) {
    // Busca o pedido no banco quando a tela aparece.
    LaunchedEffect(pedidoId) {
        viewModel.carregarPedido(pedidoId)
    }

    // Copiamos para uma variavel local porque assim o Kotlin entende que
    // dentro do else ela nao e mais nula e deixa usar o .pizzaNome.
    val pedido = viewModel.pedidoAtual

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ----- ICONE DE SUCESSO -----
        Icon(
            painter = painterResource(R.drawable.ic_check),
            contentDescription = "Pedido confirmado",
            // verde de sucesso, igual ao mockup da tela 5
            tint = VerdeFatia,
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "Pedido confirmado!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            // secondary = azul do mockup, usado nos titulos
            color = MaterialTheme.colorScheme.secondary
        )

        if (pedido == null) {
            // Aparece no instante entre a tela abrir e o banco responder,
            // ou se o pedido tiver sido cancelado.
            Text(text = "Pedido não encontrado.")
        } else {
            // O numero do pedido e o id que o banco gerou no insert.
            Text(text = "Pedido #${pedido.id}")

            // ----- CARD COM O RESUMO DO PEDIDO -----
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Resumo do pedido",
                        fontWeight = FontWeight.Bold
                    )
                    // Cada linha do resumo: titulo na esquerda, valor na
                    // direita. Todos os valores vem do banco.
                    LinhaDoResumo(titulo = "Pizza", valor = pedido.pizzaNome)
                    LinhaDoResumo(titulo = "Tamanho", valor = pedido.tamanho)
                    LinhaDoResumo(
                        titulo = "Borda recheada",
                        // No banco borda e 0 ou 1; aqui viramos texto.
                        valor = if (pedido.borda) "Sim" else "Não"
                    )
                    LinhaDoResumo(titulo = "Quantidade", valor = "${pedido.quantidade}")
                    LinhaDoResumo(titulo = "Total", valor = "R$ ${pedido.total}")
                }
            }

            Text(text = "Estamos preparando o seu pedido.")
        }

        // ----- BOTOES -----
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

// Uma linha do resumo. Separada numa funcao para nao repetir codigo.
// A tela "Meus pedidos" tambem usa esta funcao.
@Composable
fun LinhaDoResumo(titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        // SpaceBetween joga um texto para cada ponta da linha.
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = titulo)
        Text(
            text = valor,
            fontWeight = FontWeight.Bold
        )
    }
}

// Sem @Preview: esta tela agora precisa de um PedidoViewModel, que so
// existe quando o app esta rodando (ele e criado no AppNavigation).
