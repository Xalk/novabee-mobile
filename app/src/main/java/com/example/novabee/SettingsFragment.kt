package com.example.novabee

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.novabee.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    lateinit var locle: Locale
    private var currentLanguage = Locale.getDefault().language

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val languageSpinner = binding.languageSpinner

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        )


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter



        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected language from the Spinner
                val selectedLanguage = parent!!.getItemAtPosition(position) as String

                when(position){
                    0 -> {
                    }
                    1 -> setLocale("uk")
                    2 -> setLocale("en")
                }
                currentLanguage = selectedLanguage

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

    }



    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locle = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locle
            res.updateConfiguration(conf, dm)
            requireActivity().finish()
            requireActivity().startActivity(requireActivity().intent)
        } else {
            Toast.makeText(
                requireContext(), "Language, , already, , selected!", Toast.LENGTH_SHORT).show();
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

