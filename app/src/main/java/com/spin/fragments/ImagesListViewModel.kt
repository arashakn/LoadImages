package com.spin.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spin.models.Image
import com.spin.models.Images
import com.spin.network.ImagesAPIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException

class ImagesListViewModel : ViewModel() {
    val error = MutableLiveData<Boolean>(false)
    val progress = MutableLiveData<Boolean>()
    val images = MutableLiveData<ArrayList<Image>>(ArrayList<Image>())
    private val compositeDisposable  = CompositeDisposable ()


    fun fetchImages( ){
        error.value = false
        progress.value = false
        try {
            val disposable = ImagesAPIClient.getImageService().getImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<Images>(){
                    override fun onSuccess(value: Images?) {
                        value?.data?.let {
                            images.value = it
                        }
                        progress.value = false
                    }
                    override fun onError(e: Throwable?) {
                        error.value = true
                        progress.value = false
                    }
                })
            compositeDisposable.add(disposable)
        }catch (e : RuntimeException){
            error.value = true
            progress.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()

    }

}
