package com.hamzaerdas.spacexapp.service

import com.hamzaerdas.spacexapp.model.Launch
import com.hamzaerdas.spacexapp.model.LaunchDetail
import com.hamzaerdas.spacexapp.util.Constants.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SpaceXService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SpaceXAPI::class.java)

    fun get(): Single<List<Launch>> {
        return api.getAll()
    }

    fun getToYear(year: Int): Single<List<Launch>> {
        return api.getToYear(year)
    }

    fun getOne(number: Int): Single<List<LaunchDetail>>{
        return api.getOne(number)
    }

}