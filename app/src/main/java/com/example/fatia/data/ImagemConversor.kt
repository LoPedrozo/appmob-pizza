package com.example.fatia.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object ImagemConversor {

    fun paraBlob(imagem: Bitmap): ByteArray {
        val saida = ByteArrayOutputStream()
        imagem.compress(Bitmap.CompressFormat.PNG, 100, saida)
        return saida.toByteArray()
    }

    fun paraBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
