package tn.esprit.pdm.utils

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.create

import tn.esprit.pdm.models.User

interface Apiuser {

    @POST("user/login")
    fun login(@Body user: User): Call<User>

    companion object {
        var BASE_URL = "http://10.0.2.2:9090/"
        fun create(): Apiuser {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()
            return retrofit.create(Apiuser::class.java)
        }
    }
}
