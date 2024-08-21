package com.ari.prodvizhenie.calendar.domain.model

data class PostsResponse(
    val `data`: List<PostInfo>,
    val type: String
)