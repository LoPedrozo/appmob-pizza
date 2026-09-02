package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulEscuroFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.theme.FatiaTheme

// TELA 1 - LOGIN / CADASTRO
// Mockup estatico: nada aqui valida ou autentica nada, os botoes so existem
// para mostrar o visual da tela.
//
// onEntrar e uma funcao recebida de fora (contrato definido na MainActivity).
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEntrar: () -> Unit
) {
    // remember + mutableStateOf = a tela "lembra" o que foi digitado.
    // Serve so para o campo funcionar visualmente.
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Cores das bordas dos campos de texto (como foi feito em aula).
    val coresInputs = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )

    // Box de fora: pinta a tela inteira de azul.
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFatia)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Barra superior azul escuro do mockup.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(AzulEscuroFatia)
            )

            // Cartao branco arredondado, ocupando o resto da tela.
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                // verticalScroll deixa rolar quando o teclado abre.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Logo: circulo coral com a letra "F" branca no meio.
                    LogoFatia()

                    Text(
                        text = "Fatia",
                        color = AzulFatia,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = "Peça sua pizza favorita",
                        color = CinzaFatia,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Campo de e-mail (teclado com @ e ponto).
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        singleLine = true,
                        colors = coresInputs,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo de senha: PasswordVisualTransformation esconde o texto.
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha") },
                        singleLine = true,
                        colors = coresInputs,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Botao principal, largura total e fundo coral.
                    Button(
                        onClick = onEntrar,
                        colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Entrar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // "Criar conta": texto clicavel, ainda sem acao nenhuma.
                    Text(
                        text = "Criar conta",
                        color = AzulFatia,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

// Composable pequeno so para o logo, para nao deixar a tela gigante.
@Composable
fun LogoFatia(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(CoralFatia, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "F",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FatiaTheme {
        LoginScreen(onEntrar = {})
    }
}
