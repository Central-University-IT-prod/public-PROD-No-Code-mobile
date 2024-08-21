package com.ari.prodvizhenie.auth.domain.model

data class LoginResponse(
    val type: String,
    val `data`: Tokens
)