package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.dal.UsuarioDAO
import com.example.fatia.data.model.Usuario

// VIEWMODEL DO LOGIN E DO CADASTRO (o "VM" do MVVM)
//
// AndroidViewModel e um ViewModel que recebe o Application, ou seja, um
// Context. Precisamos dele porque o DAO pede um Context para abrir o banco.
//
// O caminho e sempre: Tela -> ViewModel -> DAO -> FatiaDatabase.
// A tela nunca fala com o banco direto.
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    // O DAO que este ViewModel usa. Criado uma vez, junto com o ViewModel.
    private val usuarioDAO = UsuarioDAO(application)

    // Mensagem de erro que a tela mostra. Vazio = nada para mostrar.
    // mutableStateOf = quando mudamos o valor, a tela se redesenha.
    var mensagem by mutableStateOf("")

    // LOGIN
    // Devolve true quando entrou e false quando os dados estao errados.
    fun login(email: String, senha: String): Boolean {
        // trim tira os espacos sobrando que o teclado do celular costuma por.
        val usuario = usuarioDAO.buscarPorEmail(email.trim())

        // Nao existe ninguem com esse e-mail.
        if (usuario == null) {
            mensagem = "E-mail ou senha inválidos"
            return false
        }

        // Existe, mas a senha digitada nao e a mesma do cadastro.
        // Mostramos a mesma mensagem nos dois casos de proposito, para nao
        // avisar a quem nao e o dono que aquele e-mail existe.
        if (usuario.senha != senha) {
            mensagem = "E-mail ou senha inválidos"
            return false
        }

        // Deu certo: limpa o erro antigo e abre a sessao.
        mensagem = ""
        SessaoUsuario.iniciar(usuario)
        return true
    }

    // CADASTRO
    // Devolve true quando salvou e false quando o e-mail ja estava em uso.
    fun cadastrar(nome: String, email: String, senha: String): Boolean {
        val emailLimpo = email.trim()

        // Antes de salvar, conferimos se esse e-mail ja esta no banco.
        if (usuarioDAO.buscarPorEmail(emailLimpo) != null) {
            mensagem = "Este e-mail já está cadastrado"
            return false
        }

        // id = 0 porque o usuario ainda nao existe no banco: quem vai dar
        // o numero do id e o proprio banco, no insert.
        val novoUsuario = Usuario(
            id = 0,
            nome = nome.trim(),
            email = emailLimpo,
            senha = senha
        )
        usuarioDAO.cadastrar(novoUsuario)

        mensagem = ""
        return true
    }

    // Usado pelas telas para apagar o erro antigo ao sair/entrar na tela.
    fun limparMensagem() {
        mensagem = ""
    }
}
