package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Usuario

// DAO DO USUARIO (DAL = camada de acesso a dados)
//
// DAO quer dizer "Data Access Object": e a classe que conversa com o banco.
// Ela recebe o Context porque o FatiaDatabase precisa dele para achar o
// arquivo do banco dentro do celular.
//
// Quem chama o DAO e sempre o ViewModel. A tela nunca fala com o banco.
class UsuarioDAO(private val context: Context) {

    // CRIAR: salva um usuario novo.
    // Devolve o id que o banco gerou, ou -1 se der errado
    // (acontece quando o e-mail ja existe, por causa do UNIQUE).
    fun cadastrar(usuario: Usuario): Long {
        val fatiaDatabase = FatiaDatabase(context)
        // writableDatabase porque vamos ESCREVER no banco.
        val banco = fatiaDatabase.writableDatabase

        // ContentValues e uma lista de "coluna = valor".
        // Nao colocamos o id: quem gera o id e o banco (AUTOINCREMENT).
        val valores = ContentValues()
        valores.put("nome", usuario.nome)
        valores.put("email", usuario.email)
        valores.put("senha", usuario.senha)

        val id = banco.insert("usuarios", null, valores)

        // Sempre fechar o banco no final, para nao ficar aberto sem uso.
        banco.close()
        return id
    }

    // BUSCAR PELO E-MAIL: usado no login e para nao repetir e-mail no cadastro.
    // Devolve o Usuario encontrado ou null quando nao existe ninguem com
    // aquele e-mail (o ? no tipo de retorno permite devolver null).
    fun buscarPorEmail(email: String): Usuario? {
        val fatiaDatabase = FatiaDatabase(context)
        // readableDatabase porque so vamos LER.
        val banco = fatiaDatabase.readableDatabase

        // query monta um SELECT:
        //   "usuarios"        -> tabela
        //   arrayOf(...)      -> colunas que queremos, NESSA ordem
        //   "email = ?"       -> o filtro (WHERE)
        //   arrayOf(email)    -> o valor que entra no lugar do ?
        //   os nulls do final -> groupBy, having e orderBy, que nao usamos
        val cursor = banco.query(
            "usuarios",
            arrayOf("id", "nome", "email", "senha"),
            "email = ?",
            arrayOf(email),
            null,
            null,
            null
        )

        // Cursor e o resultado da consulta. Comeca "antes" da primeira linha.
        var usuario: Usuario? = null

        // moveToFirst vai para a primeira linha e devolve false se nao veio
        // nenhuma linha. Como o e-mail e UNIQUE, no maximo vem uma.
        if (cursor.moveToFirst()) {
            // Os numeros sao a posicao da coluna na lista que pedimos acima:
            // 0 = id, 1 = nome, 2 = email, 3 = senha.
            usuario = Usuario(
                id = cursor.getLong(0),
                nome = cursor.getString(1),
                email = cursor.getString(2),
                senha = cursor.getString(3)
            )
        }

        // Fecha o cursor e o banco no final.
        cursor.close()
        banco.close()
        return usuario
    }
}
