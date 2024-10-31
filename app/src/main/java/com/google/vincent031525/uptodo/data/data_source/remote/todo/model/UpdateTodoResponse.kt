package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.gson.annotations.SerializedName

data class UpdateTodoResponse(
    @SerializedName("code") val code: Int
)
