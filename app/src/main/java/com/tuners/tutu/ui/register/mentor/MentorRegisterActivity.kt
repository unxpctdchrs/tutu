package com.tuners.tutu.ui.register.mentor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tuners.tutu.R
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.databinding.ActivityMentorRegisterBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.register.RegisterViewModel

class MentorRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMentorRegisterBinding

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMentorRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var targetDidik = ""
        var siswaIsChecked = false
        var mahasiswaIsChecked = false

        binding.btnSiswa.setOnCheckedChangeListener { _, isChecked ->
            siswaIsChecked = isChecked
        }

        binding.btnMahasiswa.setOnCheckedChangeListener { _, isChecked ->
            mahasiswaIsChecked = isChecked
        }

        binding.btnNext.setOnClickListener {
            if (siswaIsChecked) {
                targetDidik = "Siswa"
            } else if (mahasiswaIsChecked) {
                targetDidik = "Mahasiswa"
            }
            Log.d("Target Didik", targetDidik)
            val data = UserModel(
                userId = "",
                name = binding.edtName.text.toString().trim(),
                password = binding.edtPassword.text.toString().trim(),
                birthDatePlace = binding.edtBirth.text.toString().trim(),
                email = binding.edtEmail.text.toString().trim(),
                phoneNumber = binding.edtPhonenumber.text.toString().trim(),
                role = "mentor",
                balance = 0,
                jenjangPendidikan = targetDidik
            )
            val intent = Intent(this@MentorRegisterActivity, MentorRegisterActivity2::class.java)
            intent.putExtra(MentorRegisterActivity2.DATA, data)
            startActivity(intent)
        }
    }
}