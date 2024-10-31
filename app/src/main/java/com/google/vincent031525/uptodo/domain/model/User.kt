package com.google.vincent031525.uptodo.domain.model

import com.google.vincent031525.uptodo.data.data_source.remote.user.model.UserRequest

data class User(
    val userName: String,
    val password: String
) {
    fun toUserRequest() = UserRequest(userName = userName, password = password)
}