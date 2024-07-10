package com.tuners.tutu.ui.main_students

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
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.databinding.ActivityMainBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity
import com.tuners.tutu.ui.main_mentors.MainMentorActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var navView: BottomNavigationView
    private val navController : NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navHostFragment.navController
    }

    private var backPressed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
            }
        }

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                else -> {
                    menuItem.onNavDestinationSelected(navController)
                }
            }
        }

        // TODO: mungkin start activitynya dijadikan di login aja
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            } else if (user.role == "mentor") {
                startActivity(Intent(this@MainActivity, MainMentorActivity::class.java))
                finish()
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.id) {
                R.id.navigation_home -> {
                    backPressed++
                    if (backPressed == 1) {
                        showToast("Tekan tombol kembali sekali lagi untuk keluar")
                    } else if (backPressed == 2) {
                        exitProcess(1)
                    }
                }
                R.id.chatFragment -> {
                    navController.popBackStack(R.id.navigation_message, false)
                }
                R.id.orderHistoryFragment -> {
                    navController.popBackStack(R.id.consultFragment, false)
                }
                R.id.orderFragment -> {
                    navController.popBackStack(R.id.consultFragment, false)
                }
                R.id.orderFragment2 -> {
                    navController.popBackStack(R.id.orderFragment, false)
                }
                else -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    binding.coordinator.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}