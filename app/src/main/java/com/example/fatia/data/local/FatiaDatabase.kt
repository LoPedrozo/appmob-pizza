package com.example.fatia.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FatiaDatabase(context: Context) : SQLiteOpenHelper(
    context,
    NOME_BANCO,
    null,
    VERSAO_BANCO
) {

    companion object {
        const val NOME_BANCO = "fatia.db"

        const val VERSAO_BANCO = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL)"
        )

        db.execSQL(
            "CREATE TABLE pizzas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "descricao TEXT NOT NULL, " +
                "preco INTEGER NOT NULL)"
        )

        db.execSQL(
            "CREATE TABLE pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "pizza_nome TEXT NOT NULL, " +
                "preco_base INTEGER NOT NULL, " +
                "tamanho TEXT NOT NULL, " +
                "borda INTEGER NOT NULL, " +
                "quantidade INTEGER NOT NULL, " +
                "total INTEGER NOT NULL)"
        )

        db.execSQL(
            "INSERT INTO usuarios (nome, email, senha) " +
                "VALUES ('Teste', 'teste@fatia.com', '123456')"
        )

        db.execSQL(
            "INSERT INTO pizzas (nome, descricao, preco) " +
                "VALUES ('Calabresa', 'Calabresa, cebola e azeitona', 39)"
        )
        db.execSQL(
            "INSERT INTO pizzas (nome, descricao, preco) " +
                "VALUES ('Mussarela', 'Muito queijo mussarela e orégano', 35)"
        )
        db.execSQL(
            "INSERT INTO pizzas (nome, descricao, preco) " +
                "VALUES ('Portuguesa', 'Presunto, ovo, ervilha e cebola', 42)"
        )
        db.execSQL(
            "INSERT INTO pizzas (nome, descricao, preco) " +
                "VALUES ('Frango com Catupiry', 'Frango desfiado com catupiry', 45)"
        )
        db.execSQL(
            "INSERT INTO pizzas (nome, descricao, preco) " +
                "VALUES ('Quatro Queijos', 'Mussarela, provolone, gorgonzola e parmesão', 47)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pedidos")
        db.execSQL("DROP TABLE IF EXISTS pizzas")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }
}
