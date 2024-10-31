package com.google.vincent031525.uptodo.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.vincent031525.uptodo.domain.model.User
import com.google.vincent031525.uptodo.domain.use_case.RegisterUser
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUser: RegisterUser) : ViewModel() {

    fun register(user: User, onRegisterSuccess: () -> Unit) {
        viewModelScope.launch {
            registerUser(user).collect {
                it.onSuccess {
                    onRegisterSuccess()
                }
            }
        }
    }
}