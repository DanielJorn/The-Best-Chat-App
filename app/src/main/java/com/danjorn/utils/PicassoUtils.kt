package com.danjorn.utils

import android.widget.ImageView
import com.danjorn.views.R
import com.squareup.picasso.Picasso

fun downloadImage(url: String?, imageView: ImageView) {
    val validUrl = getValidUrl(url)

    Picasso.get()
            .load(validUrl)
            .placeholder(R.drawable.common_placeholder)
            .into(imageView)
}

/**
 * Picasso API throws an exception if we put blank URL in it. Instead of blank URL we should put null.
 * Method validates this URL.
 * @param url URL to be validated
 * @return null if [url] is blank, otherwise - [url] itself
 * */
private fun getValidUrl(url: String?): String? {
    if (url != null && url.isBlank()) {
        return null
    } else return url
}
