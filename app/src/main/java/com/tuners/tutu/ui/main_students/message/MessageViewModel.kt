package com.tuners.tutu.ui.main_students.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tuners.tutu.data.Repository
import com.tuners.tutu.data.remote.response.ChatsResponse

class MessageViewModel(private val repository: Repository) : ViewModel() {
    val chats: LiveData<ChatsResponse> = repository.chats
    val chatLoading: LiveData<Boolean> = repository.chatLoading

    fun getChats(userId: String) = repository.getChats(userId)

    fun postMessage(roomId: String, message: String, senderId: String) = repository.postMessage(roomId, message, senderId)
}