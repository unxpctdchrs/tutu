package com.tuners.tutu.ui.main_mentors.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuners.tutu.data.Repository
import kotlinx.coroutines.launch

class MentorProfileViewModel(private val repository: Repository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}