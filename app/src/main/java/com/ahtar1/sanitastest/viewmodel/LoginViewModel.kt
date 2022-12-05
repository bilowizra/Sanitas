package com.ahtar1.sanitastest.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.ahtar1.sanitastest.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    var isSuccessful= MutableLiveData<Boolean>()
    var errorMessage= MutableLiveData<String>()
    var isDoctor= MutableLiveData<Boolean>()

    fun loginUser(email: String,password: String){

        auth= FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                isSuccessful.postValue(true)
            } else{
                isSuccessful.postValue(false)
                errorMessage.postValue("başaramadık abi")
            }
        }
    }
    fun getRole()= CoroutineScope(Dispatchers.Main).launch{
        println("get role")
        auth= FirebaseAuth.getInstance()
        try {
            println("try")
            println(auth.currentUser!!.uid)
            var querySnapshot= FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid",auth.currentUser!!.uid).get().await()
            val role=querySnapshot.documents[0].get("role")
            println(role.toString())
            if(role =="Doctor"){
                println("gr "+role)
                isDoctor.postValue(true)
            } else{
                println("gr "+role)
                isDoctor.postValue(false)
            }
            /*
            Firebase.firestore.collection("users").whereEqualTo("uuid",auth.currentUser!!.uid).get().addOnCompleteListener {
                if(it.isSuccessful){
                    val querySnapshot= it.result
                    val role= querySnapshot.documents[0].get("role")
                    println(role)
                    if(role =="Doctor"){
                        println("gr "+role)
                        isDoctor.postValue(true)
                    } else{
                        println("gr "+role)
                        isDoctor.postValue(false)
                    }
                }else{
                    println("not successful")
                }
            }

             */



            /*
            println(auth.currentUser!!.uid)
            val role= querySnapshot["role"]
            println(role)
            if(role =="Doctor"){
                println("gr "+role)
                isDoctor.postValue(true)
            } else{
                println("gr "+role)
                isDoctor.postValue(false)
            }

             */
        } catch (e: Exception){
            println("determinerole")
            println(e.message)
        }
    }


}