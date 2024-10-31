package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.flow

class UpdateTodo(private val repository: TodoRepository) {

    operator fun invoke(todo: Todo) = flow {
        repository.updateTodo(todo).collect { result ->
            result.onSuccess {
                emit(result)
                Log.d("UpdateTodo", "invoke: Success")
            }.onFailure {
                emit(result)
                Log.e("UpdateTodo", "invoke: ${it.printStackTrace()}")
            }
        }
    }
}