package com.example.myvoozkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myvoozkotlin.databinding.ActivityMainBinding
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        loadFragment(homeFragment)
    }

    private fun initViews() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
                item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = homeFragment
                }
                R.id.navigation_profile -> {
                    fragment = profileFragment
                }
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if(fragment != null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootView, fragment)
                .commit()
        }
    }
}