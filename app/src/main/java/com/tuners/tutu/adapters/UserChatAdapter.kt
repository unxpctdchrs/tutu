package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuners.tutu.data.remote.response.ChatsItem
import com.tuners.tutu.databinding.ChatUserLayoutBinding
import com.tuners.tutu.ui.main_mentors.message.MentorMessageFragmentDirections
import com.tuners.tutu.ui.main_students.message.MessageFragmentDirections

class UserChatAdapter(private val isMentor: Boolean) : ListAdapter<ChatsItem, UserChatAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ChatUserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatsItem) {
            if (isMentor) {
                binding.tvName.text = chat.studentUsername
                binding.root.setOnClickListener {
                    val toMessages = MentorMessageFragmentDirections.actionNavigationMessageToChatFragment2()
                    toMessages.username = chat.studentUsername
                    toMessages.roomId = chat.chatroomId
                    it.findNavController().navigate(toMessages)
                }
            } else {
                binding.tvName.text = chat.mentorUsername
                binding.root.setOnClickListener {
                    val toMessages = MessageFragmentDirections.actionNavigationMessageToChatFragment()
                    toMessages.username = chat.mentorUsername
                    toMessages.roomId = chat.chatroomId
                    it.findNavController().navigate(toMessages)
                }
            }



            binding.tvChatDetails.text = chat.lastMsg
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentors = getItem(position)
        holder.bind(mentors)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatsItem>() {
            override fun areItemsTheSame(
                oldItem: ChatsItem,
                newItem: ChatsItem
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ChatsItem,
                newItem: ChatsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}