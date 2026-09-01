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

// TELA 1 - LOGIN
// ESQUELETO: substituir o conteudo pelo mockup (campos de e-mail e senha,
// botao Entrar e link "Criar conta"). NAO mudar a assinatura da funcao,
// senao a MainActivity para de compilar.
//
// onEntrar e uma funcao recebida de fora: quando chamada, o app vai
// para a tela de cardapio.
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEntrar: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tela de Login (em construção)")

        Button(onClick = onEntrar) {
            Text("Entrar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FatiaTheme {
        LoginScreen(onEntrar = {})
    }
}
