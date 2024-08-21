package com.ari.prodvizhenie.calendar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Integration(
    val group_avatar: String,
    val group_id: Long,
    val group_name: String,
    val id: Int,
    val organization_id: Int,
    val type: String
) : Parcelable