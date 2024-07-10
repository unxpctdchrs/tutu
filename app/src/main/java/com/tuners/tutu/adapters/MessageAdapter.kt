package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuners.tutu.data.remote.response.Messages
import com.tuners.tutu.databinding.ChatRightLayoutBinding

class MessageAdapter : ListAdapter<Messages, MessageAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ChatRightLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Messages) {
            binding.tvMessage.text = msg.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatRightLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentors = getItem(position)
        holder.bind(mentors)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Messages>() {
            override fun areItemsTheSame(
                oldItem: Messages,
                newItem: Messages
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: Messages,
                newItem: Messages
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}