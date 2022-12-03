package com.ahtar1.sanitastest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.ahtar1.sanitastest.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser==null){
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        signOutButton.setOnClickListener(signOutListener)

    }
    val signOutListener= View.OnClickListener { view ->
        when (view.getId()) {
            R.id.signOutButton -> {
                auth.signOut()
                val intent= Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}