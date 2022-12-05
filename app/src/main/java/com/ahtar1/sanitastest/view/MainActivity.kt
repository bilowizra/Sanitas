package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.LoginViewModel
import com.ahtar1.sanitastest.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences=this.getSharedPreferences("com.ahtar1.sanitastest",Context.MODE_PRIVATE)
        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        auth= FirebaseAuth.getInstance()
        viewModel.getRole()

        if(auth.currentUser==null){
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        val roleFromSharedPreferences= sharedPreferences.getString("role","empty")
        if (roleFromSharedPreferences== "Doctor"){
            val intent= Intent(this,DoctorActivity::class.java)
            startActivity(intent)
        } else if(roleFromSharedPreferences=="Patient"){
            val intent= Intent(this,PatientActivity::class.java)
            startActivity(intent)
        } else{
            println("empty")
        }

        /*
        if (viewModel.isDoctor){

            sharedPreferences.edit().putString("role","Doctor").apply()
            val intent= Intent(this,DoctorActivity::class.java)
            startActivity(intent)
        }else{
            sharedPreferences.edit().putString("role","Patient").apply()
            val intent= Intent(this,PatientActivity::class.java)
            startActivity(intent)
        }

         */



        signOutButton.setOnClickListener(signOutListener)

    }
    private val signOutListener= View.OnClickListener { view ->
        when (view.getId()) {
            R.id.signOutButton -> {
                auth.signOut()
                sharedPreferences.edit().putString("role","empty")
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}