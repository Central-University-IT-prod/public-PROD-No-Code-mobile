package com.ari.prodvizhenie.calendar.data.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.data.mapper.toNetworkError
import com.ari.prodvizhenie.auth.domain.model.NetworkError
import com.ari.prodvizhenie.calendar.data.remote.PostApi
import com.ari.prodvizhenie.calendar.domain.model.OrganizationsResponse
import com.ari.prodvizhenie.calendar.domain.model.PostsResponse
import com.ari.prodvizhenie.calendar.domain.repository.PostRepository
import java.time.Instant
import java.time.ZoneOffset

class PostRepositoryImpl(
    private val postApi: PostApi
) : PostRepository {

    override suspend fun getOrganizations(token: String): Either<NetworkError, OrganizationsResponse> {
        return Either.catch {
            postApi.getUserOrganizations(type = "application/json", accessToken = "Bearer $token")
        }.mapLeft {
            it.toNetworkError()
        }
    }

    override suspend fun sendToken(token: String, accessToken: String) {
        postApi.sendToken(token = token, accessToken = accessToken)
    }

    override suspend fun getPostByDateRange(
        token: String,
        date: Long,
        orgId: Int
    ): Either<NetworkError, PostsResponse> {
        return Either.catch {
            postApi.getPosts(
                accessToken = token,
                fromDate = getStartOfDay(date) * 1000 - 10800000,
                toDate = getEndOfDay(date) * 1000 - 10800000,
                orgId = orgId
            )
        }.mapLeft {
            it.toNetworkError()
        }
    }

    private fun getStartOfDay(unixTime: Long): Long {
        val instant = Instant.ofEpochSecond(unixTime)
        val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
        return localDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
    }

    // Функция для получения Unix-времени конца дня (23:59:59 UTC) по заданному Unix-времени
    private fun getEndOfDay(unixTime: Long): Long {
        val instant = Instant.ofEpochSecond(unixTime)
        val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
        return localDate.atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC)
    }


}