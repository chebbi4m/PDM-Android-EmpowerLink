    package tn.esprit.pdm.utils

    import com.google.gson.JsonElement
    import com.google.gson.JsonObject
    import retrofit2.Call
    import retrofit2.converter.gson.GsonConverterFactory
    import retrofit2.http.Body
    import retrofit2.http.POST
    import retrofit2.Retrofit
    import retrofit2.http.GET
    import retrofit2.http.PUT
    import retrofit2.http.Path
    import okhttp3.MultipartBody
    import okhttp3.OkHttpClient
    import okhttp3.RequestBody
    import okhttp3.logging.HttpLoggingInterceptor
    import retrofit2.http.Multipart
    import retrofit2.http.Part


    import retrofit2.http.Query
    import tn.esprit.pdm.models.request.LoginRequest
    import tn.esprit.pdm.uikotlin.skills.SkillsAdapter

    interface Apiuser {
        @POST("user/resetcode")
        fun resetCode(@Body resetcodeRequest: LoginRequest): Call<JsonElement>

        @POST("user/login")
        fun seConnecter(@Body loginRequest: LoginRequest): Call<JsonObject>
        @POST("user/register")
        fun sInscrire(@Body signupRequest: LoginRequest): Call<JsonObject>
        @POST("user/ForgetPassword")
        fun sendPasswordResetCode(@Body resetPasswordRequest: LoginRequest): Call<JsonElement>
        @POST("user/changerpassword")
        fun changerPassword(@Body changerPasswordRequest: LoginRequest): Call<JsonElement>
        @PUT("user/editprofile")
        fun editprofile(@Body editProfileRequest: LoginRequest): Call<JsonObject>
        @GET("user/skills/{userId}")
        fun getSkills(@Path("userId") userId: String): Call<JsonObject>
        @GET("user/search")
        fun searchUsersByName(@Query("name") name: String): Call<List<LoginRequest>>
        @Multipart
        @POST("user/updateprofilephoto/{userId}")
        fun updateProfilePhoto(
            @Path("userId") userId: String,
            @Part file: MultipartBody.Part
        ): Call<LoginRequest>
        @POST("user/addskills")
        fun addSkills(@Body addSkillsRequest: LoginRequest): Call<JsonObject>
        @GET("user/get/{username}")
        fun getUserByName(@Path("username") username: String): Call<LoginRequest>
        @POST("user/users/follow")
        fun followUser(@Body followRequest: LoginRequest): Call<JsonObject>
        @GET("user/getuser")
        fun getAllUsers(): Call<List<JsonObject>?>
        @GET("user/countFollowers/{userId}")
        fun countFollowers(@Path("userId") userId: String): Call<JsonObject>

        @GET("user/countFollowing/{userId}")
        fun countFollowing(@Path("userId") userId: String): Call<JsonObject>

        @POST("user/verifygoogle")
        suspend fun signinGoogle (@Body googleToken: JsonObject) : JsonObject



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