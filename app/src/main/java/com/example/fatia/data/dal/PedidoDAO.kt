package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Pedido

class PedidoDAO(private val context: Context) {

    fun inserir(pedido: Pedido): Long {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", pedido.usuarioId)
        valores.put("pizza_nome", pedido.pizzaNome)
        valores.put("preco_base", pedido.precoBase)
        valores.put("tamanho", pedido.tamanho)
        if (pedido.borda) {
            valores.put("borda", 1)
        } else {
            valores.put("borda", 0)
        }
        valores.put("quantidade", pedido.quantidade)
        valores.put("total", pedido.total)

        val id = banco.insert("pedidos", null, valores)

        banco.close()
        return id
    }

    fun listarDoUsuario(usuarioId: Long): List<Pedido> {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "pedidos",
            arrayOf(
                "id",
                "usuario_id",
                "pizza_nome",
                "preco_base",
                "tamanho",
                "borda",
                "quantidade",
                "total"
            ),
            "usuario_id = ?",
            arrayOf(usuarioId.toString()),
            null,
            null,
            "id DESC"
        )

        val pedidos = mutableListOf<Pedido>()

        while (cursor.moveToNext()) {
            pedidos.add(lerPedido(cursor))
        }

        cursor.close()
        banco.close()
        return pedidos
    }

    fun buscarPorId(id: Long): Pedido? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "pedidos",
            arrayOf(
                "id",
                "usuario_id",
                "pizza_nome",
                "preco_base",
                "tamanho",
                "borda",
                "quantidade",
                "total"
            ),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var pedido: Pedido? = null

        if (cursor.moveToFirst()) {
            pedido = lerPedido(cursor)
        }

        cursor.close()
        banco.close()
        return pedido
    }

    fun alterar(pedido: Pedido): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("usuario_id", pedido.usuarioId)
        valores.put("pizza_nome", pedido.pizzaNome)
        valores.put("preco_base", pedido.precoBase)
        valores.put("tamanho", pedido.tamanho)
        if (pedido.borda) {
            valores.put("borda", 1)
        } else {
            valores.put("borda", 0)
        }
        valores.put("quantidade", pedido.quantidade)
        valores.put("total", pedido.total)

        val linhas = banco.update(
            "pedidos",
            valores,
            "id = ?",
            arrayOf(pedido.id.toString())
        )

        banco.close()
        return linhas
    }

    fun excluir(id: Long): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val linhas = banco.delete(
            "pedidos",
            "id = ?",
            arrayOf(id.toString())
        )

        banco.close()
        return linhas
    }

    private fun lerPedido(cursor: Cursor): Pedido {
        return Pedido(
            id = cursor.getLong(0),
            usuarioId = cursor.getLong(1),
            pizzaNome = cursor.getString(2),
            precoBase = cursor.getInt(3),
            tamanho = cursor.getString(4),
            borda = cursor.getInt(5) == 1,
            quantidade = cursor.getInt(6),
            total = cursor.getInt(7)
        )
    }
}
