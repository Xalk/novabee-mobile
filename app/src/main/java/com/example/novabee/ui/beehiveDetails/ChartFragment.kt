package com.example.novabee.ui.beehiveDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.novabee.databinding.FragmentChartBinding
import com.example.novabee.utils.Constants


class ChartFragment() : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    private var html = "<iframe  style=\"background: #F1F5F4;border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);width: 300px;height: 300px;\"  src=\"https://charts.mongodb.com/charts-project-0-vcant/embed/dashboards?id=634a7e76-d3ad-4a73-8d6e-cf034a045a01&theme=light&autoRefresh=true&maxDataAge=3600&showTitleAndDesc=false&scalingWidth=fixed&scalingHeight=fixed\"></iframe>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentChartBinding.inflate(inflater, container, false)


        binding.webView.loadUrl("https://charts.mongodb.com/charts-project-0-vcant/embed/dashboards?id=634a7e76-d3ad-4a73-8d6e-cf034a045a01&theme=light&autoRefresh=true&maxDataAge=3600&showTitleAndDesc=false&scalingWidth=fixed&scalingHeight=fixed")
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON)
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON_DEMAND)
        binding.webView.settings.javaScriptEnabled = true

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d(Constants.TAG, testForChart)

        val testData = arguments?.getString("chart")
        if (testData != null) {
            Log.d(Constants.TAG, testData + "CHART")
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}