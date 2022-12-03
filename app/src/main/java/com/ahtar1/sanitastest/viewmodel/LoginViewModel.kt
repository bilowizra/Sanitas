package com.ahtar1.sanitastest.viewmodel

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.ahtar1.sanitastest.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
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

    fun loginUser(email: String,password:String){
        auth= FirebaseAuth.getInstance()
        if(email.isNotEmpty() && password.isNotEmpty()){
            println(email+password)

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.signInWithEmailAndPassword(email,password).await()
                    println("checked")
                    isSuccessful.postValue(true)


                } catch (e: Exception){
                    isSuccessful.postValue(false)
                    errorMessage.postValue(e.message)
                    println("Exception")
                }
                println("coroutine")
            }
        }
    }
}