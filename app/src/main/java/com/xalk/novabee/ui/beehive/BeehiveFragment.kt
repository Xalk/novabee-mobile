package com.xalk.novabee.ui.beehive

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
import com.xalk.novabee.R
import com.xalk.novabee.databinding.FragmentBeehiveBinding
import com.xalk.novabee.models.ApiaryResponse
import com.xalk.novabee.models.BeehiveResponse
import com.xalk.novabee.ui.apiary.ApiaryViewModel
import com.xalk.novabee.ui.beehiveDetails.BeehiveDetailsViewModel
import com.xalk.novabee.utils.Constants.TAG
import com.xalk.novabee.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeehiveFragment : Fragment() {

    private var _binding: FragmentBeehiveBinding? = null
    private val binding get() = _binding!!
    private var apiary: ApiaryResponse? = null

    private val beehiveDetailsViewModel by viewModels<BeehiveDetailsViewModel>()
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
        Log.d(TAG, "BEEHIVE FRAGMENT" + apiary!!.toString())
        beehiveDetailsViewModel.getBeehives(apiary!!._id)



        binding.beehiveList.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.beehiveList.adapter = adapter
        binding.addBeehive.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("apiaryId", Gson().toJson(apiary))
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
        val builder = AlertDialog.Builder(view.context,  R.style.AlertDialogTheme)

        // set title
        builder.setTitle(getString(R.string.txt_alert_title))

        //set content area
        builder.setMessage(getString(R.string.txt_alert_apiary_delete))

        //set positive button
        builder.setPositiveButton(
            getString(R.string.txt_alert_title)
        ) { dialog, id ->
            apiary.let {
                apiaryViewModel.deleteApiary(it!!._id)
            }
            findNavController().popBackStack()
            Toast.makeText(requireContext(), getString(R.string.txt_alert_delete_ok), Toast.LENGTH_SHORT).show()
        }

        // set negative button
        builder.setNegativeButton(
            getString(R.string.txt_alert_cancel)
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
        beehiveDetailsViewModel.beehivesLiveData.observe(viewLifecycleOwner, Observer {
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
        Toast.makeText(requireContext(), beehiveResponse.name, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}