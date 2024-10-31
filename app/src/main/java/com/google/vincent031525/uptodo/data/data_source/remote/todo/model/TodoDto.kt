package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.gson.annotations.SerializedName
import com.google.vincent031525.uptodo.domain.model.Todo

data class TodoDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("dead_time") val deadTime: String,
    @SerializedName("done") val done: Boolean,
    @SerializedName("user_id") val userId: String
) {
    fun toTodo(): Todo {
        return Todo(
            id = id,
            title = title,
            content = content,
            deadTime = deadTime,
            done = done
        )
    }
}
