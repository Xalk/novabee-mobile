package com.example.novabee

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.novabee.databinding.FragmentApiaryFormBinding
import com.example.novabee.databinding.FragmentBeehiveBinding
import com.example.novabee.models.ApiaryRequest
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.models.BeehiveRequest
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.ui.apiary.ApiaryViewModel
import com.example.novabee.ui.beehiveDetails.BeehiveDetailsViewModel
import com.example.novabee.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApiaryFormFragment : Fragment() {

    private var _binding: FragmentApiaryFormBinding? = null
    private val binding get() = _binding!!
    private var apiary: ApiaryResponse? = null
    private val apiaryViewModel by viewModels<ApiaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentApiaryFormBinding.inflate(inflater, container, false)

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
            val apiaryRequest = ApiaryRequest(description, name, "2022-12-02")

            if (apiary == null) {
                apiary.let {
                    apiaryViewModel.createApiary(apiaryRequest)
                }
                findNavController().popBackStack()
            } else {
                apiaryViewModel.updateApiary(apiary!!._id, apiaryRequest)
                findNavController().popBackStack()
                findNavController().popBackStack()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInitialData() {
        val jsonApiary = arguments?.getString("apiary")
        if (jsonApiary != null) {
            apiary = Gson().fromJson(jsonApiary, ApiaryResponse::class.java)
            apiary?.let {
                binding.txtName.setText(it.name)
                binding.txtDescription.setText(it.description)
            }
        } else {
            binding.addEditText.text = getString(R.string.txt_add_apiary)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}