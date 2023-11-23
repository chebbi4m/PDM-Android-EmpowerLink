package tn.esprit.pdm.utils


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.http.GET
import tn.esprit.pdm.models.Education
import tn.esprit.pdm.models.Formation

interface ApiFormationEducation {

    @GET("/formation/all")
    fun getAllformations(): Call<MutableList<Formation>>

    @GET("/formation/")
    fun getFormationById(): Call<MutableList<Formation>>

    @POST("/formation/addParticipant")
    fun addParticipant(@Body info: AddParticipantRequest): Call<JsonObject>


    @GET("/education/all")
    fun getAllEducations(): Call<MutableList<Education>>
    companion object {

        var BASE_URL = "http://192.168.139.1:9090"

        fun create(): ApiFormationEducation {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiFormationEducation::class.java)
        }
    }
    data class AddParticipantRequest(
        val formationId: String,
        val userId: String
    )
}