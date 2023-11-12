package tn.esprit.pdm.models

data class Community(
    var id: Long,
    var communityImage: Int,
    var communityTitle: String,
    var communityCategory: String,
    var communityObjectif: String
)
