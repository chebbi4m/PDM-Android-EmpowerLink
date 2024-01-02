
package tn.esprit.pdm.models.request

import java.io.Serializable


data class Opportunite(

    val imageUrl: String? = null,
    val skill: String? = null,
    val contactEmail: String? = null,
    val salary: String? = null,
    val nomEntreprise: String? = null,
    val title: String? = null,
    val description: String? = null,
    val lieu: String? = null,
    val Typedecontrat: String? = null,
    val opportunityId:String?=null,
    val applicants: List<String> = emptyList()
) : Serializable

