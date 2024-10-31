package com.google.vincent031525.uptodo.domain.model

import com.google.vincent031525.uptodo.data.data_source.local.todo.TodoEntity
import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.CreateTodoRequest

data class Todo(
    val id: String,
    val title: String,
    val content: String,
    val deadTime: String,
    val done: Boolean = false,
) {
    fun toEntity() = TodoEntity(
        title = title,
        content = content,
        deadTime = deadTime,
        done = done
    )

    fun toCreateRequest() = CreateTodoRequest(
        title = title,
        content = content,
        dead_time = deadTime
    )
}