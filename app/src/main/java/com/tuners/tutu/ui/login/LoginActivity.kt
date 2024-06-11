package com.tuners.tutu.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.tuners.tutu.R
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.databinding.ActivityLoginBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main.MainActivity
import com.tuners.tutu.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.login(binding.edtUsername.text.toString().trim(), binding.edtPassword.text.toString().trim())

            loginViewModel.loginResponse.observe(this) { user ->
                if (!user.error) {
                    val userToSave = UserModel(
                        userId = user.loginResult.first().uuid,
                        name = user.loginResult.first().username,
                        password = user.loginResult.first().password,
                        birthDatePlace = user.loginResult.first().birthDatePlace,
                        jenjangPendidikan = user.loginResult.first().jenjangPendidikan,
                        email = user.loginResult.first().email,
                        phoneNumber = user.loginResult.first().phoneNumber,
                        isLoggedIn = true
                    )
                    loginViewModel.saveSession(userToSave)
                }
            }
        }

        loginViewModel.getSession().observe(this) { user ->
            if (user.isLoggedIn) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        binding.btnLoginGoogle.setOnClickListener {
            Toast.makeText(this, "On Progress..", Toast.LENGTH_SHORT).show()
        }

        binding.tvBtnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            setLoading(isLoading)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnLogin.text = ""
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.btnLogin.text = getString(R.string.login)
            binding.loader.visibility = View.GONE
        }
    }
}