package com.example.novabee.ui.beehiveDetails

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class BeehivePagerAdapter(var context: BeehiveDetailsFragment, fm: FragmentManager, var totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                InfoFragment()
            }

            1 -> {
                ChartFragment()
            }
            2 -> {
                QueenFragment()
            }
            else -> getItem(position)
        }
    }


}