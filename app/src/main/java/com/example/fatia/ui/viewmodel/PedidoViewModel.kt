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

// VIEWMODEL DOS PEDIDOS (o "VM" do MVVM)
//
// Ele usa DOIS DAOs: o PedidoDAO para gravar e ler pedidos, e o PizzaDAO
// para descobrir o nome e o preco da pizza que foi escolhida no cardapio.
//
// Caminho: Tela -> PedidoViewModel -> DAO -> FatiaDatabase.
class PedidoViewModel(application: Application) : AndroidViewModel(application) {

    private val pedidoDAO = PedidoDAO(application)
    private val pizzaDAO = PizzaDAO(application)

    // ----- O PEDIDO QUE ESTA SENDO MONTADO OU EDITADO -----
    // A pizza escolhida (nome e preco vem do banco, nao da tela).
    var pizzaNome by mutableStateOf("")
    var precoBase by mutableStateOf(0)

    // As escolhas da pessoa. Comecam no padrao do mockup: M, sem borda, 1.
    var tamanho by mutableStateOf("M")
    var borda by mutableStateOf(false)
    var quantidade by mutableStateOf(1)

    // Lista da tela "Meus pedidos".
    var pedidos by mutableStateOf<List<Pedido>>(emptyList())

    // O pedido que a tela de confirmacao e a de editar estao mostrando.
    // null = ainda nao carregou (ou nao existe).
    var pedidoAtual by mutableStateOf<Pedido?>(null)

    // Mensagem de erro. Vazio = nada para mostrar.
    var mensagem by mutableStateOf("")

    // Chamado quando a tela de personalizar abre.
    // Busca a pizza no banco e volta as escolhas para o padrao, para nao
    // comecar com o tamanho e a quantidade do pedido anterior.
    fun carregarPizza(pizzaId: Long) {
        mensagem = ""

        val pizza = pizzaDAO.buscarPorId(pizzaId)
        if (pizza != null) {
            pizzaNome = pizza.nome
            precoBase = pizza.preco
        } else {
            // Pode acontecer se a pizza for excluida do cardapio no meio.
            pizzaNome = ""
            precoBase = 0
            mensagem = "Pizza não encontrada"
        }

        tamanho = "M"
        borda = false
        quantidade = 1
    }

    // Chamado pela tela de confirmacao e pela de editar o pedido.
    // Alem de guardar o pedido, joga os dados dele nos campos, para a tela
    // de editar ja aparecer com o que foi pedido antes.
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

    // Botao + da tela. O maximo de 10 e so para nao deixar passar de um
    // numero sem sentido.
    fun aumentarQuantidade() {
        if (quantidade < 10) {
            quantidade = quantidade + 1
        }
    }

    // Botao - da tela. Nao deixa descer de 1, senao o pedido ficaria vazio.
    fun diminuirQuantidade() {
        if (quantidade > 1) {
            quantidade = quantidade - 1
        }
    }

    // A CONTA DO PEDIDO
    //
    // Esta regra fica SO aqui, no viewModel. As telas apenas mostram o
    // resultado, entao a conta nunca aparece duplicada em dois lugares.
    //
    // P custa 5 reais menos, M e o preco normal, G custa 10 mais.
    // Borda recheada soma 8. No final, tudo vezes a quantidade.
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

    // CRIAR: monta o pedido com o que esta na tela e salva no banco.
    // Devolve o id gerado, ou 0 quando nao deu para salvar.
    fun criarPedido(): Long {
        // O pedido precisa saber de quem ele e: pegamos na sessao.
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            mensagem = "Faça login para pedir"
            return 0
        }

        val pedido = Pedido(
            // id 0 porque quem vai dar o numero e o banco, no insert.
            id = 0,
            usuarioId = usuarioId,
            pizzaNome = pizzaNome,
            precoBase = precoBase,
            tamanho = tamanho,
            borda = borda,
            quantidade = quantidade,
            // Guardamos o total ja calculado, para o pedido antigo continuar
            // com o valor que foi cobrado mesmo se a regra mudar depois.
            total = calcularTotal()
        )

        val id = pedidoDAO.inserir(pedido)
        mensagem = ""
        return id
    }

    // LISTAR: so os pedidos de quem esta logado.
    fun listar() {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            pedidos = emptyList()
        } else {
            pedidos = pedidoDAO.listarDoUsuario(usuarioId)
        }
    }

    // ALTERAR: grava o novo tamanho, borda e quantidade do pedido.
    // A pizza e o dono do pedido nao mudam: vem do pedidoAtual.
    // Devolve true quando gravou.
    fun salvarAlteracao(pedidoId: Long): Boolean {
        // Copiamos para uma variavel local para o Kotlin entender que
        // dentro do else ela nao e nula.
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
                // O total e recalculado com as escolhas novas.
                total = calcularTotal()
            )

            pedidoDAO.alterar(alterado)
            pedidoAtual = alterado
            mensagem = ""
            return true
        }
    }

    // DELETAR: o "Cancelar pedido" da lista.
    // Depois de apagar, pede a lista de novo para a tela ja ficar sem ele.
    fun excluir(pedidoId: Long) {
        pedidoDAO.excluir(pedidoId)
        listar()
    }
}
