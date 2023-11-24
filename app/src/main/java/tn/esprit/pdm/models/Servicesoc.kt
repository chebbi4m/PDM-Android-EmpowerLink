package tn.esprit.pdm.models

import java.io.Serializable

class Servicesoc(
    var nom: String? = null,
    var description: String? = null,
    var lieu: String? = null,
   var  nbParticipant:String?=null,
    val attitude: String? = null,
    val longitude: String? = null
) : Serializable