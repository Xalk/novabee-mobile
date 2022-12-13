package com.example.novabee.ui.beehive

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.novabee.R
import com.example.novabee.databinding.FragmentBeehiveBinding
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.utils.Constants.TAG
import com.example.novabee.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeehiveFragment : Fragment() {

    private var _binding: FragmentBeehiveBinding? = null
    private val binding get() = _binding!!
    private var apiary: ApiaryResponse? = null

    private val beehiveViewModel by viewModels<BeehiveViewModel>()

    private lateinit var adapter: BeehiveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBeehiveBinding.inflate(inflater, container, false)
        adapter = BeehiveAdapter(::onBeehiveClicked)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()
        bindObservers()
        beehiveViewModel.getBeehives(apiary!!._id)
        binding.beehiveList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.beehiveList.adapter = adapter
    }

    private fun setInitialData() {
        val jsonApiary = arguments?.getString("apiary")
        if (jsonApiary != null) {
            apiary = Gson().fromJson(jsonApiary, ApiaryResponse::class.java)
            Log.d(TAG, apiary.toString())
        }
    }

    private fun bindObservers() {
        beehiveViewModel.beehiveLiveData.observe(viewLifecycleOwner, Observer {
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

    private fun onBeehiveClicked(beehiveResponse: BeehiveResponse) {
        val bundle = Bundle()
        bundle.putString("beehive", Gson().toJson(beehiveResponse))
        findNavController().navigate(R.id.action_beehiveFragment_to_beehiveDetails, bundle)
        Toast.makeText(requireContext(), "beehive - ${beehiveResponse.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}