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
data object Cardapio

// data class = rota COM argumento. O valor viaja junto com a rota,
// igual a um parametro de funcao.
@Serializable
data class Personalizar(val nomePizza: String)

@Serializable
data class Confirmacao(val nomePizza: String)
