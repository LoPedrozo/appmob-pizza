package com.example.fatia.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.AvaliacaoViewModel

@Composable
fun AvaliacaoFormScreen(
    modifier: Modifier = Modifier,
    avaliacaoId: Long,
    viewModel: AvaliacaoViewModel,
    onSalvo: () -> Unit,
    onVoltar: () -> Unit
) {
    LaunchedEffect(avaliacaoId) {
        viewModel.carregar(avaliacaoId)
    }

    val camera = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { imagem ->
        viewModel.receberFoto(imagem)
    }

    val titulo = if (avaliacaoId == 0L) {
        "Nova avaliação"
    } else {
        "Editar avaliação"
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
                    text = "Conte o que achou da pizza",
                    color = CinzaFatia,
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = viewModel.pizzaNome,
                    onValueChange = { viewModel.pizzaNome = it },
                    label = { Text("Pizza") },
                    singleLine = true,
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Nota",
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(1, 2, 3, 4, 5).forEach { valor ->
                        ChipTamanho(
                            texto = valor.toString(),
                            selecionado = valor == viewModel.nota,
                            onClick = { viewModel.nota = valor },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.comentario,
                    onValueChange = { viewModel.comentario = it },
                    label = { Text("Comentário") },
                    colors = coresInputs,
                    modifier = Modifier.fillMaxWidth()
                )

                val fotoAtual = viewModel.foto
                if (fotoAtual != null) {
                    Image(
                        bitmap = fotoAtual.asImageBitmap(),
                        contentDescription = "Foto da pizza",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

                val textoBotaoFoto = if (fotoAtual == null) {
                    "Tirar foto"
                } else {
                    "Trocar foto"
                }

                Button(
                    onClick = {
                        try {
                            camera.launch(null)
                        } catch (erro: Exception) {
                            viewModel.mensagem = "Não foi possível abrir a câmera"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulFatia),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = textoBotaoFoto,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

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
                        if (viewModel.salvar(avaliacaoId)) {
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
