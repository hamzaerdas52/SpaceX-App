package com.hamzaerdas.spacexapp.util

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hamzaerdas.spacexapp.R
import com.hamzaerdas.spacexapp.databinding.ActivityDetailBinding

fun recyclerViewDownAnimation(holder: ViewHolder) {
    holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_recyclerview_down)
}

fun detailAnimation(binding: ActivityDetailBinding){
    binding.launchNameLayout.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_left)
    binding.launchImageLayout.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_right)
    binding.launchDetailLayout.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_down)
}
