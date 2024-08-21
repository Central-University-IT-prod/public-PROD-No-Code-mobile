package com.ari.prodvizhenie.auth.data.remote

import com.ari.prodvizhenie.auth.domain.model.LoginResponse
import com.ari.prodvizhenie.auth.domain.model.UserLoginInfo
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/sign-in")
    suspend fun loginUser(
        @Header("Content-Type") type: String,
        @Body userLoginInfo: UserLoginInfo
    ): LoginResponse

}