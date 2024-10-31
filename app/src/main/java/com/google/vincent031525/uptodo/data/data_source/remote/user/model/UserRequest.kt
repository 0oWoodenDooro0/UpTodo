package com.google.vincent031525.uptodo.data.data_source.remote.user.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("username") val userName: String,
    @SerializedName("password") val password: String
)
