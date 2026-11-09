package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
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

    val coresInputs = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )

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
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = titulo,
                    color = AzulFatia,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Digite o CEP e toque em Buscar CEP",
                    color = CinzaFatia,
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = viewModel.apelido,
                    onValueChange = { viewModel.apelido = it },
                    label = { Text("Apelido (ex.: Casa)") },
                    singleLine = true,
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.cep,
                    onValueChange = { viewModel.cep = it },
                    label = { Text("CEP") },
                    singleLine = true,
                    colors = coresInputs,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.buscarCep() },
                    enabled = !viewModel.buscandoCep,
                    colors = ButtonDefaults.buttonColors(containerColor = AzulFatia),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Buscar CEP",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
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
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.numero,
                    onValueChange = { viewModel.numero = it },
                    label = { Text("Número") },
                    singleLine = true,
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.bairro,
                    onValueChange = { viewModel.bairro = it },
                    label = { Text("Bairro") },
                    singleLine = true,
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.cidade,
                    onValueChange = { viewModel.cidade = it },
                    label = { Text("Cidade") },
                    singleLine = true,
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                if (viewModel.mensagem.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagem,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        if (viewModel.salvar(enderecoId)) {
                            onSalvo()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Salvar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Cancelar",
                    color = AzulFatia,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onVoltar() }
                        .padding(8.dp)
                )
            }
        }
    }
}
