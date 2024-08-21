package com.ari.prodvizhenie.auth.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import com.ari.prodvizhenie.di.dataStore
import com.ari.prodvizhenie.util.Constants
import com.ari.prodvizhenie.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun deleteAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = false
        }
    }

    override suspend fun saveDeviceToken(token: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.DEVICE_TOKEN] = token
        }
    }

    override fun readDeviceToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.DEVICE_TOKEN] ?: ""
        }
    }


}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
    val DEVICE_TOKEN = stringPreferencesKey(name = Constants.DEVICE_TOKEN)
}