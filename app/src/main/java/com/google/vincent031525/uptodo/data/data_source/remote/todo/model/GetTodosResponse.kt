package com.google.vincent031525.uptodo.data.data_source.remote.todo.model

data class GetTodosResponse (
    val code: Int,
    val msg: List<TodoDto>
)