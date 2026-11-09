package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Avaliacao

class AvaliacaoDAO(private val context: Context) {

    fun inserir(avaliacao: Avaliacao): Long {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", avaliacao.usuarioId)
        valores.put("pizza_nome", avaliacao.pizzaNome)
        valores.put("nota", avaliacao.nota)
        valores.put("comentario", avaliacao.comentario)
        if (avaliacao.foto != null) {
            valores.put("foto", avaliacao.foto)
        } else {
            valores.putNull("foto")
        }

        val id = banco.insert("avaliacoes", null, valores)

        banco.close()
        return id
    }

    fun listarDoUsuario(usuarioId: Long): List<Avaliacao> {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "avaliacoes",
            arrayOf(
                "id",
                "usuario_id",
                "pizza_nome",
                "nota",
                "comentario",
                "foto"
            ),
            "usuario_id = ?",
            arrayOf(usuarioId.toString()),
            null,
            null,
            "id DESC"
        )

        val avaliacoes = mutableListOf<Avaliacao>()

        while (cursor.moveToNext()) {
            avaliacoes.add(lerAvaliacao(cursor))
        }

        cursor.close()
        banco.close()
        return avaliacoes
    }

    fun buscarPorId(id: Long): Avaliacao? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "avaliacoes",
            arrayOf(
                "id",
                "usuario_id",
                "pizza_nome",
                "nota",
                "comentario",
                "foto"
            ),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var avaliacao: Avaliacao? = null

        if (cursor.moveToFirst()) {
            avaliacao = lerAvaliacao(cursor)
        }

        cursor.close()
        banco.close()
        return avaliacao
    }

    fun alterar(avaliacao: Avaliacao): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", avaliacao.usuarioId)
        valores.put("pizza_nome", avaliacao.pizzaNome)
        valores.put("nota", avaliacao.nota)
        valores.put("comentario", avaliacao.comentario)
        if (avaliacao.foto != null) {
            valores.put("foto", avaliacao.foto)
        } else {
            valores.putNull("foto")
        }

        val linhas = banco.update(
            "avaliacoes",
            valores,
            "id = ?",
            arrayOf(avaliacao.id.toString())
        )

        banco.close()
        return linhas
    }

    fun excluir(id: Long): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val linhas = banco.delete(
            "avaliacoes",
            "id = ?",
            arrayOf(id.toString())
        )

        banco.close()
        return linhas
    }

    private fun lerAvaliacao(cursor: Cursor): Avaliacao {
        var foto: ByteArray? = null
        if (!cursor.isNull(5)) {
            foto = cursor.getBlob(5)
        }

        return Avaliacao(
            id = cursor.getLong(0),
            usuarioId = cursor.getLong(1),
            pizzaNome = cursor.getString(2),
            nota = cursor.getInt(3),
            comentario = cursor.getString(4),
            foto = foto
        )
    }
}
