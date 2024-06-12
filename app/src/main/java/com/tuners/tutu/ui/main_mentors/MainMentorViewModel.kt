package com.tuners.tutu.ui.main_mentors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.local.pref.UserModel
import kotlinx.coroutines.launch

class MainMentorViewModel(private val repository: Repository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}