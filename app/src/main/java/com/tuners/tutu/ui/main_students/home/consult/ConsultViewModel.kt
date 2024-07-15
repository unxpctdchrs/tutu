package com.tuners.tutu.ui.main_students.home.consult

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.CreateChatResponse
import com.tuners.tutu.data.remote.response.MentorListResponse

class ConsultViewModel(private val repository: Repository) : ViewModel() {
    val mentors: LiveData<MentorListResponse> = repository.mentors
    val mentorsLoading: LiveData<Boolean> = repository.mentorsLoading
    fun getMentors() = repository.getMentors()
    fun searchMentors(username: String) = repository.searchMentors(username)

    val orderLoading: LiveData<Boolean> = repository.orderLoading
    fun placeOrder(
        mentorUsername: String,
        userId: String,
        consultationType: String,
        consultationDuration: String,
        total: Int
    ) = repository.placeOrder(mentorUsername, userId, consultationType, consultationDuration, total)

    val createChatLoading: LiveData<Boolean> = repository.createChatLoading
    val createChatResponse: LiveData<CreateChatResponse> = repository.createChatResponse
    fun createChat(
        mentorUsername: String,
        mentorId: String,
        studentUsername: String,
        userId: String
    ) = repository.createChat(mentorUsername, mentorId, studentUsername, userId)
}