package com.google.vincent031525.uptodo.data.data_source.remote.user.model

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("RefreshToken") val refreshToken: String,
    @SerializedName("RefreshUUid") val refreshUUId: String,
    @SerializedName("ReExp") val reExp: String
)
