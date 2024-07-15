package com.tuners.tutu.ui.main_mentors

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kotlin.system.exitProcess

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

    private var backPressed: Int = 0
    private var lastPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMentorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.messageFab.setOnClickListener {
            navController.navigate(R.id.navigation_message)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_message -> {
                    binding.coordinator.visibility = View.GONE
                }
                R.id.navigation_home -> {
                    binding.coordinator.visibility = View.VISIBLE
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
                    val currentTime = System.currentTimeMillis()

                    if (currentTime - lastPressTime > 1000) {
                        backPressed = 0
                    }

                    backPressed++
                    lastPressTime = currentTime

                    if (backPressed == 1) {
                        Toast.makeText(this@MainMentorActivity, "Tekan tombol kembali sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
                    } else if (backPressed == 2) {
                        exitProcess(1)
                    }
                }
                R.id.chatFragment2 -> {
                    navController.popBackStack(R.id.navigation_message, false)
                }
                else -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    binding.coordinator.visibility = View.VISIBLE
                }
            }
        }
    }
}