package com.example.fatia.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Login

@Serializable
data object Cadastro

@Serializable
data object Cardapio

@Serializable
data class Personalizar(val pizzaId: Long)

@Serializable
data class Confirmacao(val pedidoId: Long)

@Serializable
data object MeusPedidos

@Serializable
data class PizzaForm(val pizzaId: Long)

@Serializable
data class EditarPedido(val pedidoId: Long)
