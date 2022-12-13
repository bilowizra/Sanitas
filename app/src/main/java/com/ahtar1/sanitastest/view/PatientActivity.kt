package com.ahtar1.sanitastest.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityPatientBinding
import com.google.firebase.auth.FirebaseAuth

class PatientActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        setContentView(binding.root)
        replaceFragment(patient_profile())

        binding.bottomNavigationView2.setOnItemSelectedListener {
            when(it.itemId){
                R.id.schedule -> {
                    replaceFragment(patient_schedule())
                    true
                }
                R.id.medicaments -> {
                    replaceFragment(patient_view_medicaments())
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var itemView= item.itemId
        when(itemView){
            R.id.signOutButton -> {
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

    override fun onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}