package com.example.fatia.data.model

data class Avaliacao(
    val id: Long,
    val usuarioId: Long,
    val pizzaNome: String,
    val nota: Int,
    val comentario: String,
    val foto: ByteArray?
)
