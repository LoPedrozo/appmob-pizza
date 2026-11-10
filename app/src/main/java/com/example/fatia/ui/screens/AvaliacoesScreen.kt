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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fatia.data.model.Avaliacao
import com.example.fatia.ui.theme.AzulFatia
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

        CabecalhoDaTela(titulo = "Minhas avaliações", subtitulo = "O que você achou das pizzas")

        BotaoPrincipal(texto = "Nova avaliação", onClick = { onNovaAvaliacao() })

        if (viewModel.avaliacoes.isEmpty()) {
            ListaVazia(icone = Icons.Filled.Star, texto = "Você ainda não avaliou nenhuma pizza")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    Text(text = "Cancelar", color = AzulFatia)
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = avaliacao.pizzaNome, fontWeight = FontWeight.Bold)
            Text(text = "Nota: ${avaliacao.nota} de 5", color = CoralFatia, fontWeight = FontWeight.Bold)
            if (avaliacao.comentario.isNotEmpty()) {
                Text(text = avaliacao.comentario, color = CinzaFatia)
            }

            Row {
                TextButton(onClick = { onEditar(avaliacao.id) }) {
                    Text(text = "Editar", color = AzulFatia)
                }
                TextButton(onClick = { onExcluir() }) {
                    Text(text = "Excluir", color = CoralFatia)
                }
            }
        }
    }
}
