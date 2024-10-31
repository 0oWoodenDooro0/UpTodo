package com.google.vincent031525.uptodo.domain.use_case

data class TodoUseCases(
    val getTodos: GetTodo,
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo
)
