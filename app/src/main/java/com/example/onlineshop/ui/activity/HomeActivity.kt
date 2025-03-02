package com.example.onlineshop.ui.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.onlineshop.R
import com.example.onlineshop.ui.fragment.SearchFragment
import com.example.onlineshop.ui.fragment.FavouriteFragment
import com.example.onlineshop.ui.fragment.HomeFragment
import com.example.onlineshop.ui.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set the default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        // Handle item selection
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_categories -> selectedFragment = SearchFragment()
                R.id.nav_favourite -> selectedFragment = FavouriteFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            }
            true
        }
    }
}