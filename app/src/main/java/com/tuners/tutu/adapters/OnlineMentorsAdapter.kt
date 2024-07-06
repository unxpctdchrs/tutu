package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuners.tutu.data.remote.response.UserDetailsItem
import com.tuners.tutu.databinding.MentorLayoutBinding
import com.tuners.tutu.ui.main_students.home.consult.ConsultFragmentDirections
import com.tuners.tutu.ui.main_students.message.MessageFragmentDirections

class OnlineMentorsAdapter(private val isLoading: Boolean) : ListAdapter<UserDetailsItem, OnlineMentorsAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: MentorLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentor: UserDetailsItem) {
            binding.tvName.text = mentor.username
//            binding.tvRating.text = mentor.rating.toString()
            binding.root.setOnClickListener {
                val toChat =  MessageFragmentDirections.actionNavigationMessageToChatFragment()
                it.findNavController().navigate(toChat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MentorLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserDetailsItem>() {
            override fun areItemsTheSame(
                oldItem: UserDetailsItem,
                newItem: UserDetailsItem
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: UserDetailsItem,
                newItem: UserDetailsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}