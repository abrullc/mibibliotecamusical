package com.abrullc.mibibliotecamusical.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abrullc.mibibliotecamusical.R
import com.abrullc.mibibliotecamusical.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}