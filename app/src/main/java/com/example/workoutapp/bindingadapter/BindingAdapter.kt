package com.example.workoutapp.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.workoutapp.R

fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("imageInt")
fun setImageViewResource(imageView: ImageView, resource: LiveData<Int>?){
    resource?.value?.let { imageView.setImageResource(it) }
}