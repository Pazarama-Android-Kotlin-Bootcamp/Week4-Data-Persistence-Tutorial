package com.merttoptas.datapersistencetutorial.data.local

import android.content.Context

/**
 * Created by merttoptas on 9.10.2022.
 */

class ClientPreferences(context: Context) {
    private val PREFS_FILE_NAME = "client_preferences"
    private val prefs = context.getSharedPreferences(PREFS_FILE_NAME, 0)

    companion object {
        const val KEY_USER_AGE = "KEY_USER_AGE"
        const val KEY_USER_NAME = "KEY_USER_NAME"
    }

    fun setUserName(name: String) {
        prefs.edit().putString(KEY_USER_NAME, name).apply()
    }

    fun getUserName(): String? {
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun setUserAge(age: Int) {
        prefs.edit().putInt(KEY_USER_AGE, age).apply()
    }

    fun getUserAge(): Int {
        return prefs.getInt(KEY_USER_AGE, 0)
    }

}