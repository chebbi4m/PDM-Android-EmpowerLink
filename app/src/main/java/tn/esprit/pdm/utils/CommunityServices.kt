package tn.esprit.gamer.utils

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import tn.esprit.pdm.models.CommunityDTO
import tn.esprit.pdm.uikotlin.community.CommunityActivity

interface CommunityServices {

    @POST("community/createCommunity")
    suspend fun createCommunity(@Body communityDTO: CommunityDTO): Response<Unit>

    //@PUT("community/togglePinCommunity")
    //fun togglePinCommunity(
        //@Body togglePinRequest: CommunityActivity.TogglePinRequest
    //): Response<Unit>
}

data class TogglePinRequest(
    val username: String,
    val communityId: Long
)
