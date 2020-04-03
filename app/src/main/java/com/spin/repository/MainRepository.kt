package com.spin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.spin.api.ImagesAPIClient
import com.spin.api.ImagesAPIClient.imagesAPI
import com.spin.models.Images
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object MainRepository {
    suspend fun  getAllImages() : Images{
        return imagesAPI.getImages()
    }
}