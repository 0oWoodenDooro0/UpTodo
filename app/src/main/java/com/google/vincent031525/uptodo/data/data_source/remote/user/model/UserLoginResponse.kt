package com.google.vincent031525.uptodo.data.data_source.remote.user.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: UserDto,
    @SerializedName("msg") val msg: String,
    @SerializedName("refresh_token") val refreshToken: RefreshToken,
    @SerializedName("token") val token: AccessToken
)
