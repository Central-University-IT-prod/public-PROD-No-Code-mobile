package com.ari.prodvizhenie.auth.presentation.login_screen

import com.ari.prodvizhenie.calendar.domain.model.PostInfo

data class PostViewState(
    val isLoading: Boolean = false,
    val posts: ArrayList<PostInfo> = ArrayList(),
    val error: String? = null
)