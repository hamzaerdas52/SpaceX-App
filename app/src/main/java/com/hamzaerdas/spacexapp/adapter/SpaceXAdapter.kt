package com.hamzaerdas.spacexapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamzaerdas.spacexapp.databinding.RecyclerRowBinding
import com.hamzaerdas.spacexapp.model.Launch
import com.hamzaerdas.spacexapp.util.recyclerViewDownAnimation
import com.hamzaerdas.spacexapp.view.DetailActivity

class SpaceXAdapter() : RecyclerView.Adapter<SpaceXAdapter.SpaceXViewHolder>() {

    private val launchList = ArrayList<Launch>()
    private var scrollDown = false

    class SpaceXViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceXViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpaceXViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpaceXViewHolder, position: Int) {


        val launch = launchList[holder.adapterPosition]
        holder.binding.launch = launch

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("number", launch.flightNumber)
            holder.itemView.context.startActivity(intent)
        }

        recyclerViewDownAnimation(holder)

    }

    override fun getItemCount(): Int {
        return launchList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newLaunchList : List<Launch>){
        launchList.clear()
        launchList.addAll(newLaunchList)
        notifyDataSetChanged()
    }
}