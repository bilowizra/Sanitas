package com.ahtar1.sanitastest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityPatientBinding

class PatientActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(patient_profile())

        binding.bottomNavigationView2.setOnItemSelectedListener {
            when(it.itemId){
                R.id.schedule -> {
                    replaceFragment(patient_schedule())
                    true
                }
                R.id.medicaments -> {
                    replaceFragment(patient_medicaments())
                    true
                }
                R.id.profile -> {
                    replaceFragment(patient_profile())
                    true
                }
                else -> false
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}