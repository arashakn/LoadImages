package com.spin.api

import com.spin.models.Images
import retrofit2.http.GET


/**
 * Creating an interface for defining endpoints using annotation
 * Each method of interface represent one possible API call
 */
interface ImagesAPI {
    @GET("net.raquo.images/ContentStub.json")
    suspend fun getImages() : Images
}