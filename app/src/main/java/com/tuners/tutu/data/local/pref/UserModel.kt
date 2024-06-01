package com.tuners.tutu.data.local.pref

data class UserModel(
    val userId: String,
    val name: String,
    val isLoggedIn: Boolean = false
)