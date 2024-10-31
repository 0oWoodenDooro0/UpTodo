package com.google.vincent031525.uptodo.data.data_source.remote.user.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("AccessUUid") val accessUUId: String,
    @SerializedName("AtExp") val atExp: String
)
