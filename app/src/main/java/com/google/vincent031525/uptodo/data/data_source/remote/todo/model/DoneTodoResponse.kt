package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.gson.annotations.SerializedName

data class DoneTodoResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("id") val id: String
)