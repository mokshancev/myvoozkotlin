package com.example.myvoozkotlin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myvoozkotlin.databinding.ActivityMainBinding
import com.example.myvoozkotlin.home.HomeFragment
import com.example.myvoozkotlin.search.SearchFragment
import com.example.myvoozkotlin.search.helpers.SearchEnum
import com.example.myvoozkotlin.selectGroup.SelectGroupFragment
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
        val fragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.rootViewMain, fragment, HomeFragment.javaClass.simpleName).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.homeFragment)
//            //popBackStack(item, R.id.homeFragment)
//        else if(item.itemId == R.id.profileFragment)
//            //popBackStack(item, R.id.profileFragment)
        return true
    }
}