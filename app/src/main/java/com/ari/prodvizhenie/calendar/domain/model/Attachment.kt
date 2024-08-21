package com.ari.prodvizhenie.calendar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attachment(
    val body: String,
    val id: Int,
    val type: String
) : Parcelable