package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AmareloFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CinzaChipFatia
import com.example.fatia.ui.theme.CinzaFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.viewmodel.PedidoViewModel

@Composable
fun CabecalhoDaTela(titulo: String, subtitulo: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = titulo,
            color = AzulFatia,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = subtitulo,
            color = CinzaFatia,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun BotaoPrincipal(texto: String, habilitado: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        colors = ButtonDefaults.buttonColors(
            containerColor = CoralFatia,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ListaVazia(icone: ImageVector, texto: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = CinzaFatia,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = texto,
            color = CinzaFatia,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun coresDosCampos(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = CoralFatia,
        unfocusedBorderColor = CinzaFatia.copy(alpha = 0.4f)
    )
}

@Composable
fun LinhaDoResumo(titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = titulo)
        Text(
            text = valor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LogoFatia() {
    Box(
        modifier = Modifier
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

@Composable
fun OpcoesDoPedido(viewModel: PedidoViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = "Tamanho", fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("P", "M", "G").forEach { tamanho ->
                ChipOpcao(
                    texto = tamanho,
                    selecionado = tamanho == viewModel.tamanho,
                    onClick = { viewModel.tamanho = tamanho },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Borda recheada (+ R$ 8)")
            Checkbox(
                checked = viewModel.borda,
                onCheckedChange = { viewModel.borda = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Quantidade")

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = { viewModel.diminuirQuantidade() }) {
                    Text(text = "–", color = AzulFatia, style = MaterialTheme.typography.titleLarge)
                }
                Text(text = "${viewModel.quantidade}", fontWeight = FontWeight.Bold)
                TextButton(onClick = { viewModel.aumentarQuantidade() }) {
                    Text(text = "+", color = AzulFatia, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
fun ChipOpcao(
    texto: String,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                if (selecionado) AmareloFatia else CinzaChipFatia,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold,
            color = AzulFatia
        )
    }
}
