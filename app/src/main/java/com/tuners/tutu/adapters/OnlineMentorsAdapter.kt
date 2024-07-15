package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuners.tutu.data.remote.response.ResultItem
import com.tuners.tutu.databinding.MentorLayoutBinding
import com.tuners.tutu.ui.main_students.home.consult.ConsultFragmentDirections

class OnlineMentorsAdapter(private val isLoading: Boolean) : ListAdapter<ResultItem, OnlineMentorsAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: MentorLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentor: ResultItem) {
            binding.tvName.text = mentor.username
            binding.tvRating.text = mentor.rating.toString()
            binding.root.setOnClickListener {
                val toOrder = ConsultFragmentDirections.actionConsultFragmentToOrderFragment()
                toOrder.username = mentor.username!!
                toOrder.ratings = mentor.rating!!
                toOrder.mentorId = mentor.mentorId!!
                it.findNavController().navigate(toOrder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MentorLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentors = getItem(position)
        holder.bind(mentors)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultItem>() {
            override fun areItemsTheSame(
                oldItem: ResultItem,
                newItem: ResultItem
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: ResultItem,
                newItem: ResultItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}