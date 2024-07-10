package com.tuners.tutu.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USERID_KEY] = user.userId
            preferences[NAME_KEY] = user.name
            preferences[PASSWORD_KEY] = user.password
            preferences[BIRTH_KEY] = user.birthDatePlace
            preferences[PENDIDIKAN_KEY] = user.jenjangPendidikan
            preferences[EMAIL_KEY] = user.email
            preferences[PHONE_KEY] = user.phoneNumber
            preferences[ROLE_KEY] = user.role
            preferences[BALANCE_KEY] = user.balance
            preferences[IS_LOGGED_IN_KEY] = user.isLoggedIn
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
                preferences[BIRTH_KEY] ?: "",
                preferences[PENDIDIKAN_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PHONE_KEY] ?: "",
                preferences[ROLE_KEY] ?: "",
                preferences[BALANCE_KEY] ?: 0,
                preferences[IS_LOGGED_IN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERID_KEY = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val BIRTH_KEY = stringPreferencesKey("birthDatePlace")
        private val PENDIDIKAN_KEY = stringPreferencesKey("jenjangPendidikan")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PHONE_KEY = stringPreferencesKey("phoneNumber")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val BALANCE_KEY = intPreferencesKey("balance")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("isLoggedIn")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}