package com.example.criptomoedas.methods

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.criptomoedas.db.*

class AtivosMethods(context: Context) {
    var db_ = DbHelper(context)

    fun insert(ativo: Ativos): String {
        val db = db_.writableDatabase
        val values = ContentValues()
        values.put(MOEDA, ativo.moeda)
        values.put(QUANTIDADE, ativo.quantidade)
        values.put(VALOR, ativo.valor)
        values.put(DATA, ativo.data)

        val resp = db.insert(TABLE_ATIVOS, null, values)
        val msg = if(resp != -1L) {
            "Inserido com sucesso"
        } else {
            "Erro ao inserir"
        }
        db.close()

        return msg
    }

//    fun update(ativo: Ativos): Boolean {
//        val db = db_.writableDatabase
//        val values = ContentValues()
//        values.put(ID, ativo.id)
//        values.put(MOEDA, ativo.moeda)
//        values.put(QUANTIDADE, ativo.quantidade)
//        values.put(VALOR, ativo.valor)
//        values.put(DATA, ativo.data)
//        db.insertWithOnConflict(TABLE_ATIVOS,null, values, SQLiteDatabase.CONFLICT_REPLACE)
//
//        db.close()
//        return true
//    }

    fun remove(id: Int): Int {
        val db = db_.writableDatabase
        return db.delete(TABLE_ATIVOS, "ID = ?", arrayOf(id.toString()))
    }

    fun get(): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT * FROM ${TABLE_ATIVOS}"
        val cursor = db.rawQuery(sql, null)
        val lista = ArrayList<Ativos>()

        while(cursor.moveToNext()) {
            val ativo = ativoFromCursor(cursor)
            lista.add(ativo)
        }

        cursor.close()
        db.close()

        return lista
    }

    fun getBymoeda(moeda: String): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT * FROM ${TABLE_ATIVOS} WHERE ${MOEDA} LIKE '%$moeda%'"
        val cursor = db.rawQuery(sql ,null)
        val lista = ArrayList<Ativos>()

        while(cursor.moveToNext()) {
            val moeda_ = ativoFromCursor(cursor)
            lista.add(moeda_)
        }

        cursor.close()
        db.close()

        return lista
    }

    private fun ativoFromCursor(cursor: Cursor): Ativos {
        val id = cursor.getInt(cursor.getColumnIndex(ID))
        val moeda = cursor.getString(cursor.getColumnIndex(MOEDA))
        val quantidade = cursor.getDouble(cursor.getColumnIndex(QUANTIDADE))
        val valor = cursor.getDouble(cursor.getColumnIndex(VALOR))
        val data = cursor.getString(cursor.getColumnIndex(DATA))
        return Ativos(id, moeda, quantidade, valor, data)
    }
}