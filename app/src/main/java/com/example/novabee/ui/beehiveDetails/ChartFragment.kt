package com.example.novabee.ui.beehiveDetails

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.novabee.databinding.FragmentChartBinding
import com.example.novabee.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment() : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentChartBinding.inflate(inflater, container, false)

        val html =
"<iframe width=\"100%\" height=\"1000px\" src=\"https://charts.mongodb.com/charts-project-0-vcant/embed/dashboards?id=634a7e76-d3ad-4a73-8d6e-cf034a045a01&theme=light&autoRefresh=true&maxDataAge=10&showTitleAndDesc=false&scalingWidth=scale&scalingHeight=fixed\" title=\"Beehive chart\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>\n"
        binding.webView.loadData(html, "text/html; video/avc", "utf-8")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON)
        binding.webView.settings.setPluginState(WebSettings.PluginState.ON_DEMAND)
        binding.webView.settings.safeBrowsingEnabled = false
        binding.webView.settings.domStorageEnabled = true
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