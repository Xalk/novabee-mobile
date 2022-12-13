package com.example.novabee.ui.beehiveDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.gson.Gson

internal class BeehivePagerAdapter(var context: BeehiveDetailsFragment, fm: FragmentManager, var totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }



    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("info", "testForInfo")
                val fragment = InfoFragment()
                fragment.arguments = bundle

                return fragment
            }

            1 -> {
                val bundle = Bundle()
                bundle.putString("chart", "testForChart")
                val fragment = ChartFragment()
                fragment.arguments = bundle

                return fragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("queen", "testForQueen")
                val fragment = QueenFragment()
                fragment.arguments = bundle

                return fragment
            }
            else -> getItem(position)
        }
    }


}