package tn.esprit.pdm.utils

import retrofit2.http.GET
import retrofit2.http.Path
import tn.esprit.pdm.models.Community
import tn.esprit.pdm.models.Experience

interface ApiService {
    @GET("community/getAllCommunities/")
    suspend fun getAllCommunities(): List<Community>

    @GET("experience/getExperiencesByCommunity/{communityId}")
    suspend fun getExperiencesByCommunity(@Path("communityId") communityId: Long): List<Experience>
}

