package com.example.fatia.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.fatia.data.model.Usuario

// A SESSAO DO APP (quem esta logado agora)
//
// object = existe UMA copia so dele no app inteiro, criada automaticamente.
// E por isso que qualquer tela pode perguntar quem esta logado.
//
// Os valores usam mutableStateOf para o Compose redesenhar as telas sozinho
// quando alguem entra ou sai (por exemplo o "Ola, nome!" do cardapio).
//
// A sessao vive na memoria: ao fechar o app o usuario precisa logar de novo.
object SessaoUsuario {

    // null = ninguem logado. Guardamos o id porque e ele que vai junto
    // com cada pedido salvo no banco.
    var usuarioId by mutableStateOf<Long?>(null)

    // Nome do usuario logado, usado para aparecer na tela.
    var nomeUsuario by mutableStateOf("")

    // Chamado quando o login da certo.
    fun iniciar(usuario: Usuario) {
        usuarioId = usuario.id
        nomeUsuario = usuario.nome
    }

    // Chamado no botao "Sair" do menu.
    fun encerrar() {
        usuarioId = null
        nomeUsuario = ""
    }
}
