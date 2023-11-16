package tn.esprit.pdm.models

import com.google.gson.annotations.SerializedName
import tn.esprit.pdm.R

data class Experience(
    @SerializedName("username") val creatorName: String = "",
    @SerializedName("title") val experienceTitle: String = "",
    @SerializedName("experienceId") val experienceId: String,
    @SerializedName("communityId") val communityId: String,
    var creatorImage: Int = R.drawable.profile, // Default value
    @SerializedName("createdAt") val experienceDate: String,
    @SerializedName("text") val experienceText: String
) {
    // Secondary constructor
    constructor(username: String, title: String, communityId: String, experienceDate: String, experienceText: String)
            : this(username, title, "", communityId, R.drawable.profile, experienceDate, experienceText)
}