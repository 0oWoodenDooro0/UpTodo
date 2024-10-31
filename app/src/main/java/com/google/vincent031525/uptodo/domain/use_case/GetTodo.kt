package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTodo(private val repository: TodoRepository) {

    operator fun invoke(): Flow<List<Todo>> = flow {
        repository.getTodos("18501511458009948160").collect { result ->
            result.onSuccess {
                emit(it)
                Log.d("GetTodo", "invoke: $it")
            }.onFailure {
                emit(emptyList())
                Log.w("GetTodo", "invoke: ${it.printStackTrace()}")
            }
        }
    }.flowOn(Dispatchers.IO)
}