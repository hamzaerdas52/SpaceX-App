package com.hamzaerdas.spacexapp.model

import com.google.gson.annotations.SerializedName

data class LaunchFailureDetails(
    @SerializedName(value = "time")
    val time: Int,
    @SerializedName(value = "reason")
    val reason: String
)
