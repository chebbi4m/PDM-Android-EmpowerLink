package tn.esprit.pdm.utils

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.esprit.pdm.models.Community
import tn.esprit.pdm.models.request.Opportunite

interface apiOpportunite {



    @POST("api/opportunite")
    fun addopp(@Body addopprtunite: Opportunite): Call<JsonObject>
    @GET("api/opportunite")
    fun getAllOpportunites(): Call<List<Opportunite>>
    @POST("api/apply")
    fun addParticipant(@Body info: AddParticipantRequest): Call<JsonObject>

    companion object {

        var BASE_URL = "http://10.0.2.2:9090/"

        fun create() : apiOpportunite {



            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(apiOpportunite::class.java)
        }

    }
    data class AddParticipantRequest(
        val opportunityId: String,
        val userId: String
    )
}