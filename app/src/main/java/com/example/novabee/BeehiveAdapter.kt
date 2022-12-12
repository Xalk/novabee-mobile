package com.example.novabee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.novabee.databinding.BeehiveItemBinding
import com.example.novabee.models.BeehiveResponse


class BeehiveAdapter(private val onBeehiveClicked: (BeehiveResponse) -> Unit) :
    ListAdapter<BeehiveResponse,BeehiveAdapter.BeehiveViewHolder>(ComparatorDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeehiveViewHolder {
        val binding = BeehiveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeehiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeehiveViewHolder, position: Int) {
        val apiary = getItem(position)
        apiary?.let {
            holder.bind(it)
        }
    }

    inner class BeehiveViewHolder(private val binding: BeehiveItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(beehive: BeehiveResponse) {
            binding.title.text = beehive.name
            binding.desc.text = beehive.description
            binding.root.setOnClickListener {
                onBeehiveClicked(beehive)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<BeehiveResponse>() {
        override fun areItemsTheSame(oldItem: BeehiveResponse, newItem: BeehiveResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: BeehiveResponse, newItem: BeehiveResponse): Boolean {
            return oldItem == newItem
        }
    }

}