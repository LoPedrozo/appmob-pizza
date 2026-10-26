package com.example.fatia.data.model

// MODEL DA PIZZA
//
// Mesma ideia do Usuario: um campo para cada coluna da tabela "pizzas".
// O preco e Int porque no app trabalhamos com reais inteiros (39, 35...).
data class Pizza(
    val id: Long,
    val nome: String,
    val descricao: String,
    val preco: Int
)
