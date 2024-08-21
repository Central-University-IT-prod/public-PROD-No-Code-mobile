package com.ari.prodvizhenie.details.data.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.data.mapper.toNetworkError
import com.ari.prodvizhenie.auth.domain.model.NetworkError
import com.ari.prodvizhenie.details.data.remote.StatApi
import com.ari.prodvizhenie.details.domain.model.Statistics
import com.ari.prodvizhenie.details.domain.repository.StatsRepository

class StatsRepositoryImpl(
    private val statApi: StatApi
) : StatsRepository {

    override suspend fun getStatistics(id: Int, token: String): Either<NetworkError, Statistics> {
        return Either.catch {
            statApi.getStats(id, "Bearer $token").data[0].statistics
        }.mapLeft {
            it.toNetworkError()
        }
    }


}