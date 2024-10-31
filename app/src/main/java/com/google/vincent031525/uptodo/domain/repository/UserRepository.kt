package com.google.vincent031525.uptodo.domain.repository

import com.google.vincent031525.uptodo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(user: User): Flow<Result<String>>

    suspend fun register(user: User): Flow<Result<String>>
}