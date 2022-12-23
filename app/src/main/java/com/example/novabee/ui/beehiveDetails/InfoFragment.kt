package com.example.novabee.ui.beehiveDetails

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.novabee.R
import com.example.novabee.databinding.FragmentChartBinding
import com.example.novabee.databinding.FragmentInfoBinding
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.utils.Constants.TAG
import com.example.novabee.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate

@AndroidEntryPoint
class InfoFragment() : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private var beehive: BeehiveResponse? = null

    private val beehiveDetailsViewModel by viewModels<BeehiveDetailsViewModel>()

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

        bindObservers()
        beehiveDetailsViewModel.getBeehive(beehive!!.apiary, beehive!!._id)



        binding.editBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("edit", Gson().toJson(beehive))
            findNavController().navigate(R.id.action_beehiveDetails_to_beehiveFormFragment, bundle)
        }
    }

    private fun bindObservers() {
        beehiveDetailsViewModel.beehiveLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
//                    beehive = it.data
                    binding.description.text = it.data!!.description

                    binding.createdAt.text = it.data!!.createdAt

                    if (it.data!!.deviceID != ""){
                        binding.device.text = "connected"
                    }else{
                        binding.device.text = "disconnected"
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
                }

            }
        })
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