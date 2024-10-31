package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.flow

class AddTodo(private val repository: TodoRepository) {

    operator fun invoke(todo: Todo) = flow {
        repository.insertTodo(todo).collect { result ->
            result.onSuccess { todoDto ->
                emit(result.map { it.toTodo() })
                Log.d("AddTodo", "invoke: $todoDto")
            }.onFailure {
                emit(result)
                Log.e("AddTodo", "invoke: ${it.printStackTrace()}")
            }
        }
    }
}