package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityDoctorBinding

class DoctorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(doctor_schedule())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile -> replaceFragment(doctor_profile())
                R.id.schedule -> replaceFragment(doctor_schedule())
                else -> { }
            }
            true
        }

    }
    override fun onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}