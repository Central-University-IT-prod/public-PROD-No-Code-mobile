package com.ari.prodvizhenie.auth.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>

    suspend fun deleteAppEntry()

    suspend fun saveDeviceToken(token: String)

    fun readDeviceToken(): Flow<String>

}