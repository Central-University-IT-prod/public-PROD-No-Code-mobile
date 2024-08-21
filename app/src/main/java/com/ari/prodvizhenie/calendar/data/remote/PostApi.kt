package com.ari.prodvizhenie.calendar.data.remote

import com.ari.prodvizhenie.calendar.domain.model.OrganizationsResponse
import com.ari.prodvizhenie.calendar.domain.model.PostsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {

    @GET("user/me/organizations")
    suspend fun getUserOrganizations(
        @Header("Accept") type: String,
        @Header("Authorization") accessToken: String
    ): OrganizationsResponse

    @POST("mobile/save-token")
    suspend fun sendToken(
        @Header("Authorization") accessToken: String,
        @Body token: String
    )

    @GET("post")
    suspend fun getPosts(
        @Header("Authorization") accessToken: String,
        @Query("fromDate") fromDate: Long,
        @Query("toDate") toDate: Long,
        @Query("organizationId") orgId: Int,
    ): PostsResponse

}