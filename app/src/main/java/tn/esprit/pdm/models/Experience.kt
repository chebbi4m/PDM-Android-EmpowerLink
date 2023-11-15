package tn.esprit.pdm.models

import com.google.gson.annotations.SerializedName

data class Experience(
    @SerializedName("username") val creatorName: String = "",
    @SerializedName("title") val experienceTitle: String = "",
    @SerializedName("experienceId") val experienceId: String,
    @SerializedName("communityId") val communityId: String,
    var creatorImage: Int,
    @SerializedName("createdAt") val experienceDate: String,
    @SerializedName("text") val experienceText: String

)

