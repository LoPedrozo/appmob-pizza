package com.example.fatia.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.data.model.Avaliacao
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.AvaliacaoViewModel

@Composable
fun AvaliacoesScreen(
    modifier: Modifier = Modifier,
    viewModel: AvaliacaoViewModel,
    onNovaAvaliacao: () -> Unit,
    onEditarAvaliacao: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.listar()
    }

    var avaliacaoParaExcluir by remember { mutableStateOf<Avaliacao?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Minhas avaliações",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Button(
            onClick = { onNovaAvaliacao() },
            colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Nova avaliação",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        if (viewModel.avaliacoes.isEmpty()) {
            Text(
                text = "Você ainda não avaliou nenhuma pizza",
                color = CinzaFatia
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.avaliacoes) { avaliacao ->
                    CardDaAvaliacao(
                        avaliacao = avaliacao,
                        viewModel = viewModel,
                        onEditar = onEditarAvaliacao,
                        onExcluir = { avaliacaoParaExcluir = avaliacao }
                    )
                }
            }
        }
    }

    val avaliacaoDoDialogo = avaliacaoParaExcluir
    if (avaliacaoDoDialogo != null) {
        AlertDialog(
            onDismissRequest = { avaliacaoParaExcluir = null },
            title = { Text("Excluir avaliação") },
            text = { Text("Quer excluir a avaliação da pizza ${avaliacaoDoDialogo.pizzaNome}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.excluir(avaliacaoDoDialogo.id)
                    avaliacaoParaExcluir = null
                }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            },
            dismissButton = {
                TextButton(onClick = { avaliacaoParaExcluir = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CardDaAvaliacao(
    avaliacao: Avaliacao,
    viewModel: AvaliacaoViewModel,
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
            val foto = viewModel.fotoDaAvaliacao(avaliacao)
            if (foto != null) {
                Image(
                    bitmap = foto.asImageBitmap(),
                    contentDescription = "Foto da pizza ${avaliacao.pizzaNome}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            }

            Text(
                text = avaliacao.pizzaNome,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Nota: ${avaliacao.nota} de 5")
            if (avaliacao.comentario.isNotEmpty()) {
                Text(
                    text = avaliacao.comentario,
                    color = CinzaFatia
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { onEditar(avaliacao.id) }) {
                    Text("Editar")
                }
                TextButton(onClick = { onExcluir() }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            }
        }
    }
}
