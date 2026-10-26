package com.example.fatia.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// O BANCO DE DADOS DO APP
//
// SQLiteOpenHelper e a classe do Android que cuida do arquivo do banco.
// Nos so dizemos o nome do arquivo e a versao; ele decide quando chamar
// onCreate (primeira vez que o app abre) e onUpgrade (quando a versao sobe).
//
// Depois de criado, quem quiser mexer no banco pede:
//   writableDatabase -> para INSERT, UPDATE e DELETE (escrever)
//   readableDatabase -> para SELECT (apenas ler)
//
// null no terceiro parametro = usamos o leitor de linhas padrao do Android.
class FatiaDatabase(context: Context) : SQLiteOpenHelper(
    context,
    NOME_BANCO,
    null,
    VERSAO_BANCO
) {

    // companion object = valores que pertencem a classe, nao a cada objeto
    // criado (o mesmo papel do static do Java).
    companion object {
        // Nome do arquivo do banco dentro do celular.
        const val NOME_BANCO = "fatia.db"

        // Versao do banco. Se um dia mudarmos as tabelas, subimos este
        // numero e o Android chama o onUpgrade.
        const val VERSAO_BANCO = 1
    }

    // Chamado UMA vez, quando o banco ainda nao existe no celular.
    // E aqui que as tabelas nascem.
    override fun onCreate(db: SQLiteDatabase) {

        // execSQL executa um comando SQL direto no banco.
        // INTEGER PRIMARY KEY AUTOINCREMENT = o proprio banco gera o id.
        // NOT NULL = o campo nao pode ficar vazio.
        // UNIQUE no e-mail = nao deixa dois usuarios com o mesmo e-mail.
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

        // borda e INTEGER porque guardamos 0 (sem borda) ou 1 (com borda).
        // usuario_id guarda de quem e o pedido.
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

        // DADOS INICIAIS
        // Sem isto o app abriria com o cardapio vazio e sem nenhum usuario
        // para testar o login. Sao as mesmas 5 pizzas e o mesmo usuario de
        // teste que antes estavam escritos no codigo das telas.
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

    // Chamado quando o VERSAO_BANCO sobe e o celular ainda tem a versao velha.
    //
    // Nossa escolha foi a mais simples: joga as tabelas antigas fora e cria
    // tudo de novo chamando o onCreate. E um trabalho de faculdade, entao nao
    // precisamos migrar (aproveitar) os dados antigos.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pedidos")
        db.execSQL("DROP TABLE IF EXISTS pizzas")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }
}
