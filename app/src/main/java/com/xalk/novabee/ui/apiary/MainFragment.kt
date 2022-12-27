package com.xalk.novabee.ui.apiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xalk.novabee.R
import com.xalk.novabee.databinding.FragmentMainBinding
import com.xalk.novabee.models.ApiaryResponse
import com.xalk.novabee.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val apiaryViewModel by viewModels<ApiaryViewModel>()

    private lateinit var adapter: ApiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = ApiaryAdapter(::onApiaryClicked, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        apiaryViewModel.getApiaries()
        binding.apiaryList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.apiaryList.adapter = adapter

        binding.addApiary.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_apiaryFormFragment)
        }

        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }

    private fun bindObservers() {
        apiaryViewModel.apiaryLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
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

    private fun onApiaryClicked(apiaryResponse: ApiaryResponse){
        val bundle = Bundle()
        bundle.putString("apiary", Gson().toJson(apiaryResponse))
        findNavController().navigate(R.id.action_mainFragment_to_beehiveFragment, bundle)
        Toast.makeText(requireContext(), apiaryResponse.name, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}