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
import androidx.compose.foundation.rememberScrollState
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
import com.example.fatia.dados.RepositorioUsuarios
import com.example.fatia.dados.Usuario
import com.example.fatia.ui.theme.AzulEscuroFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.theme.FatiaTheme

// TELA DE CADASTRO
//
// Mesmo visual da tela de login. Assim como ela, esta tela nao conhece o
// navController: ela so avisa o que aconteceu chamando as funcoes que
// recebeu de fora.
//
// onCadastrado = chamada quando o cadastro deu certo.
// onVoltarParaLogin = chamada no texto "Ja tenho conta".
@Composable
fun CadastroScreen(
    modifier: Modifier = Modifier,
    onCadastrado: () -> Unit,
    onVoltarParaLogin: () -> Unit
) {
    // remember + mutableStateOf = a tela "lembra" o que foi digitado.
    // Sem isso o texto sumiria a cada recomposicao da tela.
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    // Guarda a mensagem de erro. Vazio = nenhum erro para mostrar.
    var erro by remember { mutableStateOf("") }

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

            // Barra superior azul escuro, igual a da tela de login.
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

                    // Mesmo logo da tela de login (funcao que ja existe
                    // no arquivo LoginScreen.kt, no mesmo pacote).
                    LogoFatia()

                    Text(
                        text = "Criar conta",
                        color = AzulFatia,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = "Preencha os seus dados",
                        color = CinzaFatia,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        singleLine = true,
                        colors = coresInputs,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        singleLine = true,
                        colors = coresInputs,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // PasswordVisualTransformation troca as letras por pontinhos.
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

                    OutlinedTextField(
                        value = confirmarSenha,
                        onValueChange = { confirmarSenha = it },
                        label = { Text("Confirmar senha") },
                        singleLine = true,
                        colors = coresInputs,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // A mensagem de erro so aparece quando existe erro.
                    if (erro.isNotEmpty()) {
                        Text(
                            text = erro,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // enabled = false deixa o botao apagado e sem clique.
                    // Ele so liga quando o formulario esta preenchido certo.
                    Button(
                        onClick = {
                            val novoUsuario = Usuario(
                                nome = nome,
                                email = email,
                                senha = senha
                            )
                            // cadastrar devolve false quando o e-mail ja existe.
                            if (RepositorioUsuarios.cadastrar(novoUsuario)) {
                                onCadastrado()
                            } else {
                                erro = "Este e-mail já está cadastrado"
                            }
                        },
                        enabled = nome.isNotEmpty() &&
                                email.isNotEmpty() &&
                                senha.length >= 4 &&
                                senha == confirmarSenha,
                        colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Cadastrar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Texto clicavel que volta para a tela de login.
                    Text(
                        text = "Já tenho conta",
                        color = AzulFatia,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVoltarParaLogin() }
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CadastroScreenPreview() {
    FatiaTheme {
        CadastroScreen(
            onCadastrado = {},
            onVoltarParaLogin = {}
        )
    }
}
