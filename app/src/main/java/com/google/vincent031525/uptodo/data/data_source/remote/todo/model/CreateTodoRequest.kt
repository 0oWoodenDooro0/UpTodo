package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

data class CreateTodoRequest(
    val title: String,
    val content: String,
    val dead_time: String
)
