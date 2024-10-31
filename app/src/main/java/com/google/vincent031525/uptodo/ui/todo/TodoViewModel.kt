package com.google.vincent031525.uptodo.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.domain.use_case.TodoUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class TodoViewModelState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList(),
)

sealed interface TodoViewModelAction {
    data class AddTodo(val todo: Todo) : TodoViewModelAction
    data class UpdateTodo(val todo: Todo) : TodoViewModelAction
    data class DoneTodo(val todo: Todo) : TodoViewModelAction
    data class DeleteTodo(val todo: Todo) : TodoViewModelAction
    data object RefreshTodo : TodoViewModelAction
}

class TodoViewModel(private val todoUseCases: TodoUseCases) : ViewModel() {

    var state by mutableStateOf(TodoViewModelState())
        private set

    private var getTodosJob: Job? = null

    init {
        getTodos()
    }

    fun onAction(action: TodoViewModelAction) {
        when (action) {
            is TodoViewModelAction.AddTodo -> {
                viewModelScope.launch {
                    todoUseCases.addTodo(action.todo).collect { result ->
                        result.onSuccess {
                            getTodos()
                        }
                    }
                }
            }


            is TodoViewModelAction.UpdateTodo -> {
                viewModelScope.launch {
                    todoUseCases.updateTodo(action.todo).collect { result ->
                        result.onSuccess {
                            getTodos()
                        }
                    }
                }
            }

            is TodoViewModelAction.DoneTodo -> {
                viewModelScope.launch {
                    todoUseCases.doneTodo(action.todo).collect { result ->
                        result.onSuccess {
                            getTodos()
                        }
                    }
                }
            }

            is TodoViewModelAction.DeleteTodo -> {
                viewModelScope.launch {
                    todoUseCases.deleteTodo(action.todo).collect { result ->
                        result.onSuccess {
                            getTodos()
                        }
                    }
                }
            }

            TodoViewModelAction.RefreshTodo -> TODO()
        }
    }

    private fun getTodos() {
        getTodosJob?.cancel()
        getTodosJob = todoUseCases.getTodos().onEach {
            state = state.copy(todos = it)
        }.launchIn(viewModelScope)
    }
}