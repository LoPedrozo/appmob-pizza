package com.example.fatia.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.fatia.data.model.Usuario

object SessaoUsuario {

    var usuarioId by mutableStateOf<Long?>(null)

    var nomeUsuario by mutableStateOf("")

    fun iniciar(usuario: Usuario) {
        usuarioId = usuario.id
        nomeUsuario = usuario.nome
    }

    fun encerrar() {
        usuarioId = null
        nomeUsuario = ""
    }
}
