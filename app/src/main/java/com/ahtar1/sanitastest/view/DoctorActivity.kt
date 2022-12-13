package com.ahtar1.sanitastest.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityDoctorBinding

import com.google.firebase.auth.FirebaseAuth

class DoctorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_doctor,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var itemView= item.itemId
        when(itemView){
            R.id.signOutDoctorButton -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return false
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}