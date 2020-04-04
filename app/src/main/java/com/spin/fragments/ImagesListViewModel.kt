package com.spin.fragments

import androidx.lifecycle.*
import com.spin.repository.MainRepository

class ImagesListViewModel : ViewModel() {

    val error = MutableLiveData<Boolean>(false)
    /**
     * Utilizing new API of coroutines for livedata using livedata builder function
     * getAllImages() is a suspend function declared
     * Use the liveData builder function to call getAllImages asynchronously
     * and then use emit() to emit the result for allImages liveData
     * and the result will be observed by fragment.
     *
     * The code block starts executing when LiveData becomes active and is canceled if the LiveData becomes inactive.
     * If it is canceled before completion, it is restarted if the LiveData becomes active again.
     * If it completed successfully in a previous run, it doesn't restart. Note that it is restarted only if canceled automatically.
     */
    var allImages = liveData {
        error.value = false
        try {
            val images = MainRepository.getAllImages()
            emit(images)
        } catch (ex: Exception) {
            error.value = true
        }
    }
}
