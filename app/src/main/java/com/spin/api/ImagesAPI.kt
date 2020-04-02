package com.spin.api

import com.spin.models.Images
import io.reactivex.Single
import retrofit2.http.GET

interface ImagesAPI {
    @GET("bins/n30si")
    suspend fun getImages() : Images
}