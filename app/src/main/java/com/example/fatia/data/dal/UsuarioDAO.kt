package com.example.fatia.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.fatia.data.local.FatiaDatabase
import com.example.fatia.data.model.Usuario

class UsuarioDAO(private val context: Context) {

    fun cadastrar(usuario: Usuario): Long {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.writableDatabase

        val valores = ContentValues()
        valores.put("nome", usuario.nome)
        valores.put("email", usuario.email)
        valores.put("senha", usuario.senha)

        val id = banco.insert("usuarios", null, valores)

        banco.close()
        return id
    }

    fun buscarPorEmail(email: String): Usuario? {
        val fatiaDatabase = FatiaDatabase(context)
        val banco = fatiaDatabase.readableDatabase

        val cursor = banco.query(
            "usuarios",
            arrayOf("id", "nome", "email", "senha"),
            "email = ?",
            arrayOf(email),
            null,
            null,
            null
        )

        var usuario: Usuario? = null

        if (cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getLong(0),
                nome = cursor.getString(1),
                email = cursor.getString(2),
                senha = cursor.getString(3)
            )
        }

        cursor.close()
        banco.close()
        return usuario
    }
}
