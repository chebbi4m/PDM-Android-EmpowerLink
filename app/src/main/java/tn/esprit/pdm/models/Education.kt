package tn.esprit.pdm.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
data class Education (
    @SerializedName("_id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("description") val description : String,
    @SerializedName("dure") val dure : Number,
    @SerializedName("createdAt") val createdAt : Date,
    @SerializedName("updatedAt") val updatedAt : Date,
): Serializable