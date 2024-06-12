package com.tuners.tutu.ui.register.mentor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tuners.tutu.R
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.databinding.ActivityMentorRegister2Binding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity
import com.tuners.tutu.ui.register.RegisterViewModel

class MentorRegisterActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMentorRegister2Binding

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMentorRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DATA, UserModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DATA)
        }

        binding.btnCreateAccount.setOnClickListener {
            if (data != null) {
                registerViewModel.register(
                    username = data.name,
                    password = data.password,
                    birthDatePlace = data.birthDatePlace,
                    email = data.email,
                    phoneNumber = data.phoneNumber,
                    jenjangPendidikan = data.jenjangPendidikan,
                    isMentor = true
                )
            }
        }

        registerViewModel.registerResponse.observe(this) { register ->
            if (!register.error) {
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MentorRegisterActivity2, LoginActivity::class.java))
                finish()
            }
        }

        registerViewModel.isLoading.observe(this) { isLoading ->
            setLoading(isLoading)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnCreateAccount.text = ""
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.btnCreateAccount.text = getString(R.string.create_account)
            binding.loader.visibility = View.GONE
        }
    }

    companion object {
        const val DATA = "data"
    }
}