package tn.esprit.pdm.models.request

import java.io.Serializable

data class Opportunite(

    val title: String?=null,
    val description: String?=null,
    val lieu: String?=null,
    val Typedecontrat: String?=null,
    val opportunityId:String?=null,
):Serializable