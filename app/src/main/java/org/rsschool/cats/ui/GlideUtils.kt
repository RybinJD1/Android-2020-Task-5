package org.rsschool.cats

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun getDefaultRequestOptions() = RequestOptions()
    .placeholder(R.drawable.loading_img)
    .error(R.drawable.ic_baseline_broken_image_24)
    .fallback(R.drawable.ic_baseline_photo_camera_24)
    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
