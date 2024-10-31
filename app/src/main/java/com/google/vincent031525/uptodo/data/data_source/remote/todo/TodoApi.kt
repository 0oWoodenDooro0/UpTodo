package com.google.vincent031525.uptodo.data.data_source.remote.todo

import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.CreateTodoRequest
import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.CreateTodoResponse
import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.DeleteTodoResponse
import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.GetTodosResponse
import com.google.vincent031525.uptodo.data.data_source.remote.todo.model.TodoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

    @GET("/todo")
    suspend fun getTodos(
        @Header("API-KEY") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Cookie") cookie: String
    ): Response<GetTodosResponse>

    @POST("/todo/create")
    suspend fun insertTodo(
        @Header("API-KEY") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Cookie") cookie: String,
        @Body todo: CreateTodoRequest
    ): Response<CreateTodoResponse>

    @PUT("/todo/{id}")
    suspend fun updateTodo(
        @Path("id") id: String, @Body todo: TodoDto
    )

    @DELETE("/todo/{id}")
    suspend fun deleteTodo(
        @Header("API-KEY") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Cookie") cookie: String,
        @Path("id") id: String
    ): Response<DeleteTodoResponse>
}