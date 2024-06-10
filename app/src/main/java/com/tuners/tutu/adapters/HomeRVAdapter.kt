package com.tuners.tutu.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuners.tutu.data.local.HomeRVData
import com.tuners.tutu.databinding.HomeRvLayoutBinding

class HomeRVAdapter(private val homeRVData: ArrayList<HomeRVData>) : RecyclerView.Adapter<HomeRVAdapter.ListViewHolder>() {
    class ListViewHolder(val binding: HomeRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = HomeRvLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (thumbnail, link) = homeRVData[position]
        Glide.with(holder.binding.root)
            .load(thumbnail)
            .into(holder.binding.thumbnail)

        holder.binding.root.setOnClickListener {
            holder.binding.root.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        }
    }
}