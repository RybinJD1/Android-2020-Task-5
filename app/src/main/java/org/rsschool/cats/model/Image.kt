package org.rsschool.cats.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @Json(name = "id")
    val id: String,
    @Json(name = "url")
    val url: String,
) : Parcelable
