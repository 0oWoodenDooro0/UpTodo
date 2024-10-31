package com.google.vincent031525.uptodo.data.data_source.local.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    val title: String,
    val content: String,
    val deadTime: String,
    val done: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
