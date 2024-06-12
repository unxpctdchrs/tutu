package com.tuners.tutu.ui.main_mentors

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityMainMentorBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity

class MainMentorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMentorBinding

    private lateinit var navView: BottomNavigationView
    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main_mentor) as NavHostFragment
        navHostFragment.navController
    }

    private val mainViewModel by viewModels<MainMentorViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMentorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        binding.messageFab.setOnClickListener {
            navController.navigate(R.id.navigation_message)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_message -> {
                    binding.coordinator.visibility = View.GONE
                }
            }
        }

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainMentorActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.id) {
                R.id.navigation_home -> {
                    val customLayout = layoutInflater.inflate(R.layout.exit_dialog, null)
                    val alertDialogBuilder = AlertDialog.Builder(this@MainMentorActivity)
                        .setView(customLayout)

                    val yesBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_yes)
                    val noBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_no)

                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()

                    yesBtn.setOnClickListener {
                        mainViewModel.logout()
                        alertDialog.dismiss()
                    }

                    noBtn.setOnClickListener {
                        alertDialog.dismiss()
                    }
                }
                else -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    binding.coordinator.visibility = View.VISIBLE
                }
            }
        }
    }
}