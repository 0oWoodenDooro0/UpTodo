package com.google.vincent031525.uptodo.ui.todo

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerSelectionMode
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.gson.internal.bind.util.ISO8601Utils
import com.google.vincent031525.uptodo.R
import com.google.vincent031525.uptodo.domain.model.Todo
import com.google.vincent031525.uptodo.ui.theme.UpTodoTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = koinViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    var showAddTodoView by remember { mutableStateOf(false) }
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "Todo") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddTodoView = true }) {
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
        if (showAddTodoView) {
            BottomSheet(sheetState = sheetState,
                onDismissRequest = { showAddTodoView = false },
                onAddButtonClick = { title, content, date ->
                    viewModel.onAction(
                        TodoViewModelAction.AddTodo(
                            Todo(
                                id = "0",
                                title = title,
                                content = content,
                                deadTime = ISO8601Utils.format(date)
                            )
                        )
                    )
                    showAddTodoView = false
                })
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

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onAddButtonClick: (String, String, Date) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance(TimeZone.getDefault()).apply {
        set(Calendar.MINUTE, 0)
    }
    val selectedTime by remember { mutableStateOf(currentTime) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.timeInMillis
    )
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY), is24Hour = true
    )
    val timeFormat = SimpleDateFormat("HH:mm")
    val dateFormat = SimpleDateFormat("EEE, MM dd, yyyy")
    ModalBottomSheet(
        sheetState = sheetState, onDismissRequest = onDismissRequest
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Add Todo",
                modifier = Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            OutlinedTextField(value = title,
                onValueChange = { title = it },
                singleLine = true,
                placeholder = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = "title cannot be empty.")
                    }
                })
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                singleLine = true,
                placeholder = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.time_24),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = dateFormat.format(selectedTime.time),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePicker = true }
                        .padding(vertical = 8.dp))
                Text(text = timeFormat.format(selectedTime.time),
                    modifier = Modifier
                        .clickable { showTimePicker = true }
                        .padding(vertical = 8.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                    if (title.isNotEmpty()) {
                        onAddButtonClick(title, content, selectedTime.time)
                    } else {
                        isError = true
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.send_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialog(onDismissRequest = { showDatePicker = false },
            datePickerState,
            onCancelClick = {
                datePickerState.selectedDateMillis = selectedTime.timeInMillis
                showDatePicker = false
            },
            onConfirmClick = {
                datePickerState.selectedDateMillis?.let {
                    selectedTime.timeInMillis = it
                }
                showDatePicker = false
            })
    }
    if (showTimePicker) {
        TimePickerDialog(onDismissRequest = { showTimePicker = false },
            timePickerState,
            onCancelClick = {
                timePickerState.apply {
                    hour = selectedTime.get(Calendar.HOUR_OF_DAY)
                    minute = selectedTime.get(Calendar.MINUTE)
                    selection = TimePickerSelectionMode.Hour
                }
                showTimePicker = false
            },
            onConfirmClick = {
                selectedTime.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                selectedTime.set(Calendar.MINUTE, timePickerState.minute)
                timePickerState.selection = TimePickerSelectionMode.Hour
                showTimePicker = false
            })
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    datePickerState: DatePickerState,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DatePicker(state = datePickerState, modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancelClick, modifier = Modifier.padding(8.dp)) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = onConfirmClick, modifier = Modifier.padding(8.dp)) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    timePickerState: TimePickerState,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
        ) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TimePicker(
                    state = timePickerState, modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 24.dp, bottom = 8.dp
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancelClick, modifier = Modifier.padding(8.dp)) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = onConfirmClick, modifier = Modifier.padding(8.dp)) {
                        Text(text = "OK")
                    }
                }
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
