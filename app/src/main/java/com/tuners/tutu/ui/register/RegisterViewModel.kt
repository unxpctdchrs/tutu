package com.tuners.tutu.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.RegisterResponse

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repository.registerResponse
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun register(
        username: String,
        password: String,
        birthDatePlace: String,
        email: String,
        phoneNumber: String,
        jenjangPendidikan: String,
        role: String
    ) = repository.register(username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan, role)
}