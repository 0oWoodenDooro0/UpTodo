package com.google.vincent031525.uptodo.ui.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
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
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "Todo") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onAction(
                    TodoViewModelAction.AddTodo(
                        Todo(
                            title = "New Todo",
                            content = "New Todo Content",
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
            TodoListView(list = viewModel.state.todos,
                onComplete = { viewModel.onAction(TodoViewModelAction.DoneTodo(it)) },
                onDelete = { viewModel.onAction(TodoViewModelAction.DeleteTodo(it)) })
            TodoCompleteListView(viewModel.state.completeTodos)
        }
    }
}

@Composable
fun TodoListView(list: List<Todo>, onComplete: (Todo) -> Unit, onDelete: (Todo) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        val size = list.size
        if (size != 0) {
            item { TodoHeaderView() }
            items(size) { index ->
                TodoView(
                    todo = list[index], onComplete = onComplete, onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun TodoHeaderView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Todo List",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun TodoView(
    todo: Todo, onComplete: (Todo) -> Unit, onDelete: (Todo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
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

@Composable
fun TodoCompleteListView(list: List<Todo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        val size = list.size
        if (size != 0) {
            item { TodoCompleteHeaderView() }
            items(size) { index -> TodoCompleteView(todo = list[index]) }
        }
    }
}

@Composable
fun TodoCompleteHeaderView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Completed",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun TodoCompleteView(todo: Todo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.baseline_check_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textDecoration = TextDecoration.LineThrough
                    )
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