package com.google.vincent031525.uptodo.data.repository

import com.google.vincent031525.uptodo.data.data_source.local.user.SharedPrefencesManager
import com.google.vincent031525.uptodo.data.data_source.remote.user.UserApi
import com.google.vincent031525.uptodo.domain.model.User
import com.google.vincent031525.uptodo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val sharedPrefences: SharedPrefencesManager,
    private val remoteDataSource: UserApi,
    private val apiKey: String
) : UserRepository {

    override suspend fun login(user: User) = flow {
        val response = remoteDataSource.loginUser(apiKey, user.toUserRequest())
        when (response.code()) {
            200 -> {
                response.body()?.let {
                    sharedPrefences.saveString("id", it.data.ID)
                    sharedPrefences.saveString("token", it.token.AccessToken)
                    emit(Result.success(it.msg))
                } ?: throw Exception("Response body is null")
            }

            else -> {
                throw Exception("Response code is not 200, ${response.code()}")
            }
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override suspend fun register(user: User) = flow {
        val response = remoteDataSource.registerUser(apiKey, user.toUserRequest())
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
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}