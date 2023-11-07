package tn.esprit.pdm.utils

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.pdm.models.User
import java.util.concurrent.TimeUnit

interface Apiuser {
    @POST("user/register")
    fun SignIn(@Body info: RequestBody): Call<User>

    @POST("user/login")
    fun seConnecter(@Body info: RequestBody): Call<User>
    companion object {

        var BASE_URL = "http://127.0.0.1:9090/"

        fun create() : Apiuser {
            val httpClient = OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(Apiuser::class.java)
        }
    }
}