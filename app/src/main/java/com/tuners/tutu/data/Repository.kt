package com.tuners.tutu.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.data.local.pref.UserPreference
import com.tuners.tutu.data.remote.response.ChatsResponse
import com.tuners.tutu.data.remote.response.CreateChatResponse
import com.tuners.tutu.data.remote.response.LoginResponse
import com.tuners.tutu.data.remote.response.MentorListResponse
import com.tuners.tutu.data.remote.response.MentorToCheckResponse
import com.tuners.tutu.data.remote.response.MentorsToCheck
import com.tuners.tutu.data.remote.response.MessageResponse
import com.tuners.tutu.data.remote.response.PlaceOrderResponse
import com.tuners.tutu.data.remote.response.RegisterResponse
import com.tuners.tutu.data.remote.response.UserDetailsResponse
import com.tuners.tutu.data.remote.response.UserUpdateResponse
import com.tuners.tutu.data.remote.retrofit.APIService
import com.tuners.tutu.helper.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class Repository private constructor(
    private val userPreference: UserPreference,
    private val apiService: APIService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Login
    private val _loginResponse = SingleLiveEvent<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun login(username: String, password: String) {
        _isLoading.value = true
        val client = apiService.login(username, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _loginResponse.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // Register
    private val _registerResponse = SingleLiveEvent<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun register(
        username: String,
        password: String,
        birthDatePlace: String,
        email: String,
        phoneNumber: String,
        jenjangPendidikan: String,
        role: String
    ) {
        _isLoading.value = true
        val client = apiService.register(username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan, role)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _registerResponse.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // Get User Details
    private val _userDetails = MutableLiveData<UserDetailsResponse>()
    val userDetails: LiveData<UserDetailsResponse> = _userDetails

    fun getUserDetails() {
        _isLoading.value = true
        val client = apiService.getUserDetails()
        client.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetails.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // User Update
    private val _userUpdate = MutableLiveData<UserUpdateResponse>()
    val userUpdate: LiveData<UserUpdateResponse> = _userUpdate

    fun updateUser(
        userId: String,
        username: String,
        password: String,
        birthDatePlace: String,
        email: String,
        phoneNumber: String,
        jenjangPendidikan: String,
    ) {
        _isLoading.value = true
        val client = apiService.updateUser(userId, username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan)
        client.enqueue(object : Callback<UserUpdateResponse> {
            override fun onResponse(
                call: Call<UserUpdateResponse>,
                response: Response<UserUpdateResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userUpdate.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserUpdateResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: APIService
        ) : Repository = instance ?: synchronized(this) {
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
        }
    }

    // get mentors
    private val _mentors = MutableLiveData<MentorListResponse>()
    val mentors: LiveData<MentorListResponse> get() = _mentors
    private val _mentorsLoading = MutableLiveData<Boolean>()
    val mentorsLoading: LiveData<Boolean> get() = _mentorsLoading

    fun getMentors() {
        _mentorsLoading.value = true
        val client = apiService.getMentors()
        client.enqueue(object : Callback<MentorListResponse> {
            override fun onResponse(
                call: Call<MentorListResponse>,
                response: Response<MentorListResponse>
            ) {
                _mentorsLoading.value = false
                if (response.isSuccessful) {
                    _mentors.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MentorListResponse>, t: Throwable) {
                _mentorsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private val _mentorsToCheck = MutableLiveData<MentorToCheckResponse>()
    val mentorsToCheck: LiveData<MentorToCheckResponse> get() = _mentorsToCheck

    fun getMentorsToCheck() {
        _mentorsLoading.value = true
        val client = apiService.getMentorsToCheck()
        client.enqueue(object : Callback<MentorToCheckResponse> {
            override fun onResponse(
                call: Call<MentorToCheckResponse>,
                response: Response<MentorToCheckResponse>
            ) {
                _mentorsLoading.value = false
                if (response.isSuccessful) {
                    _mentorsToCheck.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MentorToCheckResponse>, t: Throwable) {
                _mentorsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // search mentors
    fun searchMentors(username: String) {
        _mentorsLoading.value = true
        val client = apiService.searchMentors(username)
        client.enqueue(object : Callback<MentorListResponse> {
            override fun onResponse(
                call: Call<MentorListResponse>,
                response: Response<MentorListResponse>
            ) {
                _mentorsLoading.value = false
                if (response.isSuccessful) {
                    _mentors.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MentorListResponse>, t: Throwable) {
                _mentorsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // update userIsChecked(mentor)
    private val _approveMentor = MutableLiveData<UserUpdateResponse>()
    val approveMentor: LiveData<UserUpdateResponse> get() = _approveMentor
    private val _approveLoading = MutableLiveData<Boolean>()
    val approveLoading: LiveData<Boolean> get() = _approveLoading

    fun approveMentor(mentorId: String) {
        _approveLoading.value = true
        val client = apiService.approveMentor(mentorId)
        client.enqueue(object : Callback<UserUpdateResponse> {
            override fun onResponse(
                call: Call<UserUpdateResponse>,
                response: Response<UserUpdateResponse>
            ) {
                _approveLoading.value = false
                if (response.isSuccessful) {
                    _approveMentor.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserUpdateResponse>, t: Throwable) {
                _approveLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // get chats
    private val _chats = MutableLiveData<ChatsResponse>()
    val chats: LiveData<ChatsResponse> get() = _chats
    private val _chatLoading = MutableLiveData<Boolean>()
    val chatLoading: LiveData<Boolean> get() = _chatLoading

    fun getChats(userId: String) {
        _chatLoading.value = true
        val client = apiService.getChats(userId)
        client.enqueue(object : Callback<ChatsResponse> {
            override fun onResponse(call: Call<ChatsResponse>, response: Response<ChatsResponse>) {
                _chatLoading.value = false
                if (response.isSuccessful) {
                    _chats.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatsResponse>, t: Throwable) {
                _chatLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getChatsMentor(mentorId: String) {
        _chatLoading.value = true
        val client = apiService.getChatsMentor(mentorId)
        client.enqueue(object : Callback<ChatsResponse> {
            override fun onResponse(call: Call<ChatsResponse>, response: Response<ChatsResponse>) {
                _chatLoading.value = false
                if (response.isSuccessful) {
                    _chats.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatsResponse>, t: Throwable) {
                _chatLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // create chatroom
    private val _createChatResponse = SingleLiveEvent<CreateChatResponse>()
    val createChatResponse: LiveData<CreateChatResponse> get() = _createChatResponse
    private val _createChatLoading = SingleLiveEvent<Boolean>()
    val createChatLoading: LiveData<Boolean> get() = _createChatLoading

    fun createChat(mentorUsername: String, mentorId: String, studentUsername: String, userId: String) {
        _createChatLoading.value = true
        val client = apiService.createChat(mentorUsername, mentorId, studentUsername, userId)
        client.enqueue(object : Callback<CreateChatResponse> {
            override fun onResponse(
                call: Call<CreateChatResponse>,
                response: Response<CreateChatResponse>
            ) {
                _createChatLoading.value = false
                if (response.isSuccessful) {
                    _createChatResponse.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CreateChatResponse>, t: Throwable) {
                _createChatLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // post message
    private val _msgResp = MutableLiveData<MessageResponse>()

    fun postMessage(roomId: String, message: String, senderId: String) {
        val client = apiService.postMessage(roomId, message, senderId)
        client.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    _msgResp.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    // PlaceOrder
    private val _order = MutableLiveData<PlaceOrderResponse>()
    private val _orderLoading = MutableLiveData<Boolean>()
    val orderLoading: LiveData<Boolean> get() = _orderLoading

    fun placeOrder(mentorUsername: String, userId: String, consultationType: String, consultationDuration: String, total: Int) {
        _orderLoading.value = true
        val client = apiService.placeOrder(mentorUsername, userId, consultationType, consultationDuration, total)
        client.enqueue(object : Callback<PlaceOrderResponse> {
            override fun onResponse(
                call: Call<PlaceOrderResponse>,
                response: Response<PlaceOrderResponse>
            ) {
                _orderLoading.value = false
                if (response.isSuccessful) {
                    _order.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PlaceOrderResponse>, t: Throwable) {
                _orderLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}