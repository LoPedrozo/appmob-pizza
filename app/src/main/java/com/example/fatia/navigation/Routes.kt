package com.example.fatia.navigation

import kotlinx.serialization.Serializable

// AS ROTAS DO APP
//
// Cada rota e um tipo Kotlin. O NavHost usa esse tipo para saber
// qual tela deve aparecer, em vez de usar textos soltos como "login".
//
// @Serializable vem da biblioteca kotlinx-serialization. Ela transforma
// o objeto da rota em dados que o NavHost consegue guardar na pilha e
// reconstruir depois. E por causa disso que da para ter argumento com
// tipo (String, Int...) em vez de tudo virar texto.

// data object = rota SEM argumento. So existe uma copia dela.
@Serializable
data object Login

@Serializable
data object Cadastro

@Serializable
data object Cardapio

// data class = rota COM argumento. O valor viaja junto com a rota,
// igual a um parametro de funcao.
//
// Agora as rotas levam IDs, e nao mais textos: com o id a tela pede os
// dados para o banco. Antes o nome da pizza viajava de tela em tela.
@Serializable
data class Personalizar(val pizzaId: Long)

@Serializable
data class Confirmacao(val pedidoId: Long)

// Lista de pedidos do usuario logado. Nao precisa de argumento porque o
// viewModel pega o usuario na sessao.
@Serializable
data object MeusPedidos

// Rota do formulario de pizza. Leva o id da pizza que vai ser editada.
// pizzaId = 0 quer dizer "pizza nova", ou seja, formulario vazio.
// E a mesma tela para cadastrar e para alterar.
@Serializable
data class PizzaForm(val pizzaId: Long)

// Tela de alterar um pedido que ja existe. Leva o id do pedido.
@Serializable
data class EditarPedido(val pedidoId: Long)
