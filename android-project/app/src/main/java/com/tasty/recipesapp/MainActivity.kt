package com.tasty.recipesapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#f15025")
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set OnItemSelectedListener for the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            // Handle the selection of navigation items
            when (item.itemId) {
                R.id.homeFragment -> {  // Ensure these IDs match the menu resource
                    navController.navigate(R.id.homeFragment)
                    true // Return true to indicate that the selection was handled
                }
                R.id.recipesFragment -> {
                    navController.navigate(R.id.recipesFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                else -> false // Return false if the selection wasn't handled
            }
        }
    }
}
