package com.example.criptomoedas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ap8.criptomoedas.ui.AtivosOp
import com.example.criptomoedas.methods.Ativos
import kotlinx.android.synthetic.main.adapter_ativos.view.*
import java.text.DecimalFormat

class AtivosAdapter(private val ativos: List<Ativos>):
    RecyclerView.Adapter<AtivosAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_ativos, parent,false)

        val vHolder = VH(view)

        view.setOnClickListener(View.OnClickListener {
            val valor = vHolder.viewValor.text.toString()
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
            val quantidade = vHolder.viewQuantidade.text.toString()
                .substring(4)

            val ativo = Ativos(
                vHolder.idInvisible,
                vHolder.viewMoeda.text.toString(),
                quantidade.toDouble(),
                valor.toDouble(),
                vHolder.viewData.text.toString()
            )

            val it = Intent(parent.context, AtivosOp::class.java)
            it.putExtra("get/del", ativo)
            parent.context.startActivity(it)
        })

        return vHolder
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ativo = ativos[position]
        val valor = DecimalFormat("#,##0.00").format(ativo.valor)
        holder.idInvisible = ativo.id
        holder.viewMoeda.text = ativo.moeda.toString()
        holder.viewQuantidade.text = "${ativo.moeda}   ${ativo.quantidade}"
        holder.viewValor.text = "R$ ${valor}"
        holder.viewData.text = ativo.data.toString()
    }

    class VH(item: View): RecyclerView.ViewHolder(item) {
        var idInvisible: Int? = null
        var viewMoeda: TextView = item.view_moeda_
        var viewQuantidade: TextView = item.view_quantidade_
        var viewValor: TextView = item.view_valor_
        var viewData: TextView = item.view_data_
    }
}