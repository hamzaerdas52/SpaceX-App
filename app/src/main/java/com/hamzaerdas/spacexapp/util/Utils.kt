package com.hamzaerdas.spacexapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewbinding.ViewBinding
import com.hamzaerdas.spacexapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.picasso.transformations.BlurTransformation
import java.lang.Exception


fun ImageView.dowloadImage(url: String?, placeholder: CircularProgressDrawable) {

    Picasso.get()
        .load(url)
        .placeholder(placeholder)
        .error(R.drawable.ic_launcher_foreground)
        .into(this);
}

fun makePlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 35f
        start()
    }
}

@BindingAdapter("android:dowloadImage")
fun dowloadImage(view: ImageView, url: String?){
    view.dowloadImage(url, makePlaceHolder(view.context))
}

fun ImageView.changeBackground(url: String?, binding: ViewBinding, radius: Int) {
    Picasso
        .get()
        .load(url)
        .transform(BlurTransformation(binding.root.context, radius, 1))
        .placeholder(makePlaceHolder(binding.root.context))
        .into(this)
}