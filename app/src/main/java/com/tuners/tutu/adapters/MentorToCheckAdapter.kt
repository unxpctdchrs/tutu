package com.tuners.tutu.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.data.remote.response.MentorsToCheck
import com.tuners.tutu.databinding.MentorToCheckLayoutBinding
import com.tuners.tutu.ui.main_admin.AdminMainViewModel

class MentorToCheckAdapter(
    private val context: Context,
    private val isLoading: Boolean,
    private val adminMainViewModel: AdminMainViewModel
) : ListAdapter<MentorsToCheck, MentorToCheckAdapter.ViewHolder>(DIFF_CALLBACK) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(private val binding: MentorToCheckLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentor: MentorsToCheck) {
            binding.tvName.text = mentor.username

            binding.root.setOnClickListener {
                val customLayout = inflater.inflate(R.layout.mentor_data_to_check_dialog, null)
                val alertDialogBuilder = AlertDialog.Builder(context)
                    .setView(customLayout)

                val approveBtn = customLayout.findViewById<MaterialButton>(R.id.btn_approve)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                approveBtn.setOnClickListener {
                    adminMainViewModel.approveMentor(mentorId = mentor.mentorId)
                    alertDialog.dismiss()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MentorToCheckLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentors = getItem(position)
        holder.bind(mentors)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MentorsToCheck>() {
            override fun areItemsTheSame(
                oldItem: MentorsToCheck,
                newItem: MentorsToCheck
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: MentorsToCheck,
                newItem: MentorsToCheck
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}