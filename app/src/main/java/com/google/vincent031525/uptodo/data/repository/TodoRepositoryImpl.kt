package com.google.vincent031525.uptodo.data.repository

import com.google.vincent031525.uptodo.data.data_source.local.todo.TodoDao
import com.google.vincent031525.uptodo.data.data_source.remote.todo.TodoApi
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TodoRepositoryImpl(
    private val localDataSource: TodoDao,
    private val remoteDataSource: TodoApi,
    private val apiKey: String,
    private val token: String?,
    private val cookie: String?
) : TodoRepository {

    override fun getTodos(id: String) = flow {
        if (token == null) throw Exception("Token is null")
        if (cookie == null) throw Exception("Cookie is null")
        val response = remoteDataSource.getTodos(apiKey, token, cookie)
        when (response.code()) {
            200 -> {
                response.body()?.let { body ->
                    emit(Result.success(body.msg.map { it.toTodo() }))
                } ?: throw Exception("Response body is null")
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun insertTodo(todo: Todo) = flow {
        if (token == null) throw Exception("Token is null")
        if (cookie == null) throw Exception("Cookie is null")
        val response = remoteDataSource.insertTodo(apiKey, token, cookie, todo.toCreateRequest())
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    emit(Result.success(it.msg))
                } ?: throw Exception("Response body is null")
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateTodo(todo: Todo) = flow {
        if (token == null) throw Exception("Token is null")
        if (cookie == null) throw Exception("Cookie is null")
        val response =
            remoteDataSource.updateTodo(apiKey, token, cookie, todo.id, todo.toUpdateRequest())
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    emit(Result.success(""))
                }
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun doneTodo(todo: Todo) = flow {
        if (token == null) throw Exception("Token is null")
        if (cookie == null) throw Exception("Cookie is null")
        val response = remoteDataSource.doneTodo(apiKey, token, cookie, todo.id)
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    emit(Result.success(it.id))
                } ?: throw Exception("Response body is null")
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteTodo(todo: Todo) = flow {
        if (token == null) throw Exception("Token is null")
        if (cookie == null) throw Exception("Cookie is null")
        val response = remoteDataSource.deleteTodo(apiKey, token, cookie, todo.id)
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    emit(Result.success(it.msg))
                } ?: throw Exception("Response body is null")
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}