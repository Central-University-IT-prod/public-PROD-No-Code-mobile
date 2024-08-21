package com.ari.prodvizhenie.auth.domain.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.domain.model.LoginResponse
import com.ari.prodvizhenie.auth.domain.model.NetworkError

interface LoginRepository {

    suspend fun login(username: String, password: String): Either<NetworkError, LoginResponse>

}