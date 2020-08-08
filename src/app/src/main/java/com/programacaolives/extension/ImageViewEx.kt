package com.programacaolives.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.programacaolives.R

fun ImageView.load(imageName: String) {
    val baseUrl = "${context.getString(R.string.base_url)}/images/"
    val imageUrl = "${baseUrl}${imageName}"
    val errorUrl = "${baseUrl}/default.jpg"

    Glide.with(context)
        .load(imageUrl)
        .error(Glide.with(context).load(errorUrl))
        .transition(withCrossFade())
        .into(this)
}