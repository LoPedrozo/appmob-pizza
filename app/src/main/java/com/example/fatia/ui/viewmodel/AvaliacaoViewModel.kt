package com.example.fatia.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.ImagemConversor
import com.example.fatia.data.dal.AvaliacaoDAO
import com.example.fatia.data.model.Avaliacao

class AvaliacaoViewModel(application: Application) : AndroidViewModel(application) {

    private val avaliacaoDAO = AvaliacaoDAO(application)

    var avaliacoes by mutableStateOf<List<Avaliacao>>(emptyList())

    var pizzaNome by mutableStateOf("")
    var nota by mutableStateOf(5)
    var comentario by mutableStateOf("")
    var foto by mutableStateOf<Bitmap?>(null)

    var mensagem by mutableStateOf("")

    fun listar() {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            avaliacoes = emptyList()
        } else {
            avaliacoes = avaliacaoDAO.listarDoUsuario(usuarioId)
        }
    }

    fun carregar(id: Long) {
        mensagem = ""

        if (id == 0L) {
            pizzaNome = ""
            nota = 5
            comentario = ""
            foto = null
        } else {
            val avaliacao = avaliacaoDAO.buscarPorId(id)
            if (avaliacao != null) {
                pizzaNome = avaliacao.pizzaNome
                nota = avaliacao.nota
                comentario = avaliacao.comentario

                val fotoSalva = avaliacao.foto
                if (fotoSalva != null) {
                    foto = ImagemConversor.paraBitmap(fotoSalva)
                } else {
                    foto = null
                }
            }
        }
    }

    fun receberFoto(imagem: Bitmap?) {
        if (imagem != null) {
            foto = imagem
            mensagem = ""
        } else {
            mensagem = "Foto cancelada"
        }
    }

    fun salvar(id: Long): Boolean {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            mensagem = "Faça login para continuar"
            return false
        }

        val pizzaLimpa = pizzaNome.trim()
        if (pizzaLimpa.isEmpty()) {
            mensagem = "Informe a pizza"
            return false
        }

        val fotoAtual = foto
        var fotoEmBytes: ByteArray? = null
        if (fotoAtual != null) {
            fotoEmBytes = ImagemConversor.paraBlob(fotoAtual)
        }

        val avaliacao = Avaliacao(
            id = id,
            usuarioId = usuarioId,
            pizzaNome = pizzaLimpa,
            nota = nota,
            comentario = comentario.trim(),
            foto = fotoEmBytes
        )

        if (id == 0L) {
            avaliacaoDAO.inserir(avaliacao)
        } else {
            avaliacaoDAO.alterar(avaliacao)
        }

        mensagem = ""
        return true
    }

    fun excluir(id: Long) {
        avaliacaoDAO.excluir(id)
        listar()
    }

    fun fotoDaAvaliacao(avaliacao: Avaliacao): Bitmap? {
        val fotoSalva = avaliacao.foto
        if (fotoSalva == null) {
            return null
        } else {
            return ImagemConversor.paraBitmap(fotoSalva)
        }
    }
}
