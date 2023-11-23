package tn.esprit.pdm.utils

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.pdm.models.CommunityDTO

interface CommunityServices {

    @POST("community/createCommunity")
    suspend fun createCommunity(@Body communityDTO: CommunityDTO): Response<Unit>
}
