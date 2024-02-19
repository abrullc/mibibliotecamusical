package com.abrullc.mibibliotecamusical.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.ActivityMainBinding
import com.abrullc.mibibliotecamusical.findModule.FindFragment
import com.abrullc.mibibliotecamusical.homeModule.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var bottonNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initComponents()
        initListeners()
        replaceFragment(HomeFragment())
    }

    private fun initListeners() {
        bottonNavigationView.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bottom_bookmarks-> {
                    replaceFragment(FindFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    private fun initComponents() {
        bottonNavigationView = findViewById(R.id.bottom_nav)
    }
}