package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.flow

class DoneTodo(private val repository: TodoRepository) {

    operator fun invoke(todo: Todo) = flow {
        repository.doneTodo(todo).collect { result ->
            result.onSuccess {
                emit(result)
                Log.d("DoneTodo", "invoke: $it")
            }.onFailure {
                emit(result)
                Log.e("DoneTodo", "invoke: ${it.printStackTrace()}")
            }
        }
    }
}