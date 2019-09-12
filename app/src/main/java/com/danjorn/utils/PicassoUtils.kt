package com.danjorn.utils

import android.widget.ImageView
import com.danjorn.views.R
import com.squareup.picasso.Picasso

object PicassoUtils {
    fun commonImageDownload(url: String?, imageView: ImageView) {
        Picasso.get()
            .load(url)
            /*.placeholder(R.color.gray)*/
            .into(imageView)
    }
}