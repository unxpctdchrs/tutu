package com.tuners.tutu.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.data.local.pref.UserPreference
import com.tuners.tutu.data.remote.response.LoginResponse
import com.tuners.tutu.data.remote.response.RegisterResponse
import com.tuners.tutu.data.remote.response.UserDetailsResponse
import com.tuners.tutu.data.remote.response.UserUpdateResponse
import com.tuners.tutu.data.remote.retrofit.APIService
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
    private val _loginResponse = MutableLiveData<LoginResponse>()
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
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun register(username: String, password: String, birthDatePlace: String, email: String, phoneNumber: String, jenjangPendidikan: String) {
        _isLoading.value = true
        val client = apiService.register(username, password, birthDatePlace, email, phoneNumber, jenjangPendidikan)
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
        jenjangPendidikan: String
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
}