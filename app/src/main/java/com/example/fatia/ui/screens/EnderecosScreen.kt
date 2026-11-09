package com.example.fatia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.data.model.Endereco
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.EnderecoViewModel

@Composable
fun EnderecosScreen(
    modifier: Modifier = Modifier,
    viewModel: EnderecoViewModel,
    onNovoEndereco: () -> Unit,
    onEditarEndereco: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    var enderecoParaExcluir by remember { mutableStateOf<Endereco?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Meus endereços",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Button(
            onClick = { onNovoEndereco() },
            colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Novo endereço",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        if (viewModel.enderecos.isEmpty()) {
            Text(
                text = "Você ainda não cadastrou nenhum endereço",
                color = CinzaFatia
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.enderecos) { endereco ->
                    CardDoEndereco(
                        endereco = endereco,
                        onEditar = onEditarEndereco,
                        onExcluir = { enderecoParaExcluir = endereco }
                    )
                }
            }
        }
    }

    val enderecoDoDialogo = enderecoParaExcluir
    if (enderecoDoDialogo != null) {
        AlertDialog(
            onDismissRequest = { enderecoParaExcluir = null },
            title = { Text("Excluir endereço") },
            text = { Text("Quer excluir o endereço ${enderecoDoDialogo.apelido}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.excluir(enderecoDoDialogo.id)
                    enderecoParaExcluir = null
                }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            },
            dismissButton = {
                TextButton(onClick = { enderecoParaExcluir = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CardDoEndereco(
    endereco: Endereco,
    onEditar: (Long) -> Unit,
    onExcluir: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = endereco.apelido,
                fontWeight = FontWeight.Bold
            )
            Text(text = "${endereco.rua}, ${endereco.numero}")
            Text(text = "${endereco.bairro} - ${endereco.cidade}")
            Text(
                text = "CEP: ${endereco.cep}",
                color = CinzaFatia
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { onEditar(endereco.id) }) {
                    Text("Editar")
                }
                TextButton(onClick = { onExcluir() }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            }
        }
    }
}
