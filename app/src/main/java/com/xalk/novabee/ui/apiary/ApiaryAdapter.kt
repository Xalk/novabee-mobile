package com.xalk.novabee.ui.apiary

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xalk.novabee.NovabeeApplication
import com.xalk.novabee.R
import com.xalk.novabee.databinding.ApiaryItemBinding
import com.xalk.novabee.models.ApiaryResponse
import dagger.hilt.android.internal.Contexts.getApplication


class ApiaryAdapter(
    private val onApiaryClicked: (ApiaryResponse) -> Unit,
    private var context: Context
) :
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

    @SuppressLint("SetTextI18n")
    inner class ApiaryViewHolder(private val binding: ApiaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(apiary: ApiaryResponse) {
            binding.title.text = apiary.name
            binding.desc.text = apiary.description
            binding.beehives.text =
                context.resources.getString(R.string.txt_beehive_count) + apiary.beehives.size.toString()

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