package tn.esprit.pdm.utils

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.create
import tn.esprit.pdm.models.LoginRequest

import tn.esprit.pdm.models.User
import java.util.concurrent.TimeUnit

interface Apiuser {

    @POST("user/login")
    fun seConnecter(@Body loginRequest: LoginRequest): Call<JsonObject>
    companion object {

        var BASE_URL = "http://192.168.139.1:9090/"

        fun create() : Apiuser {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Apiuser::class.java)
    }}
}