package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.dal.PedidoDAO
import com.example.fatia.data.dal.PizzaDAO
import com.example.fatia.data.model.Pedido

class PedidoViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoDAO = PedidoDAO(application)
    private val pizzaDAO = PizzaDAO(application)

    var pizzaNome by mutableStateOf("")
    var precoBase by mutableStateOf(0)

    var tamanho by mutableStateOf("M")
    var borda by mutableStateOf(false)
    var quantidade by mutableStateOf(1)

    var pedidos by mutableStateOf<List<Pedido>>(emptyList())

    var pedidoAtual by mutableStateOf<Pedido?>(null)

    var mensagem by mutableStateOf("")

    fun carregarPizza(pizzaId: Long) {
        mensagem = ""

        val pizza = pizzaDAO.buscarPorId(pizzaId)
        if (pizza != null) {
            pizzaNome = pizza.nome
            precoBase = pizza.preco
        } else {
            pizzaNome = ""
            precoBase = 0
            mensagem = "Pizza não encontrada"
        }

        tamanho = "M"
        borda = false
        quantidade = 1
    }

    fun carregarPedido(pedidoId: Long) {
        mensagem = ""

        val pedido = pedidoDAO.buscarPorId(pedidoId)
        pedidoAtual = pedido

        if (pedido != null) {
            pizzaNome = pedido.pizzaNome
            precoBase = pedido.precoBase
            tamanho = pedido.tamanho
            borda = pedido.borda
            quantidade = pedido.quantidade
        } else {
            mensagem = "Pedido não encontrado"
        }
    }

    fun aumentarQuantidade() {
        if (quantidade < 10) {
            quantidade = quantidade + 1
        }
    }

    fun diminuirQuantidade() {
        if (quantidade > 1) {
            quantidade = quantidade - 1
        }
    }

    fun calcularTotal(): Int {
        var preco = precoBase

        if (tamanho == "P") {
            preco = precoBase - 5
        } else if (tamanho == "G") {
            preco = precoBase + 10
        }

        if (borda) {
            preco = preco + 8
        }

        return preco * quantidade
    }

    fun criarPedido(): Long {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            mensagem = "Faça login para pedir"
            return 0
        }

        val pedido = Pedido(
            id = 0,
            usuarioId = usuarioId,
            pizzaNome = pizzaNome,
            precoBase = precoBase,
            tamanho = tamanho,
            borda = borda,
            quantidade = quantidade,
            total = calcularTotal()
        )

        val id = pedidoDAO.inserir(pedido)
        mensagem = ""
        return id
    }

    fun listar() {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            pedidos = emptyList()
        } else {
            pedidos = pedidoDAO.listarDoUsuario(usuarioId)
        }
    }

    fun salvarAlteracao(pedidoId: Long): Boolean {
        val pedido = pedidoAtual

        if (pedido == null) {
            mensagem = "Pedido não encontrado"
            return false
        } else {
            val alterado = Pedido(
                id = pedidoId,
                usuarioId = pedido.usuarioId,
                pizzaNome = pedido.pizzaNome,
                precoBase = pedido.precoBase,
                tamanho = tamanho,
                borda = borda,
                quantidade = quantidade,
                total = calcularTotal()
            )

            pedidoDAO.alterar(alterado)
            pedidoAtual = alterado
            mensagem = ""
            return true
        }
    }

    fun excluir(pedidoId: Long) {
        pedidoDAO.excluir(pedidoId)
        listar()
    }
}
