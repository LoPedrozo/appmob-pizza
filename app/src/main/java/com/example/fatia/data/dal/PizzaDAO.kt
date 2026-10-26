package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Pizza

// DAO DA PIZZA (DAL = camada de acesso a dados)
//
// Esta classe tem o CRUD completo da tabela "pizzas":
//   Criar   -> inserir
//   Listar  -> listar e buscarPorId
//   Alterar -> alterar
//   Deletar -> excluir
//
// Igual ao UsuarioDAO: recebe o Context, abre o banco, faz o trabalho e
// fecha tudo no final. Quem chama o DAO e o PizzaViewModel, nunca a tela.
class PizzaDAO(private val context: Context) {

    // LISTAR: devolve todas as pizzas do cardapio em ordem de nome.
    fun listar(): List<Pizza> {
        val fatiaDatabase = FatiaDatabase(context)
        // readableDatabase porque so vamos LER.
        val banco = fatiaDatabase.readableDatabase

        // O ultimo parametro do query e o orderBy: "nome" = ORDER BY nome.
        val cursor = banco.query(
            "pizzas",
            arrayOf("id", "nome", "descricao", "preco"),
            null,
            null,
            null,
            null,
            "nome"
        )

        // mutableListOf = lista vazia que aceita adicionar itens.
        val pizzas = mutableListOf<Pizza>()

        // moveToNext anda uma linha por vez e devolve false quando acabam
        // as linhas. E assim que percorremos todo o resultado da consulta.
        while (cursor.moveToNext()) {
            // Os numeros sao a posicao da coluna na lista que pedimos acima:
            // 0 = id, 1 = nome, 2 = descricao, 3 = preco.
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

    // BUSCAR UMA: usada para preencher o formulario na hora de editar.
    // Devolve null quando nao existe pizza com aquele id.
    fun buscarPorId(id: Long): Pizza? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        // O id e numero, mas o filtro do query so aceita texto, por isso
        // o toString(). O banco converte de volta na hora de comparar.
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

        // Como o id e a chave primaria, no maximo vem uma linha.
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

    // CRIAR: salva uma pizza nova e devolve o id que o banco gerou.
    fun inserir(pizza: Pizza): Long {
        val fatiaDatabase = FatiaDatabase(context)
        // writableDatabase porque vamos ESCREVER.
        val banco = fatiaDatabase.writableDatabase

        // Nao colocamos o id: quem gera o id e o banco (AUTOINCREMENT).
        val valores = ContentValues()
        valores.put("nome", pizza.nome)
        valores.put("descricao", pizza.descricao)
        valores.put("preco", pizza.preco)

        val id = banco.insert("pizzas", null, valores)

        banco.close()
        return id
    }

    // ALTERAR: grava os novos dados da pizza que ja existe.
    // Devolve quantas linhas foram alteradas (1 quando deu certo).
    fun alterar(pizza: Pizza): Int {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("nome", pizza.nome)
        valores.put("descricao", pizza.descricao)
        valores.put("preco", pizza.preco)

        // "id = ?" + arrayOf(...) e o WHERE do UPDATE. Sem ele o banco
        // alteraria TODAS as pizzas de uma vez.
        val linhas = banco.update(
            "pizzas",
            valores,
            "id = ?",
            arrayOf(pizza.id.toString())
        )

        banco.close()
        return linhas
    }

    // DELETAR: apaga a pizza daquele id.
    // Devolve quantas linhas foram apagadas (1 quando deu certo).
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
