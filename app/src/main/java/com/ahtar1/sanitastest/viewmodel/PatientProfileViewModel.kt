package com.ahtar1.sanitastest.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.math.BigInteger
import java.util.Date

class PatientProfileViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var tc: String
    private lateinit var name: String
    var getName = MutableLiveData<String>().apply { postValue("N/A") }
    var birthdate= MutableLiveData<String>()
    var age= MutableLiveData<Int>()
    var gender= MutableLiveData<String>()
    var bloodType= MutableLiveData<String>()
    var height= MutableLiveData<Int>()
    var weight= MutableLiveData<Int>()
    var allergies= MutableLiveData<String>()
    var phone= MutableLiveData<String>()
    var language= MutableLiveData<String>()
    var bmi = MutableLiveData<Float>()
    var getTc= MutableLiveData<String>()
    //lateinit var sharedPreferences: SharedPreferences


    fun savePatient(birthdate:String, age: Int, gender:String, bloodType:String, height:Int, weight:Int, bmi:Float, allergies:String, phone:String, language:String){
        auth= FirebaseAuth.getInstance()
        database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")

        val user= auth.currentUser
        val uid= user!!.uid
        CoroutineScope(Dispatchers.Main).launch {

            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",uid).get().await()
            tc = query.documents[0].get("tc").toString()
            name = query.documents[0].get("name").toString()

            val databaseRef= Firebase.firestore.collection("patients")
            val patient= Patient(name,birthdate,age,gender, weight, height, bmi, bloodType, allergies, language, phone, tc, uid)
            val uidQuery: QuerySnapshot = FirebaseFirestore.getInstance().collection("patients").whereEqualTo("uid",uid).get().await()
            if(uidQuery.documents.isEmpty()){
                databaseRef.add(patient).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        println("b")
                    } else{
                        println("c")
                    }
                }
            } else{
                databaseRef.document(uidQuery.documents[0].id).set(patient).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        println("b")
                    } else{
                        println("c")
                    }
                }
            }

        }




    }
    fun getPatient(){
        auth= FirebaseAuth.getInstance()
        //database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")

        val user= auth.currentUser
        val uid= user!!.uid
        CoroutineScope(Dispatchers.Main).launch {
            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",uid).get().await()
            tc = query.documents[0].get("tc").toString()
            name = query.documents[0].get("name").toString()


            val uidQuery: QuerySnapshot = FirebaseFirestore.getInstance().collection("patients").whereEqualTo("uid",uid).get().await()
            if(uidQuery.documents.isEmpty()){
                getName.postValue(name)

                getTc.postValue(tc)

            } else {
                getName.postValue(uidQuery.documents[0].get("name").toString())
                birthdate.postValue(uidQuery.documents[0].get("birthdate").toString())
                age.postValue(uidQuery.documents[0].get("age").toString().toInt())
                gender.postValue(uidQuery.documents[0].get("gender").toString())
                bloodType.postValue(uidQuery.documents[0].get("bloodType").toString())
                height.postValue(uidQuery.documents[0].get("height").toString().toInt())
                weight.postValue(uidQuery.documents[0].get("weight").toString().toInt())
                allergies.postValue(uidQuery.documents[0].get("allergies").toString())
                phone.postValue(uidQuery.documents[0].get("phoneNumber").toString())
                language.postValue(uidQuery.documents[0].get("spokenLanguage").toString())
                bmi.postValue(uidQuery.documents[0].get("bmi").toString().toFloat())
                getTc.postValue(uidQuery.documents[0].get("tc").toString())

                println(getTc)
            }



        }
    }


}