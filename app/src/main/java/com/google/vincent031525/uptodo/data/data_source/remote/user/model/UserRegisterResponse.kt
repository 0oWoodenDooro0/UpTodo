package com.google.vincent031525.uptodo.data.data_source.remote.user.model

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String
)
