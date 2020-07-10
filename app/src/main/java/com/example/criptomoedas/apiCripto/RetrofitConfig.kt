package com.example.criptomoedas.apiCripto

import com.example.criptomoedas.api.MoneyService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.mercadobitcoin.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMoneyService(): MoneyService {
        return retrofit.create(MoneyService::class.java)
    }

    fun hasConnection(it: Context): Boolean? {

    }
}