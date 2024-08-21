package com.ari.prodvizhenie.details.data.remote

import com.ari.prodvizhenie.details.domain.model.StatsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StatApi {

    @GET("post/{id}/statistics")
    suspend fun getStats(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): StatsResponse
}