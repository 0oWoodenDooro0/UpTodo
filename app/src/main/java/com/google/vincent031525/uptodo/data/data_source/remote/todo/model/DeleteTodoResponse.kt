package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.gson.annotations.SerializedName

data class DeleteTodoResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String
)