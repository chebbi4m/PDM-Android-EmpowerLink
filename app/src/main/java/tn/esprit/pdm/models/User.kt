package tn.esprit.pdm.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
    @SerializedName("_id")
    var id: String,
    @SerializedName("username") val username: String,
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("address") val address: String?,
    @SerializedName("number") val number: Int?,
    @SerializedName("birthday") val birthday: Date?,
    @SerializedName("image") val image: String?,
    @SerializedName("role") val role: String = "user",
    @SerializedName("banned") val banned: String = "active",
    @SerializedName("banduration") val banduration: String = "",
    @SerializedName("reason") val reason: String?,
    @SerializedName("verified") val verified: Boolean = false,
    @SerializedName("resetCode") val resetCode: String?
) {

}