package tn.esprit.pdm.models.request


import java.io.Serializable


data class LoginRequest(
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var restCode: Int? = null,

) : Serializable