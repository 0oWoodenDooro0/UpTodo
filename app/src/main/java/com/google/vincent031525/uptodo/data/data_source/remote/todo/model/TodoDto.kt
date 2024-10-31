package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

import com.google.vincent031525.uptodo.domain.model.Todo

data class TodoDto(
    val id: String,
    val title: String,
    val content: String,
    val dead_time: String,
    val done: Boolean,
    val user_id: String
) {
    fun toTodo(): Todo {
        return Todo(
            id = id,
            title = title,
            content = content,
            deadTime = dead_time,
            done = done
        )
    }
}
