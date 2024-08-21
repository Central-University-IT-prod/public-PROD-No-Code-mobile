package com.ari.prodvizhenie.calendar.presentation

import com.ari.prodvizhenie.calendar.domain.model.Data

data class OrganizationsViewState(
    val organizations: List<Data> = emptyList(),
    val error: String? = null
)