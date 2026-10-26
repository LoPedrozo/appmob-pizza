package com.example.fatia.data.model

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
