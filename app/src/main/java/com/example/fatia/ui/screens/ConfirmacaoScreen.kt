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

// TELA 3 - CONFIRMACAO DO PEDIDO
// ESQUELETO: substituir o conteudo pelo mockup (icone de sucesso, numero
// do pedido, status da entrega e botao "Acompanhar pedido"). NAO mudar a
// assinatura da funcao, senao a MainActivity para de compilar.
//
// onVoltarAoCardapio e uma funcao recebida de fora: quando chamada, o app
// volta para a tela de cardapio.
@Composable
fun ConfirmacaoScreen(
    modifier: Modifier = Modifier,
    onVoltarAoCardapio: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tela de Confirmação (em construção)")

        Button(onClick = onVoltarAoCardapio) {
            Text("Voltar ao cardápio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmacaoScreenPreview() {
    FatiaTheme {
        ConfirmacaoScreen(onVoltarAoCardapio = {})
    }
}
