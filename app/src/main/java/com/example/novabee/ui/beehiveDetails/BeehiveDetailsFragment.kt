package com.example.novabee.ui.beehiveDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.novabee.R
import com.example.novabee.databinding.FragmentBeehiveDetailsBinding
import com.example.novabee.models.BeehiveResponse
import com.example.novabee.utils.Constants
import com.example.novabee.utils.Constants.TAG
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeehiveDetailsFragment : Fragment() {

    private var _binding: FragmentBeehiveDetailsBinding? = null
    private val binding get() = _binding!!
    private var beehive: BeehiveResponse? = null

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBeehiveDetailsBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()

        binding.textView.text = beehive!!.name

        Log.d(TAG, "beehive" + beehive.toString())

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Info"))
        tabLayout.addTab(tabLayout.newTab().setText("Chart"))
        tabLayout.addTab(tabLayout.newTab().setText("Queen"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = BeehivePagerAdapter(this, childFragmentManager, tabLayout.tabCount)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

    private fun setInitialData() {
        val jsonApiary = arguments?.getString("beehive")
        if (jsonApiary != null) {
            beehive = Gson().fromJson(jsonApiary, BeehiveResponse::class.java)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}