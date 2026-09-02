package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fatia.R
import com.example.fatia.ui.theme.FatiaTheme

// TELA 3 - CONFIRMACAO DO PEDIDO
// nomePizza = nome da pizza que o usuario escolheu no cardapio.
// onVoltarClick = funcao recebida de fora, chamada no botao "Voltar ao inicio".
@Composable
fun ConfirmacaoScreen(
    modifier: Modifier = Modifier,
    nomePizza: String,
    onVoltarClick: () -> Unit
) {
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "Pedido confirmado!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        // Numero do pedido fixo, so para o mockup.
        Text(text = "Pedido #1042")

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
                // Cada linha do resumo: titulo na esquerda, valor na direita.
                LinhaDoResumo(titulo = "Pizza", valor = nomePizza)
                LinhaDoResumo(titulo = "Tempo estimado", valor = "35 minutos")
                LinhaDoResumo(titulo = "Entrega", valor = "Delivery em casa")
            }
        }

        Text(text = "Estamos preparando o seu pedido.")

        // Ao clicar, chama a funcao que veio de fora.
        Button(
            onClick = onVoltarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar ao início")
        }
    }
}

// Uma linha do resumo. Separada numa funcao para nao repetir codigo.
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

@Preview(showBackground = true)
@Composable
fun ConfirmacaoScreenPreview() {
    FatiaTheme {
        ConfirmacaoScreen(
            nomePizza = "Calabresa",
            onVoltarClick = {}
        )
    }
}
