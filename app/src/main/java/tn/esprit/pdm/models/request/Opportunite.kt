package tn.esprit.pdm.models.request

import java.io.Serializable

data class Opportunite(

    val title: String,
    val description: String,
    val lieu: String?=null,
    val Typedecontrat: String?=null
):Serializable