package com.spin.network

import com.spin.models.Images
import io.reactivex.Single
import retrofit2.http.GET

interface ImagesAPI {
    @GET("bins/n30si")
    fun getImages() : Single<Images>
}