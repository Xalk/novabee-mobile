package com.example.novabee.ui.beehiveDetails.queen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.novabee.R
import com.example.novabee.databinding.FragmentBeehiveBinding
import com.example.novabee.databinding.FragmentQueenBinding
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.ui.beehive.BeehiveViewModel
import com.example.novabee.utils.Constants
import com.example.novabee.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QueenFragment() : Fragment() {

    private var _binding: FragmentQueenBinding? = null
    private val binding get() = _binding!!

    private var beehive: BeehiveResponse? = null
    private val queenViewModel by viewModels<QueenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentQueenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBeehiveData()
        bindObservers()
        queenViewModel.getQueen(beehive!!.apiary, beehive!!._id)

        val testData = arguments?.getString("queen")
        if (testData != null) {
            Log.d(Constants.TAG, testData + "QUEEN")
        }
    }


    private fun setBeehiveData() {
        val jsonBeehive = arguments?.getString("queen")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
        }
    }

    private fun bindObservers() {
        queenViewModel.queenLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.queenName.text = it.data!!.name
                    binding.description.text = it.data.description
                    binding.introduced.text = it.data.introducedFrom
                    binding.isOut.text = it.data.isOut.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}