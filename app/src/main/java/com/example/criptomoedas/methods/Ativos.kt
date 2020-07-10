package com.example.criptomoedas.methods
import android.os.Parcel
import android.os.Parcelable

class Ativos(var id: Int?, val moeda: String?, val quantidade: Double, val valor: Double, val data: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(moeda)
        parcel.writeDouble(quantidade)
        parcel.writeDouble(valor)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ativos> {
        override fun createFromParcel(parcel: Parcel): Ativos {
            return Ativos(parcel)
        }

        override fun newArray(size: Int): Array<Ativos?> {
            return arrayOfNulls(size)
        }
    }
}