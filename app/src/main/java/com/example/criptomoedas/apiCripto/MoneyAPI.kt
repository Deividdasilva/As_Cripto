package com.example.criptomoedas.apiCripto

import com.google.gson.annotations.SerializedName

class MoneyAPI (
    @SerializedName("ticker")
    var ticker: MoneyAPI,

    @SerializedName("high")
    var high: Double = 0.0, //Maior preço unitário de negociação das últimas 24 horas.
    @SerializedName("low")
    var low: Double = 0.0, //Menor preço unitário de negociação das últimas 24 horas.
    @SerializedName("vol")
    var vol: Double = 0.0, //Quantidade negociada nas últimas 24 horas.
    @SerializedName("last")
    var price: Double = 0.0, //Preço unitário da última negociação.
    @SerializedName("buy")
    var buy: Double = 0.0, //Maior preço de oferta de compra das últimas 24 horas.
    @SerializedName("sell")
    var sell: Double = 0.0, //Menor preço de oferta de venda das últimas 24 horas.
    @SerializedName("date")
    var date: Double = 0.0 //Data e hora da informação em Era Unix
)