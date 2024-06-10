package com.tuners.tutu.ui.register.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityStudentRegisterBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity

class StudentRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentRegisterBinding

    private val studentRegisterViewModel by viewModels<StudentRegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStudentRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var selectedPendidikan = ""
        var siswaIsChecked = false
        var mahasiswaIsChecked = false

        binding.siswaBtn.setOnCheckedChangeListener { _, isChecked ->
            siswaIsChecked = isChecked
        }

        binding.mahasiswaBtn.setOnCheckedChangeListener { _, isChecked ->
            mahasiswaIsChecked = isChecked
        }

        binding.btnCreateAccount.setOnClickListener {
            if (siswaIsChecked) {
                selectedPendidikan = "Siswa"
            } else if (mahasiswaIsChecked) {
                selectedPendidikan = "Mahasiswa"
            }
            Log.d("StudentRegister", selectedPendidikan)

            studentRegisterViewModel.register(
                binding.edtName.text.toString().trim(),
                binding.edtPassword.text.toString().trim(),
                binding.edtBirth.text.toString().trim(),
                binding.edtEmail.text.toString().trim(),
                binding.edtPhonenumber.text.toString().trim(),
                selectedPendidikan
            )
        }

        studentRegisterViewModel.registerResponse.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this@StudentRegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}