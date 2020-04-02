package com.spin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.spin.api.ImagesAPIClient.imagesAPI
import com.spin.models.Images
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object MainRepository {
    var job : CompletableJob? = null

    val allImages = liveData {
        val images = imagesAPI.getImages()
        emit(images)
    }

    fun getImages() :LiveData<Images>{
        job = Job()
        return object : LiveData<Images>(){
            override fun onActive() {
                super.onActive()
                job?.let{theJob->
                    CoroutineScope(IO +theJob).launch {
                        val images = imagesAPI.getImages()
                        withContext(Main){
                            value  = images
                            theJob.complete()
                        }
                    }

                }
            }
        }

    }

    fun getAllImages(){

    }
    fun cancelJobs(){
        job?.cancel()
    }

}