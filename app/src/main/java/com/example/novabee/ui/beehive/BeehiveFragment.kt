package com.example.novabee.ui.beehive

import android.app.AlertDialog
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
import com.example.novabee.ui.apiary.ApiaryViewModel
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
    private val apiaryViewModel by viewModels<ApiaryViewModel>()

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
        binding.beehiveList.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.beehiveList.adapter = adapter
        binding.addBeehive.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("apiaryId", apiary!!._id)
            findNavController().navigate(R.id.action_beehiveFragment_to_beehiveFormFragment, bundle)
        }

        binding.textView.text = apiary!!.name

        binding.editBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("apiary", Gson().toJson(apiary))
            findNavController().navigate(R.id.action_beehiveFragment_to_apiaryFormFragment, bundle)
        }

        binding.deleteBtn.setOnClickListener {
            onAlertDialog(view)
        }

    }

    private fun onAlertDialog(view: View) {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(view.context)

        // set title
        builder.setTitle("Delete")

        //set content area
        builder.setMessage("Are you sure you want to delete this apiary with beehives?")

        //set negative button
        builder.setPositiveButton(
            "Delete"
        ) { dialog, id ->
            apiary.let {
                apiaryViewModel.deleteApiary(it!!._id)
            }
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Successfully deleted", Toast.LENGTH_SHORT).show()
        }

        //set positive button
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, id ->
            // User cancelled the dialog
        }

        builder.show()
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
        Toast.makeText(requireContext(), "beehive - ${beehiveResponse.name}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}