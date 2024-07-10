package com.tuners.tutu.ui.register.student

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityStudentRegisterBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity
import com.tuners.tutu.ui.register.RegisterViewModel

class StudentRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentRegisterBinding

    private val registerViewModel by viewModels<RegisterViewModel> {
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

            if (!binding.siswaBtn.isChecked && !binding.mahasiswaBtn.isChecked) {
                Toast.makeText(this@StudentRegisterActivity, "Silahkan pilih jenjang pendidikan terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
            else if (binding.edtName.text?.isEmpty()!!) binding.edtName.error = getString(R.string.notam)
            else if (binding.edtPassword.text?.isEmpty()!!) binding.edtPassword.error = getString(R.string.notam)
            else if (binding.edtBirth.text?.isEmpty()!!) binding.edtBirth.error = getString(R.string.notam)
            else if (binding.edtEmail.text?.isEmpty()!!) binding.edtEmail.error = getString(R.string.notam)
            else if (binding.edtPhonenumber.text?.isEmpty()!!) binding.edtPhonenumber.error = getString(R.string.notam)
            else {
                registerViewModel.register(
                    binding.edtName.text.toString().trim(),
                    binding.edtPassword.text.toString().trim(),
                    binding.edtBirth.text.toString().trim(),
                    binding.edtEmail.text.toString().trim(),
                    binding.edtPhonenumber.text.toString().trim(),
                    selectedPendidikan,
                    "student"
                )
            }
        }

        registerViewModel.registerResponse.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this@StudentRegisterActivity, LoginActivity::class.java))
                finish()
                Toast.makeText(this@StudentRegisterActivity, "Account Created", Toast.LENGTH_SHORT).show()
            }
        }
    }
}