package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.LoginViewModel
import com.ahtar1.sanitastest.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        auth= FirebaseAuth.getInstance()



        CoroutineScope(Dispatchers.Main).launch {
            if(auth.currentUser==null){
                val intent= Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(intent)
            }
            println(auth.currentUser!!.uid)
            val query=FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",auth.currentUser!!.uid).get().await()
            val role= query.documents[0].get("role").toString()

            if (role== "Doctor"){
                val intent= Intent(this@MainActivity,DoctorActivity::class.java)
                startActivity(intent)
            } else if(role=="Patient"){
                val intent= Intent(this@MainActivity,PatientActivity::class.java)
                startActivity(intent)
            } else{
                println("empty")
            }
        }




    }


}