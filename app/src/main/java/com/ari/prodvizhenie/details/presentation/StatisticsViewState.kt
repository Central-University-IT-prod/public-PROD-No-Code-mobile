package com.ari.prodvizhenie.details.presentation

import com.ari.prodvizhenie.details.domain.model.Statistics

data class StatisticsViewState(
    val statistics: Statistics? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)