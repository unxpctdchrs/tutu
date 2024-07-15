package com.tuners.tutu.ui.main_mentors.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.ChatsResponse

class MentorMessageViewModel(private val repository: Repository) : ViewModel() {
    val chats: LiveData<ChatsResponse> = repository.chats
    val chatLoading: LiveData<Boolean> = repository.chatLoading

    fun getChatsMentor(mentorId: String) = repository.getChatsMentor(mentorId)
}