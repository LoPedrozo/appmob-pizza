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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.viewmodel.PizzaViewModel

@Composable
fun PizzaFormScreen(
    modifier: Modifier = Modifier,
    pizzaId: Long,
    viewModel: PizzaViewModel,
    onSalvo: () -> Unit,
    onVoltar: () -> Unit
) {
    LaunchedEffect(pizzaId) {
        viewModel.carregar(pizzaId)
    }

    val titulo = if (pizzaId == 0L) {
        "Nova pizza"
    } else {
        "Editar pizza"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(titulo = titulo, subtitulo = "Preencha os dados da pizza")

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
                    value = viewModel.nome,
                    onValueChange = { viewModel.nome = it },
                    label = { Text("Nome") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.descricao,
                    onValueChange = { viewModel.descricao = it },
                    label = { Text("Descrição") },
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.preco,
                    onValueChange = { viewModel.preco = it },
                    label = { Text("Preço (R$)") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                if (viewModel.salvar(pizzaId)) {
                    onSalvo()
                }
            }
        )

        TextButton(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancelar", color = AzulFatia)
        }
    }
}
