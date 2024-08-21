package com.ari.prodvizhenie.auth.domain.model

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)