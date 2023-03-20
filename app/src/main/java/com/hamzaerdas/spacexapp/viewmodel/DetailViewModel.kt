package com.hamzaerdas.spacexapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.spacexapp.model.LaunchDetail
import com.hamzaerdas.spacexapp.service.SpaceXService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    val launch = MutableLiveData<List<LaunchDetail>>()
    val loadingData = MutableLiveData<Boolean>()
    val errorData = MutableLiveData<Boolean>()

    private val spaceXService = SpaceXService()
    private val disposable = CompositeDisposable()

    fun getLaunch(number: Int){
        getOne(number)
    }

    private fun getOne(number: Int) {

        loadingData.value = true

        disposable.add(
            spaceXService.getOne(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<LaunchDetail>>() {
                    override fun onSuccess(t: List<LaunchDetail>) {
                        t.forEach {
                            it.launchDateUtc = it.launchDateUtc.substring(0,19)
                        }
                        launch.value = t
                        loadingData.value = false
                        errorData.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Hata", "Veri gelmedi")
                        errorData.value = true
                    }

                })
        )
    }

}