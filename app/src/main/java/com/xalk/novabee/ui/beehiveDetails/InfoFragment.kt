package com.xalk.novabee.ui.beehiveDetails

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.xalk.novabee.R
import com.xalk.novabee.databinding.FragmentChartBinding
import com.xalk.novabee.databinding.FragmentInfoBinding
import com.xalk.novabee.models.ApiaryResponse
import com.xalk.novabee.models.BeehiveResponse
import com.xalk.novabee.utils.Constants.TAG
import com.xalk.novabee.utils.NetworkResult
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
        beehiveDetailsViewModel.oneBeehiveLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            binding.relativeLayout.isVisible = false

            when (it) {
                is NetworkResult.Success -> {
                    binding.relativeLayout.isVisible = true
                    beehive = it.data
                    binding.description.text = it.data!!.description

                    binding.createdAt.text = it.data!!.createdAt.substring(0, 10)

                    if (it.data!!.deviceID != ""){
                        binding.device.text = getString(R.string.txt_device_state_details_on)
                    }else{
                        binding.device.text = getString(R.string.txt_device_state_details_off)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
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