package com.google.vincent031525.uptodo.data.data_source.remote.user.model

data class UserLoginResponse(
    val code: Int,
    val data: UserDto,
    val msg: String,
    val refresh_token: RefreshToken,
    val token: AccessToken
)
