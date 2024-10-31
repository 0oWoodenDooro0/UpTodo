package com.google.vincent031525.uptodo.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.vincent031525.uptodo.R
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.ui.theme.UpTodoTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(modifier: Modifier = Modifier, viewModel: TodoViewModel = koinViewModel()) {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "Task") }) }, floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onAction(
                TodoViewModelAction.AddTodo(
                    Todo(
                        title = "New Task",
                        content = "New Task Content",
                        deadTime = "2024-08-26T00:00:00Z",
                        id = "1"
                    )
                )
            )
        }, modifier = modifier) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = null
            )
        }
    }) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            LazyColumn {
                items(viewModel.state.todos) { task ->
                    TodoView(
                        todo = task,
                        onComplete = { },
                        onDelete = { viewModel.onAction(TodoViewModelAction.DeleteTodo(it)) })
                }
            }
        }
    }
}

@Composable
fun TodoView(modifier: Modifier = Modifier, todo: Todo, onComplete: (Todo) -> Unit, onDelete: (Todo) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f)) {
                Text(text = todo.title, modifier = Modifier.padding(horizontal = 24.dp))
            }
            IconButton(onClick = { onComplete(todo) }) {
                Icon(painter = painterResource(R.drawable.baseline_check_24), contentDescription = null)
            }
            IconButton(onClick = { onDelete(todo) }) {
                Icon(painter = painterResource(R.drawable.outline_delete_24), contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    UpTodoTheme(darkTheme = true) {
        TodoScreen(viewModel = TodoViewModel(koinInject()))
    }
}