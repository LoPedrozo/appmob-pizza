package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Pizza

class PizzaDAO(private val context: Context) {

    fun listar(): List<Pizza> {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "pizzas",
            arrayOf("id", "nome", "descricao", "preco"),
            null,
            null,
            null,
            null,
            "nome"
        )

        val pizzas = mutableListOf<Pizza>()

        while (cursor.moveToNext()) {
            val pizza = Pizza(
                id = cursor.getLong(0),
                nome = cursor.getString(1),
                descricao = cursor.getString(2),
                preco = cursor.getInt(3)
            )
            pizzas.add(pizza)
        }

        cursor.close()
        banco.close()
        return pizzas
    }

    fun buscarPorId(id: Long): Pizza? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "pizzas",
            arrayOf("id", "nome", "descricao", "preco"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var pizza: Pizza? = null

        if (cursor.moveToFirst()) {
            pizza = Pizza(
                id = cursor.getLong(0),
                nome = cursor.getString(1),
                descricao = cursor.getString(2),
                preco = cursor.getInt(3)
            )
        }

        cursor.close()
        banco.close()
        return pizza
    }

    fun inserir(pizza: Pizza): Long {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("nome", pizza.nome)
        valores.put("descricao", pizza.descricao)
        valores.put("preco", pizza.preco)

        val id = banco.insert("pizzas", null, valores)

        banco.close()
        return id
    }

    fun alterar(pizza: Pizza): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("nome", pizza.nome)
        valores.put("descricao", pizza.descricao)
        valores.put("preco", pizza.preco)

        val linhas = banco.update(
            "pizzas",
            valores,
            "id = ?",
            arrayOf(pizza.id.toString())
        )

        banco.close()
        return linhas
    }

    fun excluir(id: Long): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val linhas = banco.delete(
            "pizzas",
            "id = ?",
            arrayOf(id.toString())
        )

        banco.close()
        return linhas
    }
}
