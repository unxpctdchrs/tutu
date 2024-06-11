package com.tuners.tutu.data.local.pref

data class UserModel(
    val userId: String,
    val name: String,
    val password: String,
    val birthDatePlace: String,
    val jenjangPendidikan: String,
    val email: String,
    val phoneNumber: String,
    val isLoggedIn: Boolean = false
)