package tn.esprit.pdm.utils

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import tn.esprit.pdm.models.request.LoginRequest

interface Apiuser {

    @POST("user/login")
    fun seConnecter(@Body loginRequest: LoginRequest): Call<JsonObject>
    @POST("user/register")
    fun sInscrire(@Body signupRequest: LoginRequest): Call<JsonObject>
    @POST("user/ForgetPassword")
    fun sendPasswordResetCode(@Body resetPasswordRequest: LoginRequest): Call<JsonElement>
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