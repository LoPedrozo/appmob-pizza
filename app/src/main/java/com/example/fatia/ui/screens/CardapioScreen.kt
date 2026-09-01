package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.FatiaTheme

// TELA 2 - CARDAPIO (HOME)
// ESQUELETO: substituir o conteudo pelo mockup (saudacao, campo de busca,
// categorias e a lista de pizzas com nome e preco). NAO mudar a assinatura
// da funcao, senao a MainActivity para de compilar.
//
// onFinalizarPedido e uma funcao recebida de fora: quando chamada, o app
// vai para a tela de confirmacao.
@Composable
fun CardapioScreen(
    modifier: Modifier = Modifier,
    onFinalizarPedido: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tela de Cardápio (em construção)")

        Button(onClick = onFinalizarPedido) {
            Text("Finalizar pedido")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardapioScreenPreview() {
    FatiaTheme {
        CardapioScreen(onFinalizarPedido = {})
    }
}
