package com.example.novabee.ui.apiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.novabee.databinding.ApiaryItemBinding
import com.example.novabee.models.ApiaryResponse


class ApiaryAdapter(private val onApiaryClicked: (ApiaryResponse) -> Unit) :
    ListAdapter<ApiaryResponse, ApiaryAdapter.ApiaryViewHolder>(ComparatorDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiaryViewHolder {
        val binding = ApiaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApiaryViewHolder, position: Int) {
        val apiary = getItem(position)
        apiary?.let {
            holder.bind(it)
        }
    }

    inner class ApiaryViewHolder(private val binding: ApiaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(apiary: ApiaryResponse) {
            binding.title.text = apiary.name
            binding.desc.text = apiary.description
            binding.beehives.text = "Beehives: " + apiary.beehives.size.toString()
            binding.root.setOnClickListener {
                onApiaryClicked(apiary)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<ApiaryResponse>() {
        override fun areItemsTheSame(oldItem: ApiaryResponse, newItem: ApiaryResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: ApiaryResponse, newItem: ApiaryResponse): Boolean {
            return oldItem == newItem
        }
    }


}