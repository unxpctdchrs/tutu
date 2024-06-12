package com.tuners.tutu.ui.main_students.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.data.remote.response.UserUpdateResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val userUpdate: LiveData<UserUpdateResponse> = repository.userUpdate

    fun updateUser(
        userId: String,
        username: String,
        password: String,
        birthDatePlace: String,
        email: String,
        phoneNumber: String,
        jenjangPendidikan: String
    ) = repository.updateUser(userId, username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}