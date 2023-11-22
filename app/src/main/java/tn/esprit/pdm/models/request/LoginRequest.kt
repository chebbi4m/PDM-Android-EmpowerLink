package tn.esprit.pdm.models.request


import java.io.Serializable


data class LoginRequest(

    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var restCode: Int? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var adress: String? = null,
    var number: String? = null,
    var userId:String? = null,
    var description:String?=null,
    var skills: List<String>? = null,
    var image: String? = null,
    var followers : List<String>? = null,
    ) : Serializable