package com.google.vincent031525.uptodo.data.data_source.local.user

import android.content.Context
import android.content.SharedPreferences

class SharedPrefencesManager(context: Context) {

    companion object {
        private const val PREFENCE_NAME = "UserSharedPref"
        private lateinit var sharedPreferences: SharedPreferences
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

}