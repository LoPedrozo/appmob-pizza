package com.example.fatia.data.model

data class Endereco(
    val id: Long,
    val usuarioId: Long,
    val apelido: String,
    val cep: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String
)
