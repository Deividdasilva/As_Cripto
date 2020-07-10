package com.example.criptomoedas.api

import com.example.criptomoedas.apiCripto.MoneyAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoneyService {
    @GET("api/{moeda}/ticker")
    fun getMoney(@Path("moeda") moeda: String): Call<MoneyAPI>
}