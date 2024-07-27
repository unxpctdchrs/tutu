package com.tuners.tutu.ui.main_admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.adapters.MentorToCheckAdapter
import com.tuners.tutu.data.remote.response.MentorToCheckResponse
import com.tuners.tutu.data.remote.supabase.SupabaseConfig
import com.tuners.tutu.databinding.ActivityAdminMainBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    private val adminMainViewModel by viewModels<AdminMainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvMentorToCheck.layoutManager = LinearLayoutManager(this)

        adminMainViewModel.getMentorsToCheck()
        adminMainViewModel.mentorsToCheck.observe(this) { mentors ->
            loadMentors(mentors)
        }

        adminMainViewModel.approveMentor.observe(this) { response ->
            if (!response.error) adminMainViewModel.getMentorsToCheck()
        }

        adminMainViewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this@AdminMainActivity, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnLogout.setOnClickListener {
            val customLayout = layoutInflater.inflate(R.layout.exit_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(customLayout)

            val yesBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_yes)
            val noBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_no)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            yesBtn.setOnClickListener {
                adminMainViewModel.logout()
                alertDialog.dismiss()
            }

            noBtn.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        adminMainViewModel.mentorsLoading.observe(this) { isLoading ->
            setLoading(isLoading)
        }
    }

    private fun loadMentors(mentors: MentorToCheckResponse) {
        val adapter = MentorToCheckAdapter(this, false, adminMainViewModel)
        adapter.submitList(mentors.result)
        binding.rvMentorToCheck.adapter = adapter
    }

    private fun setLoading(b: Boolean) {
        binding.loader.visibility = if (b) View.VISIBLE else View.GONE
    }
}