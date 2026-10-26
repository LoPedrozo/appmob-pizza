package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.dal.PizzaDAO
import com.example.fatia.data.model.Pizza

class PizzaViewModel(application: Application) : AndroidViewModel(application) {

    private val pizzaDAO = PizzaDAO(application)

    var pizzas by mutableStateOf<List<Pizza>>(emptyList())

    var nome by mutableStateOf("")
    var descricao by mutableStateOf("")
    var preco by mutableStateOf("")

    var mensagem by mutableStateOf("")

    val nomeUsuario: String
        get() = SessaoUsuario.nomeUsuario

    fun listar() {
        pizzas = pizzaDAO.listar()
    }

    fun carregar(id: Long) {
        mensagem = ""

        if (id == 0L) {
            nome = ""
            descricao = ""
            preco = ""
        } else {
            val pizza = pizzaDAO.buscarPorId(id)
            if (pizza != null) {
                nome = pizza.nome
                descricao = pizza.descricao
                preco = pizza.preco.toString()
            }
        }
    }

    fun salvar(id: Long): Boolean {
        val nomeLimpo = nome.trim()
        val descricaoLimpa = descricao.trim()

        if (nomeLimpo.isEmpty()) {
            mensagem = "Informe o nome da pizza"
            return false
        }

        if (descricaoLimpa.isEmpty()) {
            mensagem = "Informe a descrição da pizza"
            return false
        }

        val precoNumero = preco.trim().toIntOrNull()
        if (precoNumero == null || precoNumero <= 0) {
            mensagem = "Informe um preço maior que zero"
            return false
        }

        val pizza = Pizza(
            id = id,
            nome = nomeLimpo,
            descricao = descricaoLimpa,
            preco = precoNumero
        )

        if (id == 0L) {
            pizzaDAO.inserir(pizza)
        } else {
            pizzaDAO.alterar(pizza)
        }

        mensagem = ""
        return true
    }

    fun excluir(id: Long) {
        pizzaDAO.excluir(id)
        listar()
    }
}
