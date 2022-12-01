package com.android.cricbuzz.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.cricbuzz.R
import com.bumptech.glide.Glide

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, imageName: String) {
    imageView.setImageResource(imageName.getDrawable())
}


@BindingAdapter("android:image")
fun setImageViewResource(imageView: ImageView, list: List<String>) {
   val image = list.firstOrNull()?.getDrawable() ?: R.drawable.one
    imageView.setImageResource(image)
}