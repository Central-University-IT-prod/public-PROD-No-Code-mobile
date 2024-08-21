package com.ari.prodvizhenie.calendar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostInfo(
    val attachments: List<Attachment>,
    val body: String,
    val creator_id: Int,
    val id: Int,
    val integrations: List<Integration>,
    val name: String,
    val organization_id: Int,
    val upload_date: Long
) : Parcelable