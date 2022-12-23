package com.example.novabee.ui.beehiveDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.ui.beehiveDetails.queen.QueenFragment
import com.google.gson.Gson

internal class BeehivePagerAdapter(
    var context: BeehiveDetailsFragment,
    fm: FragmentManager,
    var totalTabs: Int,
    private val beehiveResponse: BeehiveResponse
) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("info", Gson().toJson(beehiveResponse))
                bundle.putString("apiaryId", beehiveResponse.apiary)
                val fragment = InfoFragment()
                fragment.arguments = bundle

                return fragment
            }

            1 -> {
                val bundle = Bundle()
                bundle.putString("chart", Gson().toJson(beehiveResponse))
                val fragment = ChartFragment()
                fragment.arguments = bundle

                return fragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("queen", Gson().toJson(beehiveResponse))
                val fragment = QueenFragment()
                fragment.arguments = bundle

                return fragment
            }
            else -> getItem(position)
        }
    }


}