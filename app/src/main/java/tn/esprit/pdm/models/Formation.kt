package tn.esprit.pdm.models
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
data class Formation (
    @SerializedName("_id") val _id : String,
    @SerializedName("title") val title : String,
    @SerializedName("nbPlace") val nbPlace : Int,
    @SerializedName("nbParticipant") val nbParticipant : Int,
    @SerializedName("description") val description : String,
    @SerializedName("image") val image : String,
    @SerializedName("participants") val participants : MutableList<String>,
    @SerializedName("createdAt") val createdAt : Date,
    @SerializedName("updatedAt") val updatedAt : Date,
    ): Serializable