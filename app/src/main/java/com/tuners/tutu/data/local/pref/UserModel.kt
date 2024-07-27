package com.tuners.tutu.data.local.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val userId: String,
    val name: String,
    val password: String,
    val birthDatePlace: String,
    val jenjangPendidikan: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val balance: Int,
    val accountIsChecked: Boolean,
    val isLoggedIn: Boolean = false
): Parcelable