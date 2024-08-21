package com.ari.prodvizhenie.calendar.domain.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.domain.model.NetworkError
import com.ari.prodvizhenie.calendar.domain.model.OrganizationsResponse
import com.ari.prodvizhenie.calendar.domain.model.PostsResponse

interface PostRepository {

    suspend fun getOrganizations(token: String): Either<NetworkError, OrganizationsResponse>

    suspend fun sendToken(token: String, accessToken: String)

    suspend fun getPostByDateRange(
        token: String,
        date: Long,
        orgId: Int
    ): Either<NetworkError, PostsResponse>

}