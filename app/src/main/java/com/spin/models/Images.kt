package com.spin.models

data class Image(
    val url: String?,
    val title: String?
)

data class Images(
    val data: ArrayList<Image>
)