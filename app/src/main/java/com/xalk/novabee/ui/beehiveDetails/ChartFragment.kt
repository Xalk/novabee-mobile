package com.xalk.novabee.ui.beehiveDetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.xalk.novabee.databinding.FragmentChartBinding
import com.xalk.novabee.models.BeehiveResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChartFragment() : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private var beehive: BeehiveResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChartBinding.inflate(inflater, container, false)

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBeehiveData()
        setInitialChart()


    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setInitialChart() {
        var html = ""
        html += createChartIframe("634a8616-d3ad-43dd-8039-cf034a084791")
        html += createChartIframe("639b236d-1a46-4175-875b-b67e6b03230c")

        binding.webView.loadData(html, "text/html; video/avc", "utf-8")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON)
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON_DEMAND)
        binding.webView.settings.safeBrowsingEnabled = false
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.javaScriptEnabled = true
    }

    private fun createChartIframe(chartId: String): String {
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())

        val deviceId = beehive!!.deviceID

        val iframe = "<iframe width=\"100%\" height=\"500px\" src=\"https://charts.mongodb.com/charts-project-0-vcant/embed/charts?" +
                "id=$chartId" +
                "&filter={deviceID: '$deviceId',  createdAt : {'\$gte': new Date('$currentDate')}}" +
                "&theme=light&autoRefresh=true&maxDataAge=10&showTitleAndDesc=false&scalingWidth=scale&scalingHeight=fixed\" title=\"Beehive chart\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>\n"

        return iframe
    }

    private fun setBeehiveData() {
        val jsonBeehive = arguments?.getString("chart")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}