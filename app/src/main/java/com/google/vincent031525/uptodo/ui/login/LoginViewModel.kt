package com.google.vincent031525.uptodo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.vincent031525.uptodo.domain.model.User
import com.google.vincent031525.uptodo.domain.use_case.LoginUser
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUser: LoginUser) : ViewModel() {

    fun login(user: User, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            loginUser(user).collect {
                it.onSuccess {
                    onLoginSuccess()
                }
            }
        }
    }
}