package com.tuners.tutu.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityMainBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.messageFab.setOnClickListener {
            navController.navigate(R.id.navigation_message)
        }

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.id) {
                R.id.navigation_home -> {
                    Toast.makeText(this@MainActivity, "woah", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_message -> {
                    binding.messageFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_message_black))
                    navController.popBackStack(R.id.navigation_home, false)
                }
                else -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    binding.messageFab.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_message))
                }
            }
        }
    }
}