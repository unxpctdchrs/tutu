package com.tuners.tutu.ui.register.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.RegisterResponse

class StudentRegisterViewModel(private val repository: Repository) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repository.registerResponse

    fun register(
        username: String,
        password: String,
        birthDatePlace: String,
        email: String,
        phoneNumber: String,
        jenjangPendidikan: String
    ) = repository.register(username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan)
}