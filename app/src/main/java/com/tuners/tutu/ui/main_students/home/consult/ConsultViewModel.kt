package com.tuners.tutu.ui.main_students.home.consult

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.MentorListResponse

class ConsultViewModel(private val repository: Repository) : ViewModel() {
    val mentors: LiveData<MentorListResponse> = repository.mentors
    val mentorsLoading: LiveData<Boolean> = repository.mentorsLoading

    fun getMentors() = repository.getMentors()
}