package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.dal.PizzaDAO
import com.example.fatia.data.model.Pizza

// VIEWMODEL DAS PIZZAS (o "VM" do MVVM)
//
// Ele guarda o que as telas do cardapio e do formulario precisam mostrar e
// e o unico que conversa com o PizzaDAO.
//
// Caminho: Tela -> PizzaViewModel -> PizzaDAO -> FatiaDatabase.
class PizzaViewModel(application: Application) : AndroidViewModel(application) {

    private val pizzaDAO = PizzaDAO(application)

    // A lista que o cardapio desenha. Comeca vazia e e preenchida no listar().
    // mutableStateOf = quando a lista troca, a tela se redesenha sozinha.
    var pizzas by mutableStateOf<List<Pizza>>(emptyList())

    // ----- CAMPOS DO FORMULARIO -----
    // Sao String porque e o que o OutlinedTextField sabe mostrar e devolver.
    // O preco so vira numero na hora de salvar.
    var nome by mutableStateOf("")
    var descricao by mutableStateOf("")
    var preco by mutableStateOf("")

    // Mensagem de erro do formulario. Vazio = nada para mostrar.
    var mensagem by mutableStateOf("")

    // Nome de quem esta logado, usado na saudacao do cardapio.
    // Com o get() o valor e lido na hora em que a tela pergunta, entao se a
    // sessao mudar a saudacao muda junto.
    val nomeUsuario: String
        get() = SessaoUsuario.nomeUsuario

    // LISTAR: pede as pizzas para o banco e guarda na variavel pizzas.
    fun listar() {
        pizzas = pizzaDAO.listar()
    }

    // Prepara o formulario. Chamado quando a tela do formulario aparece.
    fun carregar(id: Long) {
        // Limpa um erro que tenha sobrado da ultima vez.
        mensagem = ""

        if (id == 0L) {
            // id 0 = pizza nova, entao os campos comecam vazios.
            nome = ""
            descricao = ""
            preco = ""
        } else {
            // Editar: busca a pizza no banco e joga os dados nos campos.
            val pizza = pizzaDAO.buscarPorId(id)
            if (pizza != null) {
                nome = pizza.nome
                descricao = pizza.descricao
                // toString porque o campo de texto mostra String, nao Int.
                preco = pizza.preco.toString()
            }
        }
    }

    // SALVAR: serve para criar e para alterar.
    // Devolve true quando gravou e false quando algum campo esta errado.
    fun salvar(id: Long): Boolean {
        // trim tira os espacos sobrando que o teclado costuma por.
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

        // toIntOrNull devolve null quando o texto nao e um numero inteiro
        // (por exemplo "abc" ou vazio). Assim o app nao quebra.
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
            // id 0 = ainda nao existe no banco, entao e um INSERT.
            pizzaDAO.inserir(pizza)
        } else {
            // Ja existe: e um UPDATE naquela linha.
            pizzaDAO.alterar(pizza)
        }

        mensagem = ""
        return true
    }

    // EXCLUIR: apaga a pizza e pede a lista de novo, para o cardapio
    // ja aparecer sem ela.
    fun excluir(id: Long) {
        pizzaDAO.excluir(id)
        listar()
    }
}
