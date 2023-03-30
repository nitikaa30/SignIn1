package com.example.signin

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.signin.databinding.ActivityMainBinding
import com.example.signin.fragments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Splash()).commit()
        val preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        Handler(Looper.myLooper()!!).postDelayed( Runnable {
            if (preferences.getBoolean("isLoggedIn", false)) {
                val fragment = Home()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            } else {
                val fragment = signin()
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()

            }
        },3000)

    }
}