package com.example.fatia.data.model

// MODEL DO PEDIDO
//
// Um campo para cada coluna da tabela "pedidos".
//
// usuarioId diz de quem e o pedido (e o id do usuario logado).
//
// Repare que guardamos pizzaNome e precoBase, e nao o id da pizza.
// Foi uma escolha nossa: assim o pedido continua existindo do jeito que
// foi feito mesmo que a pizza seja excluida do cardapio depois.
//
// borda e Boolean aqui no Kotlin, mas no banco vira 0 ou 1, porque o
// SQLite nao tem o tipo boolean.
data class Pedido(
    val id: Long,
    val usuarioId: Long,
    val pizzaNome: String,
    val precoBase: Int,
    val tamanho: String,
    val borda: Boolean,
    val quantidade: Int,
    val total: Int
)
