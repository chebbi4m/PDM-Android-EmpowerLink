package tn.esprit.pdm.models.request


import java.io.Serializable

class LoginRequest :Serializable{
    var email: String? = null
    var password: String? = null
    var username:String?=null

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
    constructor(email: String?, password: String?,username:String?) {
        this.email = email
        this.password = password
        this.username=username
    }
        constructor(email: String?) {
        this.email = email
    }

}