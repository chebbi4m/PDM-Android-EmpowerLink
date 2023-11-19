package tn.esprit.pdm.uikotlin

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.auth0.android.jwt.JWT
import tn.esprit.pdm.uikotlin.home.HomePageActivity

class SessionManager(context: Context) {
     private val sharedPreferences : SharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
     private val editor : SharedPreferences.Editor = sharedPreferences.edit()

    private val IS_LOGGED_IN = "isLoggedIn"
    private val USER_ID = "userId"
    private val USER_EMAIL ="useremail"
    private val USER_NAME = "username"
    private val USER_DESCRIPTION ="description"
    private val  USER_SKILLS = "skills"
    private val USER_IMAGE = "image"
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
    fun setUserEmail(useremail: String) {
        editor.putString(USER_EMAIL, useremail)
        editor.apply()
    }
    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, "")
    }

    fun setUserName(username: String) {
        editor.putString(USER_NAME, username)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, "").toString()
    }
    fun setuSERDescription(description: String) {
        editor.putString(USER_DESCRIPTION, description)
        editor.apply()
    }

    fun getUserDescription(): String? {
        return sharedPreferences.getString(USER_DESCRIPTION, "").toString()
    }
    fun getToken(): String? {
        return sharedPreferences.getString(USER_ID, "").toString()
    }
    fun setUserSkills(skills: String) {
        editor.putString(USER_SKILLS, skills)
        editor.apply()
    }

    fun getUserSkills(): String? {
        return sharedPreferences.getString(USER_SKILLS, "").toString()
    }
    fun setUserImage(image: String) {
        editor.putString(USER_IMAGE, image)
        editor.apply()
    }

    fun getUserImage(): String? {
        return sharedPreferences.getString(USER_IMAGE, "").toString()
    }

     fun logout() {
        editor.clear()
        editor.apply()}



    fun decodeToken(token: String): DecodedToken {
        try {
            val jwt = JWT(token)

            // Extraire les informations du jeton
            val userId = jwt.getClaim("userId").asString()
            val username = jwt.getClaim("username").asString()
            val email = jwt.getClaim("email").asString()
            val role = jwt.getClaim("role").asString()
            val description = jwt.getClaim("description").asString()
            val skills = jwt.getClaim("skills").asString()
            val image = jwt.getClaim("image").asString()

            return DecodedToken(userId, username, email, role , description , skills ,image)
        } catch (e: Exception) {
            Log.e("TokenDecoder", "Erreur lors du décodage du token : ${e.message}")
            // Gérer l'erreur de décodage, renvoyer un objet vide ou null selon vos besoins
            return DecodedToken("", "", "", "","","","")
        }
    }
    data class DecodedToken(
        val userId: String?,
        val username: String?,
        val email: String?,
        val role: String?,
        val description: String?,
        val skills: String?,
        val image: String?,

    )
}