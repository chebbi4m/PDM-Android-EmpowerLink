package tn.esprit.pdm.models

class LoginRequest {
    var email: String? = null
    var password: String? = null

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
}