package com.hamzaerdas.spacexapp.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName(value = "mission_patch")
    val missionPatch: String,
    @SerializedName(value = "mission_patch_small")
    val missionPatchSmall: String,
    @SerializedName(value = "wikipedia")
    val wikipedia: String,
    @SerializedName(value = "video_link")
    val videoLink: String
)