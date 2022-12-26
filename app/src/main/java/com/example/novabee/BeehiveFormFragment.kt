package com.example.novabee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.novabee.databinding.FragmentBeehiveFormBinding
import com.example.novabee.databinding.FragmentInfoBinding
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.models.BeehiveRequest
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.ui.beehive.BeehiveViewModel
import com.example.novabee.ui.beehiveDetails.BeehiveDetailsViewModel
import com.example.novabee.utils.Constants.TAG
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeehiveFormFragment : Fragment() {

    private var _binding: FragmentBeehiveFormBinding? = null
    private val binding get() = _binding!!
    private var beehive: BeehiveResponse? = null
    private var apiary: ApiaryResponse? = null

    private val beehiveViewModel by viewModels<BeehiveDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBeehiveFormBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()
        bindHandlers()

    }

    private fun bindHandlers() {
        binding.btnSubmit.setOnClickListener {
            val name = binding.txtName.text.toString()
            val description = binding.txtDescription.text.toString()
            val deviceId = binding.txtDeviceId.text.toString()
            val beehiveRequest = BeehiveRequest(description, deviceId, name)



            if (beehive == null) {
                beehive.let {
                    Log.d(TAG, "Created beehive" + apiary!!._id)
                    beehiveViewModel.createBeehive(apiary!!._id, beehiveRequest)
                }


                findNavController().navigate(R.id.mainFragment)



            } else {
                beehiveViewModel.updateBeehive(beehive!!.apiary, beehive!!._id, beehiveRequest)
                findNavController().popBackStack()
            }
        }
    }

    private fun setInitialData() {
        val jsonBeehive = arguments?.getString("edit")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
            beehive?.let {
                binding.txtName.setText(it.name)
                binding.txtDescription.setText(it.description)
                binding.txtDeviceId.setText(it.deviceID)
            }
        } else {
            binding.addEditText.text = getString(R.string.txt_add_beehive)
        }

        val jsonApiary = arguments?.getString("apiaryId")
        if (jsonApiary != null){
            apiary = Gson().fromJson(jsonApiary, ApiaryResponse::class.java)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}