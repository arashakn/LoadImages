package com.spin.models

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url")
    val url: String?,
    @SerializedName("title")
    val title: String?
)

data class Images(
    @SerializedName("data")
    val data: ArrayList<Image>
)