package com.google.vincent031525.uptodo.ui.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun TodoScreen(viewModel: TodoViewModel = koinViewModel()) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "Task") }) },
        floatingActionButton = {
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
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn {
                val size = viewModel.state.todos.size
                itemsIndexed(viewModel.state.todos) { index, task ->
                    val modifier = when (index) {
                        0 -> {
                            Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        }

                        size - 1 -> {
                            Modifier.clip(
                                RoundedCornerShape(
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )
                            )
                        }

                        else -> {
                            Modifier
                        }
                    }
                    TodoView(
                        modifier = modifier,
                        todo = task,
                        onComplete = { viewModel.onAction(TodoViewModelAction.DoneTodo(it)) },
                        onDelete = { viewModel.onAction(TodoViewModelAction.DeleteTodo(it)) })
                }
            }
        }
    }
}

@Composable
fun TodoView(
    modifier: Modifier = Modifier,
    todo: Todo,
    onComplete: (Todo) -> Unit,
    onDelete: (Todo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .then(modifier)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(onClick = { onComplete(todo) }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(onClick = { onDelete(todo) }) {
                Icon(
                    painter = painterResource(R.drawable.outline_delete_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    UpTodoTheme(darkTheme = false) {
        TodoScreen(viewModel = TodoViewModel(koinInject()))
    }
}