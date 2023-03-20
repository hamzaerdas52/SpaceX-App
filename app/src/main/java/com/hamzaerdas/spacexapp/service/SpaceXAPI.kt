package com.hamzaerdas.spacexapp.service

import com.hamzaerdas.spacexapp.model.Launch
import com.hamzaerdas.spacexapp.model.LaunchDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXAPI {
    // https://api.spacexdata.com/v2/
    // launches

    // https://api.spacexdata.com/v2/launches?launch_year=2007

    @GET("launches")
    fun getAll(): Single<List<Launch>>

    @GET("launches")
    fun getToYear(
        @Query("launch_year") date: Int
    ): Single<List<Launch>>

    @GET("launches")
    fun getOne(
        @Query("flight_number") number: Int
    ): Single<List<LaunchDetail>>

}