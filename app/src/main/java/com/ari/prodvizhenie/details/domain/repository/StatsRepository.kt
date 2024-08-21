package com.ari.prodvizhenie.details.domain.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.domain.model.NetworkError
import com.ari.prodvizhenie.details.domain.model.Statistics

interface StatsRepository {

    suspend fun getStatistics(id: Int, token: String): Either<NetworkError, Statistics>

}