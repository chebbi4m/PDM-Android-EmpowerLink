package tn.esprit.pdm.models

import tn.esprit.pdm.R

data class Community(
    val communityId: Int,
    val image: Int,
    val title: String,
    val objectif: String,
    val category: String,
    val username: String,
    val pending: List<String> = emptyList(),
    val members: List<String> = emptyList(),
    val pinned: List<String> = emptyList()
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
val community = Community(
    communityId = 1,
    image = R.drawable.profile,
    title = "CommunityName",
    objectif = "Objectif",
    category = "Category",
    username = "Username"
)


val communityDTO = CommunityDTO(communityId = 1, image = R.drawable.profile, name = name, category = category, objectif = objectif, username = username)