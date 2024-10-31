package com.google.vincent031525.uptodo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.vincent031525.uptodo.ui.login.LoginScreen
import com.google.vincent031525.uptodo.ui.register.RegisterScreen
import com.google.vincent031525.uptodo.ui.todo.TodoScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN_ROUTE,
        modifier = modifier
    ) {
        composable(Destinations.LOGIN_ROUTE) {
            LoginScreen(
                navigateToTodo = { navController.navigate(Destinations.TODO_ROUTE){
                    popUpTo(Destinations.LOGIN_ROUTE){
                        inclusive = true
                    }
                    launchSingleTop = true
                } },
                navigateToRegister = { navController.navigate(Destinations.REGISTER_ROUTE) })
        }
        composable(Destinations.REGISTER_ROUTE) {
            RegisterScreen(navigateToLogin = { navController.navigateUp() })
        }
        composable(Destinations.TODO_ROUTE) { TodoScreen() }
    }
}