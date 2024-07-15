package com.tuners.tutu.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuners.tutu.data.Repository
import com.tuners.tutu.di.Injection
import com.tuners.tutu.ui.login.LoginViewModel
import com.tuners.tutu.ui.main_mentors.MainMentorViewModel
import com.tuners.tutu.ui.main_mentors.message.MentorMessageViewModel
import com.tuners.tutu.ui.main_mentors.profile.MentorProfileViewModel
import com.tuners.tutu.ui.main_students.MainViewModel
import com.tuners.tutu.ui.main_students.home.consult.ConsultViewModel
import com.tuners.tutu.ui.main_students.message.MessageViewModel
import com.tuners.tutu.ui.main_students.profile.ProfileViewModel
import com.tuners.tutu.ui.register.RegisterViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainMentorViewModel::class.java) -> {
                MainMentorViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MentorProfileViewModel::class.java) -> {
                MentorProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ConsultViewModel::class.java) -> {
                ConsultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
                MessageViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MentorMessageViewModel::class.java) -> {
                MentorMessageViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}