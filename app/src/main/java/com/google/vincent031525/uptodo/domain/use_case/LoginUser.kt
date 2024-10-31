package com.google.vincent031525.uptodo.domain.use_case

import android.util.Log
import com.google.vincent031525.uptodo.domain.model.User
import com.google.vincent031525.uptodo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUser(private val repository: UserRepository) {

    operator fun invoke(user: User) = flow {
        repository.login(user).collect { result ->
            result.onSuccess {
                emit(result)
                Log.d("LoginUser", "invoke: $it")
            }.onFailure {
                emit(result)
                Log.e("LoginUser", "invoke: ${it.printStackTrace()}")
            }
        }
    }.flowOn(Dispatchers.IO)
}