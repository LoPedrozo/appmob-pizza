package com.example.fatia.data.api

import com.example.fatia.data.model.RespostaCep
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun buscar(@Path("cep") cep: String): RespostaCep
}

object ViaCepApi {
    val service: ViaCepService = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ViaCepService::class.java)
}
