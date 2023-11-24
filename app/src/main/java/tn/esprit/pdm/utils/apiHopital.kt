package tn.esprit.pdm.utils

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tn.esprit.pdm.models.Servicesoc

interface apiHopital {
    @POST("service/add")
    fun addhop(@Body add:Servicesoc): Call<JsonObject>
    @GET("service/all") // Remplacez "/all" par le chemin correct de votre API
    fun getHopitaux(): Call<List<Servicesoc>>
    @GET("service/{id}")
    fun getHopitalDetails(@Path("id") hopitalId: String): Call<Servicesoc>

    companion object {

        var BASE_URL = "http://192.168.139.1:9090/"

        fun create() : apiHopital {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(apiHopital::class.java)
        }}
}