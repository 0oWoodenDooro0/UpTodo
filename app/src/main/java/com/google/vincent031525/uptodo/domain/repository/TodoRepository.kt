package com.google.vincent031525.uptodo.domain.repository

import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.TodoDto
import com.google.vincent031525.uptodo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(id: String): Flow<Result<List<Todo>>>

    suspend fun insertTodo(todo: Todo): Flow<Result<TodoDto>>

    suspend fun updateTodo(todo:Todo): Flow<Result<*>>

    suspend fun doneTodo(todo: Todo): Flow<Result<String>>

    suspend fun deleteTodo(todo: Todo): Flow<Result<String>>
}