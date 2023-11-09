package tn.esprit.pdm.uikotlin

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
     private val sharedPreferences : SharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
     private val editor : SharedPreferences.Editor = sharedPreferences.edit()

    private val IS_LOGGED_IN = "isLoggedIn"
    private val USER_ID = "userId"

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun setUserId(userId: String) {
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, "")
    }

     fun logout() {
        editor.clear()
        editor.apply()}
}