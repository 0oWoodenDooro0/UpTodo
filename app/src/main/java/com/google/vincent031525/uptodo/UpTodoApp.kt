package com.google.vincent031525.uptodo

import android.app.Application
import com.google.vincent031525.uptodo.di.apiModule
import com.google.vincent031525.uptodo.di.databaseModule
import com.google.vincent031525.uptodo.di.repositoryModule
import com.google.vincent031525.uptodo.di.sharedPrefencesModule
import com.google.vincent031525.uptodo.di.useCaseModule
import com.google.vincent031525.uptodo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class UpTodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@UpTodoApp)
            modules(
                viewModelModule,
                databaseModule,
                apiModule,
                sharedPrefencesModule,
                repositoryModule,
                useCaseModule
            )
        }
    }
}