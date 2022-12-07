package com.ahtar1.sanitastest.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.StringBuilder

class MainViewModel: ViewModel() {
    private lateinit var auth: FirebaseAuth
    var isDoctor=false

    fun getRole()= CoroutineScope(Dispatchers.Main).launch{
        auth= FirebaseAuth.getInstance()
        try {
            val databaseRef= Firebase.firestore.collection("users")
            val querySnapshot= databaseRef.document().get().await()
            val role= querySnapshot.get("role")
            if (role =="Doctor"){
                isDoctor= true
            } else{
                isDoctor=false
            }
        } catch (e: Exception){
            println("determinerole")
        }
    }
}