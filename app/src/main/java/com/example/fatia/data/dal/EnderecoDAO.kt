package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Endereco

class EnderecoDAO(private val context: Context) {

    fun inserir(endereco: Endereco): Long {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", endereco.usuarioId)
        valores.put("apelido", endereco.apelido)
        valores.put("cep", endereco.cep)
        valores.put("rua", endereco.rua)
        valores.put("numero", endereco.numero)
        valores.put("bairro", endereco.bairro)
        valores.put("cidade", endereco.cidade)

        val id = banco.insert("enderecos", null, valores)

        banco.close()
        return id
    }

    fun listarDoUsuario(usuarioId: Long): List<Endereco> {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "enderecos",
            arrayOf(
                "id",
                "usuario_id",
                "apelido",
                "cep",
                "rua",
                "numero",
                "bairro",
                "cidade"
            ),
            "usuario_id = ?",
            arrayOf(usuarioId.toString()),
            null,
            null,
            "apelido"
        )

        val enderecos = mutableListOf<Endereco>()

        while (cursor.moveToNext()) {
            enderecos.add(lerEndereco(cursor))
        }

        cursor.close()
        banco.close()
        return enderecos
    }

    fun buscarPorId(id: Long): Endereco? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "enderecos",
            arrayOf(
                "id",
                "usuario_id",
                "apelido",
                "cep",
                "rua",
                "numero",
                "bairro",
                "cidade"
            ),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var endereco: Endereco? = null

        if (cursor.moveToFirst()) {
            endereco = lerEndereco(cursor)
        }

        cursor.close()
        banco.close()
        return endereco
    }

    fun alterar(endereco: Endereco): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", endereco.usuarioId)
        valores.put("apelido", endereco.apelido)
        valores.put("cep", endereco.cep)
        valores.put("rua", endereco.rua)
        valores.put("numero", endereco.numero)
        valores.put("bairro", endereco.bairro)
        valores.put("cidade", endereco.cidade)

        val linhas = banco.update(
            "enderecos",
            valores,
            "id = ?",
            arrayOf(endereco.id.toString())
        )

        banco.close()
        return linhas
    }

    fun excluir(id: Long): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val linhas = banco.delete(
            "enderecos",
            "id = ?",
            arrayOf(id.toString())
        )

        banco.close()
        return linhas
    }

    private fun lerEndereco(cursor: Cursor): Endereco {
        return Endereco(
            id = cursor.getLong(0),
            usuarioId = cursor.getLong(1),
            apelido = cursor.getString(2),
            cep = cursor.getString(3),
            rua = cursor.getString(4),
            numero = cursor.getString(5),
            bairro = cursor.getString(6),
            cidade = cursor.getString(7)
        )
    }
}
