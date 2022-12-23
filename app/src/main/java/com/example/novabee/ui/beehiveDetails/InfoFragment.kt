package com.example.novabee.ui.beehiveDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.novabee.R
import com.example.novabee.databinding.FragmentChartBinding
import com.example.novabee.databinding.FragmentInfoBinding
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.utils.Constants.TAG
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment() : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private var beehive: BeehiveResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBeehiveData()

        binding.description.text = beehive!!.description
        binding.createdAt.text = beehive!!.createdAt
        if (beehive!!.deviceID != ""){
            binding.device.text = "connected"
        }else{
            binding.device.text = "disconnected"
        }
    }

    private fun setBeehiveData() {
        val jsonBeehive = arguments?.getString("info")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}