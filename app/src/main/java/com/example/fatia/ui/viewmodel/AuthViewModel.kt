package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.dal.UsuarioDAO
import com.example.fatia.data.model.Usuario

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDAO = UsuarioDAO(application)

    var mensagem by mutableStateOf("")

    fun login(email: String, senha: String): Boolean {
        val usuario = usuarioDAO.buscarPorEmail(email.trim().lowercase())

        if (usuario == null) {
            mensagem = "E-mail ou senha inválidos"
            return false
        }

        if (usuario.senha != senha) {
            mensagem = "E-mail ou senha inválidos"
            return false
        }

        mensagem = ""
        SessaoUsuario.iniciar(usuario)
        return true
    }

    fun cadastrar(nome: String, email: String, senha: String): Boolean {
        val nomeLimpo = nome.trim()
        val emailLimpo = email.trim().lowercase()

        if (nomeLimpo.isEmpty() || emailLimpo.isEmpty()) {
            mensagem = "Preencha o nome e o e-mail"
            return false
        }

        if (!emailLimpo.contains("@")) {
            mensagem = "Informe um e-mail válido"
            return false
        }

        if (usuarioDAO.buscarPorEmail(emailLimpo) != null) {
            mensagem = "Este e-mail já está cadastrado"
            return false
        }

        val novoUsuario = Usuario(
            id = 0,
            nome = nomeLimpo,
            email = emailLimpo,
            senha = senha
        )
        usuarioDAO.inserir(novoUsuario)

        mensagem = ""
        return true
    }

    fun limparMensagem() {
        mensagem = ""
    }
}
