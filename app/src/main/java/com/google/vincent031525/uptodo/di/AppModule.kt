package com.google.vincent031525.uptodo.di

import android.app.Application
import androidx.room.Room
import com.google.vincent031525.uptodo.BuildConfig
import com.google.vincent031525.uptodo.data.data_source.local.todo.TodoDao
import com.google.vincent031525.uptodo.data.data_source.local.todo.TodoDatabase
import com.google.vincent031525.uptodo.data.data_source.local.user.SharedPrefencesManager
import com.google.vincent031525.uptodo.data.data_source.remote.todo.TodoApi
import com.google.vincent031525.uptodo.data.data_source.remote.user.UserApi
import com.google.vincent031525.uptodo.data.repository.TodoRepositoryImpl
import com.google.vincent031525.uptodo.data.repository.UserRepositoryImpl
import com.google.vincent031525.uptodo.domain.repository.TodoRepository
import com.google.vincent031525.uptodo.domain.repository.UserRepository
import com.google.vincent031525.uptodo.domain.use_case.AddTodo
import com.google.vincent031525.uptodo.domain.use_case.DeleteTodo
import com.google.vincent031525.uptodo.domain.use_case.DoneTodo
import com.google.vincent031525.uptodo.domain.use_case.GetTodo
import com.google.vincent031525.uptodo.domain.use_case.LoginUser
import com.google.vincent031525.uptodo.domain.use_case.RegisterUser
import com.google.vincent031525.uptodo.domain.use_case.TodoUseCases
import com.google.vincent031525.uptodo.domain.use_case.UpdateTodo
import com.google.vincent031525.uptodo.ui.login.LoginViewModel
import com.google.vincent031525.uptodo.ui.register.RegisterViewModel
import com.google.vincent031525.uptodo.ui.todo.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module {
    factory { TodoViewModel(get()) }
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
}

fun provideDataBase(application: Application): TodoDatabase = Room.databaseBuilder(
    application, TodoDatabase::class.java, TodoDatabase.DATABASE_NAME
).fallbackToDestructiveMigration().build()

fun provideDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.todoDao

fun provideTodoApi() =
    Retrofit.Builder().baseUrl("https://todo-plvv.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TodoApi::class.java)

fun provideUserApi() =
    Retrofit.Builder().baseUrl("https://todo-plvv.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(UserApi::class.java)

fun provideApiKey() = BuildConfig.API_KEY

val databaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}

val apiModule = module {
    single { provideTodoApi() }
    single { provideUserApi() }
    single(named("apiKey")) { provideApiKey() }
    single(named("token")) {
        SharedPrefencesManager(get()).getString("token")?.let { "Bearer $it" }
    }
    single(named("cookie")) { SharedPrefencesManager(get()).getString("id")?.let { "id=$it" } }
}

val sharedPrefencesModule = module {
    single { SharedPrefencesManager(androidContext()) }
}

val repositoryModule = module {
    factory<TodoRepository> {
        TodoRepositoryImpl(
            get(), get(), get(named("apiKey")), get(named("token")), get(named("cookie"))
        )
    }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get(named("apiKey"))) }
}

val useCaseModule = module {
    factory { AddTodo(get()) }
    factory { GetTodo(get()) }
    factory { UpdateTodo(get()) }
    factory { DoneTodo(get()) }
    factory { DeleteTodo(get()) }
    factory { TodoUseCases(get(), get(), get(), get(), get()) }
    factory { LoginUser(get()) }
    factory { RegisterUser(get()) }
}