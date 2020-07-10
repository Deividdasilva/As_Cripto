package com.ap8.criptomoedas.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomoedas.AtivosAdapter
import com.example.criptomoedas.R
import com.example.criptomoedas.api.MoedaAPI
import com.example.criptomoedas.api.RetrofitConfig
import com.example.criptomoedas.methods.Ativos
import com.example.criptomoedas.methods.AtivosMethods

import kotlinx.android.synthetic.main.fragment_bitcoin.*
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
class HomeFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var listaAtivosBTC = getListaByMoeda("BTC")
    private var listaAtivosETH = getListaByMoeda("ETH")
    private var listaAtivosLTC = getListaByMoeda("LTC")
    private var listaAtivosBCH = getListaByMoeda("BCH")
    private var total: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val chartView: PieChartView = root.findViewById(R.id.chart)

        return root
    }

    override fun onStart() {
        super.onStart()
        listaAtivosBTC = getListaByMoeda("BTC")
        listaAtivosETH = getListaByMoeda("ETH")
        listaAtivosLTC = getListaByMoeda("LTC")
        listaAtivosBCH = getListaByMoeda("BCH")

        getListaDB()
        val total_ = somar(listaAtivos)
        val totalBTC = somar(listaAtivosBTC)
        val totalETH = somar(listaAtivosETH)
        val totalLTC = somar(listaAtivosLTC)
        val totalBCH = somar(listaAtivosBCH)

        pieChart(totalBTC, totalETH, totalLTC, totalBCH)

        total_home.text = "R$ ${formatar(total_)}"
        btc_total.text = "R$ ${formatar(totalBTC)}"
        eth_total.text = "R$ ${formatar(totalETH)}"
        ltc_total.text = "R$ ${formatar(totalLTC)}"
        bch_total.text = "R$ ${formatar(totalBCH)}"
    }

    fun getListaByMoeda(moeda: String): MutableList<Ativos> {
        var lista = mutableListOf<Ativos>()
        val methods = activity?.let { AtivosMethods(it) }
        if (methods != null) {
            lista = methods.getBymoeda(moeda)
        }
        return lista
    }

    fun getListaDB() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.get()
        }
    }

    fun somar(lista: MutableList<Ativos>): Double {
        total = 0.0
        for(position in 0 .. lista.size - 1) {
            val ativo = lista[position]
            total += ativo.valor
        }
        val valor = total
        return valor
    }

    fun formatar(valor: Double): String {
        return DecimalFormat("#,##0.00").format(valor)
    }

    fun pieChart(
        totalBTC: Double,
        totalETH: Double,
        totalLTC: Double,
        totalBCH: Double
    ) {
        if(totalBTC == 0.0 && totalETH == 0.0 && totalLTC == 0.0 && totalBCH == 0.0) {
            chart.visibility = View.GONE
        } else {
            val pieData: MutableList<SliceValue> = ArrayList()
            pieData.add(SliceValue(totalBTC.toFloat(), Color.parseColor("#FFFF8600")))
            pieData.add(SliceValue(totalETH.toFloat(), Color.parseColor("#FF666666")))
            pieData.add(SliceValue(totalLTC.toFloat(), Color.parseColor("#FF345D9D")))
            pieData.add(SliceValue(totalBCH.toFloat(), Color.parseColor("#2ECC71")))
            val pieChartData = PieChartData(pieData)
            pieChartData.setHasCenterCircle(true)
            chart.pieChartData = pieChartData
        }
    }

}
