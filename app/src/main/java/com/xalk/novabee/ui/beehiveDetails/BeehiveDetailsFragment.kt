package com.xalk.novabee.ui.beehiveDetails

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.xalk.novabee.R
import com.xalk.novabee.api.BeehiveAPI
import com.xalk.novabee.databinding.FragmentBeehiveDetailsBinding
import com.xalk.novabee.models.BeehiveResponse
import com.xalk.novabee.ui.beehive.BeehiveViewModel
import com.xalk.novabee.utils.Constants
import com.xalk.novabee.utils.Constants.TAG
import com.xalk.novabee.utils.NetworkResult
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

    private val beehiveViewModel by viewModels<BeehiveViewModel>()
    private val beehiveDetailsViewModel by viewModels<BeehiveDetailsViewModel>()

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
        bindObservers()
        beehiveDetailsViewModel.getBeehive(beehive!!.apiary, beehive!!._id)



        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.txt_tab_info_details)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.txt_tab_chart_details)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.txt_tab_queen_details)))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = BeehivePagerAdapter(this, childFragmentManager, tabLayout.tabCount, beehive!!)

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

        binding.deleteBtn.setOnClickListener {
            onAlertDialog(view)
        }

    }




    private fun setInitialData() {


        val jsonBeehive = arguments?.getString("beehive")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
        }


    }

    private fun onAlertDialog(view: View) {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(view.context, R.style.AlertDialogTheme)


        // set title
        builder.setTitle(getString(R.string.txt_alert_title))

        //set content area
        builder.setMessage(getString(R.string.txt_alert_beehive_delete))

        //set negative button
        builder.setPositiveButton(
            getString(R.string.txt_alert_title)
        ) { dialog, id ->
            beehive.let {
                beehiveDetailsViewModel.deleteBeehive(it!!.apiary, it._id)
            }
            findNavController().popBackStack()
            Toast.makeText(requireContext(), getString(R.string.txt_alert_delete_ok), Toast.LENGTH_SHORT).show()
        }

        //set positive button
        builder.setNegativeButton(
            getString(R.string.txt_alert_cancel)
        ) { dialog, id ->
            // User cancelled the dialog
        }



        builder.show()


    }

    private fun bindObservers() {
        beehiveDetailsViewModel.oneBeehiveLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
//                    beehive = it.data
                    binding.textView.text = it.data!!.name
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
                }

            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}