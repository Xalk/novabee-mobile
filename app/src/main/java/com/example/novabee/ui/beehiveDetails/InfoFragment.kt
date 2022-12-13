package com.example.novabee.ui.beehiveDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.novabee.R
import com.example.novabee.models.ApiaryResponse
import com.example.novabee.utils.Constants.TAG
import com.google.gson.Gson


class InfoFragment() : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testData = arguments?.getString("info")
        if (testData != null) {
            Log.d(TAG, testData + "INFO")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

}