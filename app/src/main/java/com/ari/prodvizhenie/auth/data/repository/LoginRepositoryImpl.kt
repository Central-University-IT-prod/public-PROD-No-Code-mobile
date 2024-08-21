package com.ari.prodvizhenie.auth.data.repository

import arrow.core.Either
import com.ari.prodvizhenie.auth.data.mapper.toNetworkError
import com.ari.prodvizhenie.auth.data.remote.LoginApi
import com.ari.prodvizhenie.auth.domain.model.LoginResponse
import com.ari.prodvizhenie.auth.domain.model.NetworkError
import com.ari.prodvizhenie.auth.domain.model.UserLoginInfo
import com.ari.prodvizhenie.auth.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginApi: LoginApi
) : LoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Either<NetworkError, LoginResponse> {
        return Either.catch {
            loginApi.loginUser(
                type = "application/json",
                UserLoginInfo(
                    username = username,
                    password = password
                )
            )
        }.mapLeft {
            it.printStackTrace()
            it.toNetworkError()
        }
    }


}
