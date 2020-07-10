package com.ap8.criptomoedas.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.criptomoedas.R
import com.example.criptomoedas.methods.Ativos
import com.example.criptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.activity_save.*
import kotlinx.android.synthetic.main.activity_visualizar.*
import java.text.DecimalFormat

class AtivosOp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ativo = intent.getParcelableExtra<Ativos>("get/del")
        val moeda = intent.getStringExtra("moeda")
        val valorizacao = intent.getStringExtra("valorizacao")
        if(moeda != null) {
            setContentView(R.layout.activity_save)

            btn_save.setOnClickListener(View.OnClickListener {
                val ativo = Ativos(
                    null,
                    moeda,
                    view_quantidade.text.toString().toDouble(),
                    view_valor.text.toString().toDouble(),
                    view_data.text.toString()
                )
                val dao = AtivosMethods(this)
                dao.insert(ativo)

                finish()
            })
        } else {
            setContentView(R.layout.activity_visualizar)
            val valor = DecimalFormat("#,##0.00").format(ativo?.valor)


            view_quantidade_.text = "R$ ${valor}"
            view_valor_.text = "${ativo?.moeda} ${ativo?.quantidade}"
            view_data_.text = ativo?.data.toString()
            view_valorizacao_.text = valorizacao

            btn_remove.setOnClickListener(View.OnClickListener {
                val dao = AtivosMethods(this)
                ativo.id?.let { it1 -> dao.remove(it1) }

                finish()
            })
        }
    }
}