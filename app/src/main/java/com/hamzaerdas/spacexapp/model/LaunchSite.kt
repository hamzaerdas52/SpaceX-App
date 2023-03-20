package com.hamzaerdas.spacexapp.model

import com.google.gson.annotations.SerializedName

data class LaunchSite(
    @SerializedName(value = "site_name")
    val siteName: String,
    @SerializedName(value = "site_name_long")
    val siteNameLong: String
)