package com.google.vincent031525.uptodo.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.vincent031525.uptodo.domain.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToTodo: () -> Unit,
    navigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val usernameValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = usernameValue.value,
            onValueChange = { usernameValue.value = it },
            singleLine = true,
            modifier = Modifier.padding(8.dp),
            placeholder = { Text(text = "Username") })
        TextField(value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp),
            placeholder = { Text(text = "Password") })
        Button(
            onClick = {
                viewModel.login(User(usernameValue.value, passwordValue.value), onLoginSuccess = navigateToTodo)
            },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 50.dp)
        ) {
            Text(text = "Login")
        }
        Row {
            Text(text = "or ")
            Text(text = "Sign up", modifier = Modifier.clickable { navigateToRegister() })
        }
    }
}