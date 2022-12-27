package com.xalk.novabee

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker.OnDateChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xalk.novabee.databinding.FragmentQueenFormBinding
import com.xalk.novabee.models.BeehiveRequest
import com.xalk.novabee.models.BeehiveResponse
import com.xalk.novabee.models.QueenRequest
import com.xalk.novabee.models.QueenResponse
import com.xalk.novabee.ui.beehiveDetails.BeehiveDetailsViewModel
import com.xalk.novabee.ui.beehiveDetails.queen.QueenViewModel
import com.xalk.novabee.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QueenFormFragment : Fragment() {

    private var _binding: FragmentQueenFormBinding? = null
    private val binding get() = _binding!!
    private var queen: QueenResponse? = null

    private var beehive: BeehiveResponse? = null

    private val queenViewModel by viewModels<QueenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQueenFormBinding.inflate(inflater, container, false)
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


            val introduced =
                "${binding.datePicker.year}-${binding.datePicker.month}-${binding.datePicker.dayOfMonth}"

            val isOut = binding.checkBox.isChecked
            val description = binding.txtDescription.text.toString()

            val queenRequest = QueenRequest(name, introduced, isOut, description)

            if (queen == null && beehive != null) {
                queen.let {
                    queenViewModel.createQueen(beehive!!.apiary, beehive!!._id, queenRequest)
                }
                findNavController().popBackStack()
            } else {
                queenViewModel.updateQueen(beehive!!.apiary, beehive!!._id, queenRequest)
                findNavController().popBackStack()
            }

        }
    }

    private fun setInitialData() {
        val jsonQueen = arguments?.getString("qEdit")

        if (jsonQueen != null) {
            queen = Gson().fromJson(jsonQueen, QueenResponse::class.java)
            queen?.let {
                binding.txtName.setText(it.name)

                val date = it.introducedFrom.split("-")

                binding.datePicker.init(
                    date[0].toInt(),
                    date[1].toInt(),
                    date[2].substring(0, 2).toInt(),
                    datePickerListener
                )
                binding.checkBox.isChecked = it.isOut
                binding.txtDescription.setText(it.description)
            }
        } else {
            binding.addEditText.text = getString(R.string.txt_add_queen_form)
        }


        val jsonBeehive = arguments?.getString("qBeehive")
        if (jsonBeehive != null) {
            beehive = Gson().fromJson(jsonBeehive, BeehiveResponse::class.java)
        }
    }

    private var datePickerListener =
        OnDateChangedListener { birthDayDatePicker, newYear, newMonth, newDay ->
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}