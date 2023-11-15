package tn.esprit.pdm.models

import tn.esprit.pdm.R

data class Community(
    val id: Long,
    val image: Int,
    val title: String,
    val category: String,
    val username: String
)

data class CommunityDTO(
    var communityId: Long,
    var image: Int,
    var name: String,
    var category: String,
    var objectif: String,
    var username: String
)


// Assuming these variables are defined somewhere in your code
val name: String = "CommunityName"
val username: String = "Username"
val category: String = "Category"
val objectif: String = "Objectif"

// Update the community instance creation with the correct parameter names
val community = Community(id = 1, image = R.drawable.profile, title = name, category = category, username = username)

val communityDTO = CommunityDTO(communityId = 1, image = R.drawable.profile, name = name, category = category, objectif = objectif, username = username)