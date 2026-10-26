package com.example.fatia.data.model

// MODEL DO USUARIO
//
// data class = classe feita so para guardar dados.
// Cada campo aqui e uma coluna da tabela "usuarios" do banco.
// O id vem do banco (AUTOINCREMENT). Usamos id = 0 quando o usuario
// ainda nao foi salvo, porque nesse momento o banco e quem vai dar o numero.
data class Usuario(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String
)
