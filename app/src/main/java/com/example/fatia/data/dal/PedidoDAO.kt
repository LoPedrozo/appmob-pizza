package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Pedido

// DAO DO PEDIDO (DAL = camada de acesso a dados)
//
// Segundo CRUD completo do app, em cima da tabela "pedidos":
//   Criar   -> inserir
//   Listar  -> listarDoUsuario e buscarPorId
//   Alterar -> alterar
//   Deletar -> excluir
//
// Detalhe importante: borda e Boolean no Kotlin, mas no banco e 0 ou 1,
// porque o SQLite nao tem o tipo boolean. A conversao acontece aqui dentro.
class PedidoDAO(private val context: Context) {

    // CRIAR: salva o pedido e devolve o id que o banco gerou.
    fun inserir(pedido: Pedido): Long {
        val fatiaDatabase = FatiaDatabase(context)
        // writableDatabase porque vamos ESCREVER.
        val banco = fatiaDatabase.writableDatabase

        // Nao colocamos o id: quem gera o id e o banco (AUTOINCREMENT).
        val valores = ContentValues()
        valores.put("usuario_id", pedido.usuarioId)
        valores.put("pizza_nome", pedido.pizzaNome)
        valores.put("preco_base", pedido.precoBase)
        valores.put("tamanho", pedido.tamanho)
        // Aqui o Boolean vira numero: true = 1, false = 0.
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

    // LISTAR: so os pedidos do usuario logado, do mais novo para o mais velho.
    fun listarDoUsuario(usuarioId: Long): List<Pedido> {
        val fatiaDatabase = FatiaDatabase(context)
        // readableDatabase porque so vamos LER.
        val banco = fatiaDatabase.readableDatabase

        // "usuario_id = ?" e o filtro: sem ele apareceriam os pedidos de
        // todos os usuarios. "id DESC" ordena do maior id para o menor,
        // e o maior id e sempre o pedido mais novo.
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

        // moveToNext anda uma linha por vez e devolve false quando acabam.
        while (cursor.moveToNext()) {
            pedidos.add(lerPedido(cursor))
        }

        cursor.close()
        banco.close()
        return pedidos
    }

    // BUSCAR UM: usada na confirmacao e na tela de editar o pedido.
    // Devolve null quando nao existe pedido com aquele id.
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

        // Como o id e a chave primaria, no maximo vem uma linha.
        if (cursor.moveToFirst()) {
            pedido = lerPedido(cursor)
        }

        cursor.close()
        banco.close()
        return pedido
    }

    // ALTERAR: grava os novos dados do pedido que ja existe.
    // Devolve quantas linhas foram alteradas (1 quando deu certo).
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

        // "id = ?" e o WHERE do UPDATE. Sem ele o banco alteraria TODOS
        // os pedidos de uma vez.
        val linhas = banco.update(
            "pedidos",
            valores,
            "id = ?",
            arrayOf(pedido.id.toString())
        )

        banco.close()
        return linhas
    }

    // DELETAR: apaga o pedido daquele id ("cancelar pedido" na tela).
    // Devolve quantas linhas foram apagadas (1 quando deu certo).
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

    // Monta um Pedido com os dados da linha em que o cursor esta agora.
    // Foi separado numa funcao porque as tres consultas acima leem as
    // mesmas colunas, na mesma ordem, e assim nao repetimos o codigo.
    //
    // private = so o PedidoDAO usa esta funcao.
    // Os numeros sao a posicao da coluna na lista que pedimos no query:
    // 0 = id, 1 = usuario_id, 2 = pizza_nome, 3 = preco_base,
    // 4 = tamanho, 5 = borda, 6 = quantidade, 7 = total.
    private fun lerPedido(cursor: Cursor): Pedido {
        return Pedido(
            id = cursor.getLong(0),
            usuarioId = cursor.getLong(1),
            pizzaNome = cursor.getString(2),
            precoBase = cursor.getInt(3),
            tamanho = cursor.getString(4),
            // Aqui o numero volta a ser Boolean: 1 = true, 0 = false.
            borda = cursor.getInt(5) == 1,
            quantidade = cursor.getInt(6),
            total = cursor.getInt(7)
        )
    }
}
