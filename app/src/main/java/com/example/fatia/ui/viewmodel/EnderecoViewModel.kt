package com.example.fatia.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatia.auth.SessaoUsuario
import com.example.fatia.data.api.ViaCepApi
import com.example.fatia.data.dal.EnderecoDAO
import com.example.fatia.data.model.Endereco
import kotlinx.coroutines.launch

class EnderecoViewModel(application: Application) : AndroidViewModel(application) {

    private val enderecoDAO = EnderecoDAO(application)

    var enderecos by mutableStateOf<List<Endereco>>(emptyList())

    var apelido by mutableStateOf("")
    var cep by mutableStateOf("")
    var rua by mutableStateOf("")
    var numero by mutableStateOf("")
    var bairro by mutableStateOf("")
    var cidade by mutableStateOf("")

    var mensagem by mutableStateOf("")

    var buscandoCep by mutableStateOf(false)

    fun listar() {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            enderecos = emptyList()
        } else {
            enderecos = enderecoDAO.listarDoUsuario(usuarioId)
        }
    }

    fun carregar(id: Long) {
        mensagem = ""

        if (id == 0L) {
            apelido = ""
            cep = ""
            rua = ""
            numero = ""
            bairro = ""
            cidade = ""
        } else {
            val endereco = enderecoDAO.buscarPorId(id)
            if (endereco != null) {
                apelido = endereco.apelido
                cep = endereco.cep
                rua = endereco.rua
                numero = endereco.numero
                bairro = endereco.bairro
                cidade = endereco.cidade
            }
        }
    }

    fun buscarCep() {
        val cepLimpo = cep.trim().replace("-", "")

        if (cepLimpo.length != 8 || cepLimpo.toIntOrNull() == null) {
            mensagem = "Informe um CEP com 8 números"
            return
        }

        mensagem = ""
        buscandoCep = true

        viewModelScope.launch {
            try {
                val resposta = ViaCepApi.service.buscar(cepLimpo)

                if (resposta.cep == null) {
                    mensagem = "CEP não encontrado"
                } else {
                    rua = resposta.logradouro ?: ""
                    bairro = resposta.bairro ?: ""
                    cidade = resposta.localidade ?: ""
                }
            } catch (erro: Exception) {
                mensagem = "Não foi possível consultar o CEP"
            } finally {
                buscandoCep = false
            }
        }
    }

    fun salvar(id: Long): Boolean {
        val usuarioId = SessaoUsuario.usuarioId
        if (usuarioId == null) {
            mensagem = "Faça login para continuar"
            return false
        }

        val apelidoLimpo = apelido.trim()
        val cepLimpo = cep.trim()
        val ruaLimpa = rua.trim()
        val numeroLimpo = numero.trim()
        val bairroLimpo = bairro.trim()
        val cidadeLimpa = cidade.trim()

        if (apelidoLimpo.isEmpty() ||
            cepLimpo.isEmpty() ||
            ruaLimpa.isEmpty() ||
            numeroLimpo.isEmpty() ||
            bairroLimpo.isEmpty() ||
            cidadeLimpa.isEmpty()
        ) {
            mensagem = "Preencha todos os campos"
            return false
        }

        val endereco = Endereco(
            id = id,
            usuarioId = usuarioId,
            apelido = apelidoLimpo,
            cep = cepLimpo,
            rua = ruaLimpa,
            numero = numeroLimpo,
            bairro = bairroLimpo,
            cidade = cidadeLimpa
        )

        if (id == 0L) {
            enderecoDAO.inserir(endereco)
        } else {
            enderecoDAO.alterar(endereco)
        }

        mensagem = ""
        return true
    }

    fun excluir(id: Long) {
        enderecoDAO.excluir(id)
        listar()
    }
}
