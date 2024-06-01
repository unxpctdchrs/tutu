package com.tuners.tutu.di

import android.content.Context
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.local.pref.UserPreference
import com.tuners.tutu.data.local.pref.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        return Repository.getInstance(pref)
    }
}