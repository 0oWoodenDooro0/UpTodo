package com.google.vincent031525.uptodo.data.data_source.remote.user

import com.google.vincent031525.uptodo.data.data_source.remote.user.model.UserLoginResponse
import com.google.vincent031525.uptodo.data.data_source.remote.user.model.UserRegisterResponse
import com.google.vincent031525.uptodo.data.data_source.remote.user.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("/login")
    suspend fun loginUser(
        @Header("API-KEY") apiKey: String,
        @Body user: UserRequest
    ): Response<UserLoginResponse>

    @POST("/register")
    suspend fun registerUser(
        @Header("API-KEY") apiKey: String,
        @Body user: UserRequest
    ): Response<UserRegisterResponse>
}