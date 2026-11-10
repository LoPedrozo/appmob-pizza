package com.example.fatia.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CabecalhoDaTela(titulo = titulo, subtitulo = "Conte o que achou da pizza")

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
                    value = viewModel.pizzaNome,
                    onValueChange = { viewModel.pizzaNome = it },
                    label = { Text("Pizza") },
                    singleLine = true,
                    colors = coresDosCampos(),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "Nota", fontWeight = FontWeight.Bold)

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
                    colors = coresDosCampos(),
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

                TextButton(
                    onClick = {
                        try {
                            camera.launch(null)
                        } catch (erro: Exception) {
                            viewModel.mensagem = "Não foi possível abrir a câmera"
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = AzulFatia),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Filled.PhotoCamera, contentDescription = null)
                    Text(
                        text = textoBotaoFoto,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
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
                if (viewModel.salvar(avaliacaoId)) {
                    onSalvo()
                }
            }
        )

        TextButton(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cancelar", color = AzulFatia)
        }
    }
}
