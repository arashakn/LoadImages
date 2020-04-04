package com.spin.api

import com.spin.models.Images
import io.reactivex.Single
import retrofit2.http.GET

interface ImagesAPI {
    @GET("net.raquo.images/ContentStub.json")
    suspend fun getImages() : Images
}