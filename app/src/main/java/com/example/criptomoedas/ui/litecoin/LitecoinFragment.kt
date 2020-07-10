package com.ap8.criptomoedas.ui.litecoin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap8.criptomoedas.ui.AtivosOp
import com.example.criptomoedas.AtivosAdapter
import com.example.criptomoedas.R
import com.example.criptomoedas.apiCripto.RetrofitConfig
import com.example.criptomoedas.methods.Ativos
import com.example.criptomoedas.methods.AtivosMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_bcash.*
import kotlinx.android.synthetic.main.fragment_ethereum.*
import kotlinx.android.synthetic.main.fragment_litecoin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class LitecoinFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_litecoin, container, false)
        val btn: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_ltc)

        btn.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, AtivosOp::class.java)
            it.putExtra("moeda", "LTC")
            activity?.startActivity(it)
        })

        initRecyclerView(recycle)
        return root
    }
    override fun onStart() {
        super.onStart()
        if(context?.let { RetrofitConfig().hasConnection(it) }!!) {
            progressltc.visibility = View.VISIBLE
            val call: Call<MoedaAPI> = RetrofitConfig().getMoedaService().getMoeda("ltc")
            call.enqueue(object: Callback<MoedaAPI> {
                override fun onFailure(call: Call<MoedaAPI>, t: Throwable) {
                    Log.e("onFailure error", t.message)
                    progressltc.visibility = View.GONE
                }
                override fun onResponse(call: Call<MoedaAPI>, response: Response<MoedaAPI>) {
                    progressltc.visibility = View.VISIBLE
                    ltcAPI = response.body()?.ticker
                    updateAdapter()
                    somar()
                    progressltc.visibility = View.GONE
                }
            })
        } else {
            updateAdapter()
            somar()
        }
    }

    private fun updateAdapter() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.getBymoeda("LTC")
        }
        if(listaAtivos.isEmpty()) {
            recycle_ltc.visibility = View.GONE
            recycle_ltc.visibility = View.VISIBLE
            ltc_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
            recycle_ltc.adapter = AtivosAdapter(listaAtivos, null)
        } else {
            recycle_ltc.visibility = View.VISIBLE
            ltc_msg.text = ""
            if(ltcAPI == null) {
                recycle_ltc.adapter = AtivosAdapter(listaAtivos, null)
            } else {
                recycle_ltc.adapter = AtivosAdapter(listaAtivos.reversed(), ltcAPI?.price)
            }
            recycle_ltc.adapter?.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(recycle: RecyclerView) {
        val adapter_ = AtivosAdapter(listaAtivos.reversed(), ltcAPI?.price)
        val layout = LinearLayoutManager(activity)
        recycle.setHasFixedSize(true)
        recycle.adapter = adapter_
        recycle.layoutManager = layout
    }

    fun somar() {
        total = 0.0
        for(position in 0 .. listaAtivos.size - 1) {
            val ativo = listaAtivos[position]
            total += ativo.valor
        }
        val valor = DecimalFormat("#,##0.00").format(total)
        total_ltc.text = "R$ ${valor}"
    }
}


