package com.example.myvoozkotlin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myvoozkotlin.databinding.ActivityMainBinding
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureViews()
    }

    private fun configureViews() {
        initNavigationView()
    }

    private fun initNavigationView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.homeFragment)
            popBackStack(item, R.id.homeFragment)
        else if(item.itemId == R.id.profileFragment)
            popBackStack(item, R.id.profileFragment)
        return true
    }

    private fun popBackStack(item: MenuItem, fragmentId: Int){
        val navController = findNavController(R.id.nav_host_fragment)
        if (item.itemId != navController.currentDestination?.id) {
            if(!findNavController(R.id.nav_host_fragment).popBackStack(fragmentId, false)) {
                findNavController(R.id.nav_host_fragment).navigate(fragmentId)
            }
        }
    }
}