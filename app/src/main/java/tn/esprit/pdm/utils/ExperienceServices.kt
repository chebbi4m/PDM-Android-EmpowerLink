package tn.esprit.pdm.utils


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.pdm.models.ApiResponse
import tn.esprit.pdm.models.Experience

interface ExperienceServices {

    @POST("experience/createExperience")
    suspend fun createExperience(@Body experience: Experience): Response<ApiResponse<String>>
}

