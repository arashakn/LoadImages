package com.spin.fragments

import androidx.lifecycle.*
import com.spin.models.Image
import com.spin.models.Images
import com.spin.api.ImagesAPIClient
import com.spin.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class ImagesListViewModel : ViewModel() {
    val error = MutableLiveData<Boolean>(false)
    val progress = MutableLiveData<Boolean>(false)
    var allImages = liveData {
        error.value = false
        progress.value = true
        try {
            val images = ImagesAPIClient.imagesAPI.getImages()
            emit(images)
        } catch (ex: Exception) {
            error.value = true
        }
        progress.value = false
    }


    fun getImages() = viewModelScope.launch {
        allImages = liveData {
            error.value = false
            progress.value = true
            try {
                val images = ImagesAPIClient.imagesAPI.getImages()
                emit(images)
            } catch (ex: Exception) {
                error.value = true
            }
            progress.value = false
        }
    }
}
