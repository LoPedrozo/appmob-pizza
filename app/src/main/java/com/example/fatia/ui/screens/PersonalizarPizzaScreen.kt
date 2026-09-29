package com.example.fatia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fatia.ui.theme.AmareloFatia
import com.example.fatia.ui.theme.AzulFatia
import com.example.fatia.ui.theme.CoralFatia
import com.example.fatia.ui.theme.FatiaTheme

// Cinza do chip de tamanho quando ele NAO esta selecionado.
// As outras cores da tela (azul, coral e amarelo) ja existem no
// arquivo ui/theme/Color.kt e sao importadas ali em cima.
// O formato e 0xAARRGGBB: AA = transparencia, depois vermelho, verde e azul.
val CinzaChipFatia = Color(0xFFF0F0F0)

// TELA 3 - PERSONALIZAR PIZZA
// O preco e fixo em R$ 39 e a quantidade fica sempre em 1, igual ao mockup.
//
// nomePizza = nome que veio como argumento da rota Personalizar.
// onAdicionar = funcao recebida de fora, chamada no botao "Adicionar".
// A tela nao conhece o navController: quem navega e o AppNavigation.
@Composable
fun PersonalizarPizzaScreen(
    modifier: Modifier = Modifier,
    nomePizza: String,
    onAdicionar: (String) -> Unit
) {

    // remember + mutableStateOf = variavel que a tela "lembra" e observa.
    // Serve so para o componente se destacar quando a pessoa toca nele.
    // Comeca no "M" e com a borda marcada, igual ao mockup.
    var tamanhoSelecionado by remember { mutableStateOf("M") }
    var bordaRecheada by remember { mutableStateOf(true) }

    // A quantidade e fixa nesta etapa (os botoes - e + sao so visuais).
    val quantidade = 1

    // Box de fora: pinta a tela inteira de azul.
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFatia)
    ) {
        // Cartao branco arredondado, ocupando a maior parte da tela.
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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // ----- FOTO DA PIZZA (placeholder) -----
                // Nao e imagem de verdade: e so um retangulo amarelo
                // com o desenho da pizza no meio.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(AmareloFatia, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🍕",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

                // ----- NOME DA PIZZA -----
                // Vem da rota, nao e mais um texto fixo.
                Text(
                    text = nomePizza,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = AzulFatia
                )

                // ----- SELETOR DE TAMANHO (P / M / G) -----
                // forEach percorre a lista e desenha um chip para cada texto.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("P", "M", "G").forEach { tamanho ->
                        ChipTamanho(
                            texto = tamanho,
                            // So fica amarelo o chip que estiver selecionado.
                            selecionado = tamanho == tamanhoSelecionado,
                            onClick = { tamanhoSelecionado = tamanho },
                            // weight(1f) faz os tres chips dividirem a linha
                            // em partes iguais.
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // ----- BORDA RECHEADA -----
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    // SpaceBetween joga um item para cada ponta da linha.
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Borda recheada")
                    Checkbox(
                        checked = bordaRecheada,
                        onCheckedChange = { bordaRecheada = it }
                    )
                }

                // ----- QUANTIDADE -----
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Quantidade")

                    // Linha da direita: - 1 +
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Os sinais sao apenas visuais: o clique nao faz nada.
                        Text(
                            text = "–",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.clickable { }
                        )
                        Text(text = "$quantidade")
                        Text(
                            text = "+",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.clickable { }
                        )
                    }
                }

                // Spacer com weight(1f) ocupa todo o espaco que sobra
                // e empurra o botao para o fim do cartao.
                Spacer(modifier = Modifier.weight(1f))

                // ----- BOTAO ADICIONAR -----
                Button(
                    onClick = { onAdicionar(nomePizza) },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralFatia),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Adicionar · R$ 39",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Um chip do seletor de tamanho.
// Foi separado numa funcao propria para nao repetir o mesmo codigo tres vezes.
// selecionado = true deixa o fundo amarelo; false deixa cinza.
@Composable
fun ChipTamanho(
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
                RoundedCornerShape(8.dp)
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

@Preview(showBackground = true)
@Composable
fun PersonalizarPizzaScreenPreview() {
    FatiaTheme {
        PersonalizarPizzaScreen(
            modifier = Modifier.fillMaxSize(),
            nomePizza = "Pizza calabresa",
            onAdicionar = {}
        )
    }
}
