package com.google.vincent031525.uptodo.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val usernameValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = usernameValue.value,
            onValueChange = { usernameValue.value = it },
            singleLine = true,
            modifier = Modifier.padding(8.dp),
            placeholder = { Text(text = "Username") }
        )
        TextField(
            value = passwordValue.value,
            onValueChange = { passwordValue.value = it },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(8.dp),
            placeholder = { Text(text = "Password") }
        )
        Button(
            onClick = { viewModel.register(User(usernameValue.value, passwordValue.value), navigateToLogin) },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 50.dp)
        ) {
            Text(text = "Register")
        }
    }
}