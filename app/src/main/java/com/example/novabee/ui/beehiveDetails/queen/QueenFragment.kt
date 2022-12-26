package com.example.novabee.ui.beehiveDetails.queen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.novabee.R
import com.example.novabee.databinding.FragmentBeehiveBinding
import com.example.novabee.databinding.FragmentQueenBinding
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.models.QueenResponse
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
    private var queen: QueenResponse? = null


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



        binding.edit.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("qEdit", Gson().toJson(queen))
            bundle.putString("qBeehive", Gson().toJson(beehive))
            findNavController().navigate(R.id.action_beehiveDetails_to_queenFormFragment, bundle)
        }

        binding.add.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("qBeehive", Gson().toJson(beehive))
            findNavController().navigate(R.id.action_beehiveDetails_to_queenFormFragment, bundle)
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
            binding.edit.visibility = VISIBLE
//            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    queen = it!!.data
                    Log.d(Constants.TAG, it.data!!.toString()+ "QUEEN" )
                    binding.add.visibility = INVISIBLE
                    binding.queenName.text = it.data.name
                    binding.description.text = it.data.description
                    binding.introduced.text = it.data.introducedFrom

                    if(it.data.isOut){
                        binding.isOut.text = getString(R.string.txt_isOut_queen_yes)
                    }else{
                        binding.isOut.text = getString(R.string.txt_isOut_queen_no)
                    }

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    binding.queenName.text = "-"
                    binding.description.text = "-"
                    binding.introduced.text = "-"
                    binding.isOut.text = "-"
                    binding.edit.visibility = INVISIBLE
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