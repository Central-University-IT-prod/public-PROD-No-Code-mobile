package com.ari.prodvizhenie.details.domain.model

data class Statistics(
    val commentsCount: Int,
    val membersCount: Int,
    val negativeReactionCount: Int,
    val neutralReactionCount: Int,
    val positiveReactionCount: Int,
    val viewCount: Int
)