package com.ari.prodvizhenie.auth.presentation.login_screen

import com.ari.prodvizhenie.auth.domain.model.Tokens

data class LoginViewState(
    val isLoading: Boolean = false,
    val tokens: Tokens = Tokens("", ""),
    val error: String? = null
)