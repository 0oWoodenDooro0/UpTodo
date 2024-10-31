package com.google.vincent031525.uptodo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.vincent031525.uptodo.ui.theme.UpTodoTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpTodoTheme {
                KoinAndroidContext {
                    NavGraph()
                }
            }
        }
    }
}

