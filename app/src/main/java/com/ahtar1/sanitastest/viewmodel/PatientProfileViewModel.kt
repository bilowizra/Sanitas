package com.ahtar1.sanitastest.viewmodel

import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.Patient
import com.ahtar1.sanitastest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class PatientProfileViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var tc: String
    private lateinit var name: String


    fun savePatient(birthdate:String, age: Int, gender:String, bloodType:String, height:Int, weight:Int, bmi:Float, allergies:String, phone:Int, language:String){
        auth= FirebaseAuth.getInstance()
        database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")

        val user= auth.currentUser
        val uid= user!!.uid
        CoroutineScope(Dispatchers.Main).launch {

            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",uid).get().await()
            tc=query.documents[0].get("tc").toString()
            name=query.documents[0].get("name").toString()
        }

        val databaseRef= Firebase.firestore.collection("patients")
        val patient= Patient(name,birthdate,age,gender, weight, height, bmi, bloodType, allergies, language, phone, tc.toInt(), uid)
        databaseRef.add(patient)


    }


}