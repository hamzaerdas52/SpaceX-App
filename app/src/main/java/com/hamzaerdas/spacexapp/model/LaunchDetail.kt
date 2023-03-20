package com.hamzaerdas.spacexapp.model

import com.google.gson.annotations.SerializedName

data class LaunchDetail(
    @SerializedName(value = "flight_number")
    val flightNumber: Int,
    @SerializedName(value = "mission_name")
    val missionName: String,
    @SerializedName(value = "launch_year")
    val launchYear: String,
    @SerializedName(value = "launch_date_utc")
    var launchDateUtc: String,
    @SerializedName(value = "launch_site")
    val launchSite: LaunchSite,
    @SerializedName(value = "launch_success")
    val launchSuccess: Boolean,
    @SerializedName(value = "launch_failure_details")
    val launchFailureDetails: LaunchFailureDetails? = null,
    @SerializedName(value = "links")
    val links: Links,
    @SerializedName(value = "details")
    val details: String
)
