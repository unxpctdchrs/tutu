package com.tuners.tutu.ui.register.mentor

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityMentorRegister3Binding
import com.tuners.tutu.ui.login.LoginActivity

class MentorRegisterActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMentorRegister3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMentorRegister3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBackToLogin.setOnClickListener {
            startActivity(Intent(this@MentorRegisterActivity3, LoginActivity::class.java))
            finish()
        }
    }
}