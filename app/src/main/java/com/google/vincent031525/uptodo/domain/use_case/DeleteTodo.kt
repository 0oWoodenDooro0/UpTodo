package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteTodo(private val repository: TodoRepository) {

    operator fun invoke(todo: Todo) = flow {
        repository.deleteTodo(todo).collect { result ->
            result.onSuccess {
                emit(result)
                Log.d("DeleteTodo", "invoke: $it")
            }.onFailure {
                emit(result)
                Log.e("DeleteTodo", "invoke: ${it.printStackTrace()}")
            }
        }
    }.flowOn(Dispatchers.IO)
}