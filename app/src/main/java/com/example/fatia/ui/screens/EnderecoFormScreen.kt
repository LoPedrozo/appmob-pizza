package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.viewmodel.EnderecoViewModel

@Composable
fun EnderecoFormScreen(
    modifier: Modifier = Modifier,
    enderecoId: Long,
    viewModel: EnderecoViewModel,
    onSalvo: () -> Unit,
    onVoltar: () -> Unit
) {
    LaunchedEffect(enderecoId) {
        viewModel.carregar(enderecoId)
    }

    val titulo = if (enderecoId == 0L) {
        "Novo endereço"
    } else {
        "Editar endereço"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(titulo = titulo, subtitulo = "Digite o CEP e toque em Buscar CEP")

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.apelido,
                    onValueChange = { viewModel.apelido = it },
                    label = { Text("Apelido (ex.: Casa)") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.cep,
                    onValueChange = { viewModel.cep = it },
                    label = { Text("CEP") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = { viewModel.buscarCep() },
                    enabled = !viewModel.buscandoCep,
                    colors = ButtonDefaults.textButtonColors(contentColor = AzulFatia),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    Text(
                        text = "Buscar CEP",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                if (viewModel.buscandoCep) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                if (viewModel.mensagemCep.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagemCep,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = viewModel.rua,
                    onValueChange = { viewModel.rua = it },
                    label = { Text("Rua") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.numero,
                    onValueChange = { viewModel.numero = it },
                    label = { Text("Número") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.bairro,
                    onValueChange = { viewModel.bairro = it },
                    label = { Text("Bairro") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.cidade,
                    onValueChange = { viewModel.cidade = it },
                    label = { Text("Cidade") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (viewModel.mensagem.isNotEmpty()) {
            Text(
                text = viewModel.mensagem,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        BotaoPrincipal(
            texto = "Salvar",
            onClick = {
                if (viewModel.salvar(enderecoId)) {
                    onSalvo()
                }
            }
        )

        TextButton(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancelar", color = AzulFatia)
        }
    }
}
