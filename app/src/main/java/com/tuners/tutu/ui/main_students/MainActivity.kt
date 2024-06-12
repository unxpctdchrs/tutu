package com.tuners.tutu.ui.main_students

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
import com.tuners.tutu.databinding.ActivityMainBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.login.LoginActivity
import com.tuners.tutu.ui.main_mentors.MainMentorActivity

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


        // TODO: mungkin start activitynya dijadikan di login aja
        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            } else if (user.isMentor) {
                startActivity(Intent(this@MainActivity, MainMentorActivity::class.java))
                finish()
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (navController.currentDestination?.id) {
                R.id.navigation_home -> {
                    val customLayout = layoutInflater.inflate(R.layout.exit_dialog, null)
                    val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
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