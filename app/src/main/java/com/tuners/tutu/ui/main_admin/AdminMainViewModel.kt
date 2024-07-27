package com.tuners.tutu.ui.main_admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.data.remote.response.MentorToCheckResponse
import com.tuners.tutu.data.remote.response.UserUpdateResponse
import kotlinx.coroutines.launch

class AdminMainViewModel(private val repository: Repository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    val mentorsToCheck: LiveData<MentorToCheckResponse> = repository.mentorsToCheck
    val mentorsLoading: LiveData<Boolean> = repository.mentorsLoading
    fun getMentorsToCheck() = repository.getMentorsToCheck()

    val approveMentor: LiveData<UserUpdateResponse> = repository.approveMentor
    fun approveMentor(mentorId: String) = repository.approveMentor(mentorId)
}