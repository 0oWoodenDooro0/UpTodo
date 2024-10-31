package com.google.vincent031525.uptodo.data.data_source.local.todo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "todos_db"
    }
}