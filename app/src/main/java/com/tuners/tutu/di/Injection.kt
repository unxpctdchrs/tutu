package com.tuners.tutu.di

import android.content.Context
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.local.pref.UserPreference
import com.tuners.tutu.data.local.pref.dataStore
import com.tuners.tutu.data.remote.retrofit.APIConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = APIConfig.getApiService()
        return Repository.getInstance(pref, apiService)
    }
}