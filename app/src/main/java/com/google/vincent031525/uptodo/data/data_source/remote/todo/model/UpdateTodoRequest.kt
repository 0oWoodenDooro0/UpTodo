package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.gson.annotations.SerializedName

data class UpdateTodoRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("dead_time") val deadTime: String
)
