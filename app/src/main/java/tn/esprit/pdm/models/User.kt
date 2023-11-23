package tn.esprit.pdm.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class User(

    var id: String,
   val username: String?,
val firstname: String?,
   val lastname: String?,
val email: String,
    val password: String,
   val address: String?,
  val number: Int?,
     val birthday: Date?,
  val image: String?,
     val role: String = "user",
     val banned: String = "active",
    val banduration: String = "",
    val reason: String?,
   val verified: Boolean = false,
 val resetCode: String?
): Serializable