package com.spin.repository

import com.spin.api.RetrofitBuilder.imagesAPI
import com.spin.models.Images

object MainRepository {
    suspend fun  getAllImages() : Images{
        return imagesAPI.getImages()
    }
}