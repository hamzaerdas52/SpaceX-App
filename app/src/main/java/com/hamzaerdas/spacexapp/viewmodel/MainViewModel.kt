package com.hamzaerdas.spacexapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzaerdas.spacexapp.model.Launch
import com.hamzaerdas.spacexapp.service.SpaceXService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val spaceXService: SpaceXService) : ViewModel() {

    val allLaunches = MutableLiveData<List<Launch>>()
    val yearToLaunches = MutableLiveData<List<Launch>>()
    val loadingData = MutableLiveData<Boolean>()
    val errorData = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun getAllData() {
        getAll()
    }

    fun getToDate(year: Int) {
        getToYear(year)
    }

    private fun getAll() {

        loadingData.value = true

        disposable.add(
            spaceXService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Launch>>() {
                    override fun onSuccess(t: List<Launch>) {
                        allLaunches.value = t
                        loadingData.value = false
                        errorData.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Hata", "Veri Gelmedi", e)
                        errorData.value = true
                    }

                })
        )
    }

    private fun getToYear(year: Int) {

        loadingData.value = true

        disposable.add(
            spaceXService.getToYear(year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Launch>>() {
                    override fun onSuccess(t: List<Launch>) {
                        yearToLaunches.value = t
                        loadingData.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Hata", "Veri Gelmedi", e)
                        errorData.value = true
                    }

                })
        )

    }
}