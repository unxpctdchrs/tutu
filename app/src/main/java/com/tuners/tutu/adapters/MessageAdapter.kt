package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuners.tutu.data.remote.response.Messages
import com.tuners.tutu.databinding.ChatLeftLayoutBinding
import com.tuners.tutu.databinding.ChatRightLayoutBinding

class MessageAdapter(private val currentUserId: String) : ListAdapter<Messages, MessageAdapter.ViewHolder>(DIFF_CALLBACK) {
    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(message: Messages)

        class SenderViewHolder(private val binding: ChatRightLayoutBinding) : ViewHolder(binding.root) {
            override fun bind(message: Messages) {
                binding.tvMessage.text = message.message
            }
        }

        class ReceiverViewHolder(private val binding: ChatLeftLayoutBinding) : ViewHolder(binding.root) {
            override fun bind(message: Messages) {
                binding.tvMessage.text = message.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderId == currentUserId) VIEW_TYPE_SENDER else VIEW_TYPE_RECEIVER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SENDER -> {
                val binding = ChatRightLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.SenderViewHolder(binding)
            }
            else -> {
                val binding = ChatLeftLayoutBinding.inflate(inflater, parent, false)
                ViewHolder.ReceiverViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private const val VIEW_TYPE_SENDER = 1
        private const val VIEW_TYPE_RECEIVER = 2

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