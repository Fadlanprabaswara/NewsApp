package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newsapp.fragment.FragmentBusiness
import com.example.newsapp.fragment.FragmentEntertaiment
import com.example.newsapp.fragment.FragmentHeadline
import com.example.newsapp.fragment.FragmentHealth
import com.example.newsapp.fragment.FragmentSports
import com.example.newsapp.fragment.FragmentTechnology
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<ChipNavigationBar>(R.id.bottomNavigation)
        bottomNavigation.setItemSelected(R.id.menuHome, true)

        setupFragment(FragmentHeadline())

        bottomNavigation.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.menuHome -> setupFragment(FragmentHeadline())
                    R.id.menuSports -> setupFragment(FragmentSports())
                    R.id.menuTechnology -> setupFragment(FragmentTechnology())
                    R.id.menuBusiness -> setupFragment(FragmentBusiness())
                    R.id.menuHealty -> setupFragment(FragmentHealth())
                    R.id.menuEnternatiment -> setupFragment(FragmentEntertaiment())
//                    R.id.menuSearch -> setupFragment(FragmentSearch())
                }
            }
        })
    }


    private fun setupFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, fragment)
        transaction.commit()
    }
}
