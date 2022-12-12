package com.ahtar1.sanitastest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.Doctor
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

class DoctorProfileViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var tc: String
    private lateinit var name: String
    //private lateinit var email: String

    var getName = MutableLiveData<String>()
    var age= MutableLiveData<Int>()
    var gender= MutableLiveData<String>()
    var specialty= MutableLiveData<String>()
    var phone= MutableLiveData<String>()
    var getTc= MutableLiveData<String>()
    //var getEmail= MutableLiveData<String>()

    fun saveDoctor(age: Int, gender:String, specialty:String, phone:String){

        auth= FirebaseAuth.getInstance()
        database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")

        val user= auth.currentUser
        val uid= user!!.uid

        CoroutineScope(Dispatchers.Main).launch {

            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",uid).get().await()
            tc = query.documents[0].get("tc").toString()
            name = query.documents[0].get("name").toString()
            //email = query.documents[0].get("email").toString()

            val databaseRef= Firebase.firestore.collection("doctors")
            val doctor= Doctor(name,age,gender,specialty,uid,phone,tc)
            val uidQuery: QuerySnapshot = FirebaseFirestore.getInstance().collection("doctors").whereEqualTo("uid",uid).get().await()

            if(uidQuery.documents.isEmpty()){
                databaseRef.add(doctor).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        println("b")
                    } else{
                        println("c")
                    }
                }

            } else{
                databaseRef.document(uidQuery.documents[0].id).set(doctor).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        println("b")
                    } else{
                        println("c")
                    }
                }
            }
        }
    }

    fun getDoctor(){

        auth= FirebaseAuth.getInstance()
        //database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")

        val user= auth.currentUser
        val uid= user!!.uid
        CoroutineScope(Dispatchers.Main).launch{

            val uidQuery: QuerySnapshot = FirebaseFirestore.getInstance().collection("doctors").whereEqualTo("uid",uid).get().await()

            //ifle kontrol
            if(uidQuery.documents.isEmpty()){
                getName.postValue(name)
                getTc.postValue(tc)
                //getEmail.postValue(email)

            }else {

                getName.postValue(uidQuery.documents[0].get("name").toString())
                age.postValue(uidQuery.documents[0].get("age").toString().toInt())
                specialty.postValue(uidQuery.documents[0].get("specialty").toString())
                gender.postValue(uidQuery.documents[0].get("gender").toString())
                phone.postValue(uidQuery.documents[0].get("phoneNumber").toString())
                getTc.postValue(uidQuery.documents[0].get("tc").toString())
                //getEmail.postValue(uidQuery.documents[0].get("email").toString())

                println(getTc)
            }
        }
    }
}